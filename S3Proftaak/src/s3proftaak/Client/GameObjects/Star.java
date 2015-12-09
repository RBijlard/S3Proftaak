/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.GameObjects;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import s3proftaak.Client.GameObjects.Interfaces.IRemoteUpdatable;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;

/**
 *
 * @author Berry-PC
 */
public class Star extends GameObject implements IRemoteUpdatable, IRenderable {

    private Image sprite;
    private boolean removed;

    public Star(float x, float y, float width, float height) {
        super(x, y, width, height);
        try {
            this.sprite = new Image("Resources/Levels/lollipopRed.png");
        } catch (SlickException ex) {
            Logger.getLogger(Star.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        //render star animation/img
        if (sprite != null){
            sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset());
        }
        
    }

    public int calculateOffset() {
        return (int) (70 - this.getRect().getHeight());
    }

    @Override
    public void setActive(boolean active) {
        this.sprite = null;
        this.removed = true;
    }
    
    public boolean isRemoved(){
        return this.removed;
    }

    @Override
    public String toString() {
        return super.toString() + " -- STAR " + this.getMatches();
    }

}
