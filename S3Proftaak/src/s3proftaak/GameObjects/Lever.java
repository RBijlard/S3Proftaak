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
import s3proftaak.GameObjects.Interfaces.IPressable;
import s3proftaak.GameObjects.Interfaces.IRenderable;

/**
 *
 * @author Berry-PC
 */
public class Lever extends GameObject implements IPressable, IRenderable {

    private boolean isActive = false;
    private Image sprite;
    
    public Lever(float x, float y, float width, float height, int match) {
        super(x, y, width, height, match);
        this.hitbox = new Rectangle(this.x,this.y,this.width,this.height);
        this.changeImage(isActive);
    }
    
    @Override
    public void render(GameContainer gc, Graphics g){
        //render button animation/img
        sprite.draw(this.x,this.y - calculateOffset());        
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
        
//            if (!getMatchedObjects().isEmpty()){
//                    boolean enable = true;
//                    for (GameObject mo : getMatchedObjects().get(0).getMatchedObjects()){
//                        if(mo instanceof Button){
//                            if (!((Button) mo).isActive()){
//                                enable = false;
//                                break;
//                            }
//                        }
//                    }
//                    
//                    if (enable){
//                        ((Door)getMatchedObjects().get(0)).setActive(true);
//                    }
//                    
//            }
    }
    
    private void changeImage(boolean active){
        try{
            if(active){
                this.sprite = new Image("Resources/Levels/switchLeft.png");
            }
            else{
                this.sprite = new Image("Resources/Levels/switchRight.png");            
            }
        }
        catch(SlickException ex){}
    }
    
    @Override
    public String toString(){
        return super.toString() + " -- LEVER " + this.match;
    }
}