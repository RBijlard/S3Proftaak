package s3proftaak.GameObjects;


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
public class Block extends GameObject{

    public Block(float x, float y, float width, float height, int match) {
        super(x, y, width, height, match);
        this.hitbox = new Rectangle(this.x,this.y,this.width,this.height);
    }
    
    public void render(GameContainer gc, Graphics g){
        //render block animation/img
    }
    
    @Override
    public String toString(){
        return super.toString() + " -- BLOCK";
    }
    
}
