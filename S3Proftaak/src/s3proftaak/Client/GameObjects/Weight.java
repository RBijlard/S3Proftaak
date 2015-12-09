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
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.Client.GameObjects.Interfaces.IUpdateable;

/**
 *
 * @author Berry-PC
 */
public class Weight extends GameObject implements IStateChangeable, IRenderable, IUpdateable {

    private boolean isActive = false;
    private Image sprite;
    private Image sprite1;
    
    private int minus = 0;
    private float height;
    
    public Weight(float x, float y, float width, float height){
        super(x, y, width, height);
        
        this.height = height;
        
        try {
            sprite = new Image("Resources/Levels/weightChained.png");
            sprite1 = new Image("Resources/Levels/chain.png"); 
        } catch (SlickException ex) {
            Logger.getLogger(Weight.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void update(GameContainer gc, int i) {
        this.getRect().setHeight(height - minus);
        
        if (isActive){
            if (minus < height - 70){
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
        for (int i=0;height - i*70 + 70 - minus > 0;i++){
            sprite1.draw(this.getRect().getX(), height - i*70 - 70 - minus);
        }
        
        sprite.draw(this.getRect().getX(),this.getRect().getY() - calculateOffset() - minus);
    }    
    
    public int calculateOffset(){
        return (int) (70-height);
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
        return super.toString() + " -- WEIGHT " + this.getMatches();
    }
    
}