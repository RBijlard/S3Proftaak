package s3proftaak.Client.GameObjects;

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

    public Block(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    
    @Override
    public String toString(){
        return super.toString() + " -- BLOCK";
    }
    
}
