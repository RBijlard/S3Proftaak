package s3proftaak.Client.GameObjects;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Berry-PC
 */
public class Spike extends GameObject implements IRenderable{

    public Spike(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.hitbox = new Rectangle(this.x,this.y,this.width,this.height);
    }
    
    @Override
    public void render(GameContainer gc, Graphics g){
        //render spike animation/img
    }
    
    @Override
    public String toString(){
        return super.toString() + " -- SPIKE";
    }
    
}
