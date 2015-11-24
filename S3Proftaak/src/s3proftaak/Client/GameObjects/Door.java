package s3proftaak.Client.GameObjects;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.Client.ClientAdministration;

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
    
    public Door(float x, float y, float width, float height){
        super(x, y, width, height);
        this.hitbox = new Rectangle(this.x,this.y,this.width,this.height);
        this.changeImage(isActive);
    }
    
    public void finish(){
        ClientAdministration.getGame().doFinish();
    }
    
    @Override
    public void render(GameContainer gc, Graphics g){
        //render door animation/img
        sprite.draw(this.x,this.y - calculateOffset());
        sprite1.draw(this.x,this.y + calculateOffset());
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
    
    private void changeImage(boolean active){
        try{
            if(active){
                sprite = new Image("Resources/Levels/door_openMid.png");
                sprite1 = new Image("Resources/Levels/door_openTop.png");
            }
            else{
                sprite = new Image("Resources/Levels/door_closedMid.png");
                sprite1 = new Image("Resources/Levels/door_closedTop.png");           
            }
        }
        catch(SlickException ex){}
    }
    
    @Override
    public String toString(){
        return super.toString() + " -- DOOR " + this.getMatches();
    }
    
}
