/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author S33D
 */
public class Settings extends BasicGame{

    public Settings(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean soundMute;
    private boolean fullscreen;
    private String skinPath;

    public Settings(boolean soundMute, boolean fullscreen, String skinPath) {
        this.soundMute = soundMute;
        this.fullscreen = fullscreen;
        this.skinPath = skinPath;
    }

    public boolean isSoundMute() {
        return soundMute;
    }

    public void setSoundMute(boolean soundMute) {
        this.soundMute = soundMute;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public String getSkinPath() {
        return skinPath;
    }

    public void setSkinPath(String skinPath) {
        this.skinPath = skinPath;
    }
}
