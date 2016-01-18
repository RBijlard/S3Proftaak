/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.openal.AL;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author Berry-PC
 */
public class SoundManager {

    private static SoundManager instance;

    public static SoundManager getInstance() {
        return instance;
    }

    private Music music = null;
    private boolean playDeathSound;

    public SoundManager() {
        instance = this;
    }

    public void playMusic() {
        Random rand = new Random();
        int randomNum = rand.nextInt(5) + 1;

        String path = SoundManager.class.getResource("/Resources/Music/music" + randomNum + ".ogg").getPath().replace("%20", " ");

        try {
            stopMusic();

            music = new Music(path);
        } catch (SlickException ex) {
            Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        music.loop(1, 0.25f);
    }

    public void stopMusic() {
        if (music != null) {
            music.stop();
            music = null;
        }
    }

    public void restartSound() {
        if (AL.isCreated()) {
            AL.destroy();
        }

        this.playDeathSound = true;
    }

    public void playSound(Sounds s) {
        try {
            if (s != null) {
                Sound sound = new Sound(s.getPath());

                if (music != null) {
                    music.pause();
                    sound.play(1, 0.6f);
                    music.resume();
                } else {
                    sound.play(1, 0.6f);
                }
            }

        } catch (SlickException ex) {
            System.out.println(ex.toString());
        }
    }

    public void playMenuMusic() {
        try {
            stopMusic();

            music = new Music(getClass().getResource("/Resources/Music/menu.ogg").getPath().replace("%20", " "));
            music.loop(1, 0.25f);
        } catch (SlickException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playDeathSound() {
        if (this.playDeathSound) {
            this.playDeathSound = false;
            SoundManager.getInstance().playSound(Sounds.GAMEOVER);
        }

    }

    public enum Sounds {

        JUMP("jump"), 
        GAMEOVER("gameOver"), 
        COINPICKUP("coinPickUp"), 
        BLOCKFALL("blockFall"), 
        BUTTONPRESS("buttonPress"), 
        BUTTONRELEASE("buttonRelease"), 
        LEVERPULL("leverPull"), 
        LEVERPUSH("leverPush"), 
        WEIGHTDOWN("weightDown"), 
        WEIGHTUP("weightUp");

        private final String path;

        Sounds(String path) {
            this.path = path;
        }

        public String getPath() {
            return getClass().getResource("/Resources/Music/" + path + ".ogg").getPath().replace("%20", " ");
        }
    }
}
