/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.GameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.GameObjects.Interfaces.IRenderable;
import s3proftaak.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.Main;

/**
 *
 * @author Berry-PC
 */
public class Weight extends GameObject implements IStateChangeable, IRenderable {

    private boolean isActive = false;
    private Image sprite;
    private Image sprite1;
    
    public Weight(float x, float y, float width, float height, int match){
        super(x, y, width, height, match);
        this.hitbox = new Rectangle(this.x,this.y,this.width,this.height);
        this.changeImage(isActive);
    }
    
    public void finish(){
        Main.getApp().exit();
    }
    
    @Override
    public void render(GameContainer gc, Graphics g){
        //render door animation/img
        sprite.draw(this.x,this.y - calculateOffset());
        
        for (int i=0;this.getHeight() - i*70 + 70 > 0;i++){
            sprite1.draw(this.x, this.getHeight() - i*70);
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
        changeImage(active);
    }
    
    public void changeImage(boolean active){
        try{
            if(active){
                sprite = new Image("Resources/Levels/transparent.png");
                sprite1 = new Image("Resources/Levels/transparent.png");
            }
            else{
                sprite = new Image("Resources/Levels/weightChained.png");
                sprite1 = new Image("Resources/Levels/chain.png");           
            }
        }
        catch(SlickException ex){}
    }
    
    @Override
    public String toString(){
        return super.toString() + " -- WEIGHT " + this.match;
    }
    
}