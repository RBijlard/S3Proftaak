/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author Berry-PC
 */
public final class SoundManager {

    private Music music = null;
    
    private static SoundManager instance;
    
    public SoundManager(){
        instance = this;
    }
    
    public static SoundManager getInstance(){
        return instance;
    }

    public void playMusic() {
        Random rand = new Random();
        int randomNum = rand.nextInt((5 - 1) + 1) + 1;

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
        if (music != null){
            music.stop();
            music = null;
        }
    }
    
    public void restartSound(){
        if (AL.isCreated()){
            AL.destroy();
        }
    }

    public void playSound(String soundType) {
        try {
            String path = "";

            switch (soundType) {
                case "JUMP":
                    path = getClass().getResource("/Resources/Music/jump.ogg").getPath().replace("%20", " ");
                    break;
                case "GAMEOVER":
                    path = getClass().getResource("/Resources/Music/gameOver.ogg").getPath().replace("%20", " ");
                    break;
                case "COINPICKUP":
                    path = getClass().getResource("/Resources/Music/coinPickUp.ogg").getPath().replace("%20", " ");
                    break;
                case "BLOCKFALL":
                    path = getClass().getResource("/Resources/Music/blockFall.ogg").getPath().replace("%20", " ");
                    break;
                case "BUTTONPRESS":
                    path = getClass().getResource("/Resources/Music/buttonPress.ogg").getPath().replace("%20", " ");
                    break;
                case "BUTTONRELEASE":
                    path = getClass().getResource("/Resources/Music/buttonRelease.ogg").getPath().replace("%20", " ");
                    break;
                case "LEVERPULL":
                    path = getClass().getResource("/Resources/Music/leverPull.ogg").getPath().replace("%20", " ");
                    break;
                case "LEVERPUSH":
                    path = getClass().getResource("/Resources/Music/leverPush.ogg").getPath().replace("%20", " ");
                    break;
                case "WEIGHTDOWN":
                    path = getClass().getResource("/Resources/Music/weightDown.ogg").getPath().replace("%20", " ");
                    break;
                case "WEIGHTUP":
                    path = getClass().getResource("/Resources/Music/weightUp.ogg").getPath().replace("%20", " ");
                    break;
            }

            if (!path.isEmpty()){
                Sound sound = new Sound(path);

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
}
