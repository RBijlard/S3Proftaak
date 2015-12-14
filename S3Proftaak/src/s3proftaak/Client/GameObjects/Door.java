package s3proftaak.Client.GameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.ResourceManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Berry-PC
 */
public class Door extends GameObject implements IStateChangeable, IRenderable {

    private boolean isActive = false;
    private Image sprite;
    private Image sprite1;
    private boolean finished;

    public Door(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.changeImage(isActive);
        finished = false;
    }

    public void finish() {
        if (!finished) {
            finished = true;
            ClientAdministration.getInstance().getGame().doFinish();
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        //render door animation/img
        sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset());
        sprite1.draw(this.getRect().getX(), this.getRect().getY() + calculateOffset());
    }

    public int calculateOffset() {
        return (int) (70 - this.getRect().getHeight());
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public void setActive(boolean active) {
        this.isActive = active;
        changeImage(active);
    }

    private void changeImage(boolean active) {
        try {
            if (active) {
                sprite = ResourceManager.getImage(ResourceManager.Images.DOOR_OPENMID);
                sprite1 = ResourceManager.getImage(ResourceManager.Images.DOOR_OPENTOP);
            } else {
                sprite = ResourceManager.getImage(ResourceManager.Images.DOOR_CLOSEDMID);
                sprite1 = ResourceManager.getImage(ResourceManager.Images.DOOR_CLOSEDTOP);
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public String toString() {
        return super.toString() + " -- DOOR " + this.getMatches();
    }

}
