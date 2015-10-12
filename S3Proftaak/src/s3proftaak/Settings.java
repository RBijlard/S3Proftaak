/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

/**
 *
 * @author S33D
 */
public class Settings {
    
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
