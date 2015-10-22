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
import s3proftaak.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.GameObjects.Interfaces.IUpdateable;

/**
 *
 * @author Berry-PC
 */
public class Weight extends GameObject implements IStateChangeable, IRenderable, IUpdateable {

    private boolean isActive = false;
    private Image sprite;
    private Image sprite1;
    
    private int minus = 0;
    
    public Weight(float x, float y, float width, float height, int match){
        super(x, y, width, height, match);
        this.hitbox = new Rectangle(this.x,this.y,this.width,this.height);
        
        try {
            sprite = new Image("Resources/Levels/weightChained.png");
            sprite1 = new Image("Resources/Levels/chain.png"); 
        } catch (SlickException ex) {
            Logger.getLogger(Weight.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void update(GameContainer gc, int i) {
        this.hitbox = new Rectangle(this.x,this.y,this.width,this.height-minus);
        
        if (isActive){
            if (minus < this.height - 70){
                minus+=5;
            }
        }else{
            if (minus > 0){
                minus-=5;
            }
        }
            
    }
    
    @Override
    public void render(GameContainer gc, Graphics g){
        //render door animation/img
        sprite.draw(this.x,this.y - calculateOffset() - minus);
        
        for (int i=0;this.getHeight() - i*70 + 70 - minus > 0;i++){
            sprite1.draw(this.x, this.getHeight() - i*70 - minus);
        }
    }    
    
    public int calculateOffset(){
        return (int) (70-this.height);
    }
    
    @Override
    public boolean isActive(){
        return this.isActive;
    }
    
    @Override
    public void setActive(boolean active){
        this.isActive = active;
    }
    
    @Override
    public String toString(){
        return super.toString() + " -- WEIGHT " + this.match;
    }
    
}