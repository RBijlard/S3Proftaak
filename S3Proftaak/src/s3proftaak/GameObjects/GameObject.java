/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.GameObjects;

/**
 *
 * @author S33D
 */
public abstract class GameObject {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private String spritePath;
    private boolean collision;

    public GameObject(int posX, int posY, int width, int height, String spritePath, boolean collision, boolean trigger) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.spritePath = spritePath;
        this.collision = collision;
        this.trigger = trigger;
    }
    
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSpritePath() {
        return spritePath;
    }

    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public boolean isTrigger() {
        return trigger;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }
    private boolean trigger;
}
