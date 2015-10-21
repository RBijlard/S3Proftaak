package s3proftaak.GameObjects;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.GameObjects.Interfaces.IRenderable;

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

    public Spike(float x, float y, float width, float height, int match) {
        super(x, y, width, height, match);
        this.hitbox = new Rectangle(this.x,this.y,this.width,this.height);
    }
    
    public void render(GameContainer gc, Graphics g){
        //render spike animation/img
    }
    
    @Override
    public String toString(){
        return super.toString() + " -- SPIKE";
    }
    
}
