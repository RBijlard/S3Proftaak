package s3proftaak.GameObjects;


import java.util.List;
import s3proftaak.GameObjects.GameObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Berry-PC
 */
public class Button extends GameObject{

    private boolean isActive = false;
    
    public Button(float x, float y, float width, float height, int match) {
        super(x, y, width, height, match);
        this.hitbox = new Rectangle(this.x,this.y,this.width,this.height);
    }
    
    public void render(GameContainer gc, Graphics g){
        //render button animation/img
    }
    
    public boolean isActive(){
        return this.isActive;
    }
    
    public void setActive(boolean active){
        this.isActive = active;
        for(GameObject go : this.matchedObjects){
            
        }
    }
    
    @Override
    public String toString(){
        return super.toString() + " -- BUTTON";
    }
}
