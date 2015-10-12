/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.GameObjects;

/**
 *
 * @author Stan
 */
public class Lever extends GameObject {

    private boolean isPulled;
    private String altSpritePath;

    public Lever(boolean isPulled, String altSpritePath, int posX, int posY, int width, int height, String spritePath, boolean collision, boolean trigger) {
        super(posX, posY, width, height, spritePath, collision, trigger);
        this.isPulled = isPulled;
        this.altSpritePath = altSpritePath;
    }

    public boolean isPulled() {
        return isPulled;
    }

    public void setIsPulled(boolean isPulled) {
        this.isPulled = isPulled;
    }

    public String getAltSpritePath() {
        return altSpritePath;
    }

    public void setAltSpritePath(String altSpritePath) {
        this.altSpritePath = altSpritePath;
    }
    
    
}
