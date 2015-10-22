package s3proftaak.GameObjects;


import java.util.ArrayList;
import java.util.List;
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
public abstract class GameObject {
    float x, y, width, height;
    int match;
    Rectangle hitbox;
    List<GameObject> matchedObjects;
    
    public GameObject(float x, float y, float width, float height, int match){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.match = match;
        this.matchedObjects = new ArrayList<>();
        this.hitbox = new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    public void addMatchedObject(GameObject match){
        if(!this.matchedObjects.contains(match)){
            this.matchedObjects.add(match);
        }
        
    } 
    
    public List<GameObject> getMatchedObjects(){
        return this.matchedObjects;
    }
    
    public int getMatch(){
        return this.match;
    }
    
    public void updateHitbox(){
        this.hitbox.setX(this.x);
        this.hitbox.setY(this.y);
    }
    
    public Rectangle getRect(){
        return this.hitbox;
    }
    
    public float getX(){
        return this.x;
    }
    
    public float getY(){
        return this.y;
    }
    
    public float getWidth(){
        return this.width;
    }
    
    public float getHeight(){
        return this.height;
    }
    
    public void setX(float x){
        this.x = x;
    }
    
    public void setY(float y){
        this.y = y;
    }
    
    public void setWidth(float width){
        this.width = width;
    }
    
    public void setHeight(float height){
        this.height = height;
    }
    
    @Override
    public String toString(){
        return this.x + "," + this.y + " - " + this.width + " x " + this.height;
    }
}
