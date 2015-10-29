/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.GameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import s3proftaak.GameObjects.Interfaces.IRenderable;

/**
 *
 * @author Berry-PC
 */
public class Star extends GameObject implements IRenderable{

    //lolipopred.png als star? :))
    public Star(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
