/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.GameObjects;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.GameObjects.Interfaces.IRenderable;

/**
 *
 * @author Berry-PC
 */
public class Star extends GameObject implements IRenderable {

    private Image sprite;
    private boolean isActive = true;

    public Star(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.hitbox = new Rectangle(this.x, this.y, this.width, this.height);
        try {
            this.sprite = new Image("Resources/Levels/lollipopRed.png");
        } catch (SlickException ex) {
            Logger.getLogger(Star.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.isActive = true;
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        //render star animation/img
        sprite.draw(this.x, this.y - calculateOffset());
    }

    public int calculateOffset() {
        return (int) (70 - this.height);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
        if (this.isActive == true) {
            this.hitbox = new Rectangle(this.x,this.y,this.width,this.height);
            try {
                this.sprite = new Image("Resources/Levels/lollipopRed.png");
            } catch (SlickException ex) {
                Logger.getLogger(Star.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //this.hitbox = null;
            //this.sprite = null;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " -- STAR " + this.getMatches();
    }

}
