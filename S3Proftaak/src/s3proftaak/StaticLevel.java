/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author Berry-PC
 */
public class StaticLevel{
    
    private Shape levelBase, platform;
    
    /**
     * Initialise method, runs once on the startup of the game
     * @param container the window for the game
     * @throws SlickException 
     */
    public void init(GameContainer container) throws SlickException {
        float[] polygonPoint = new float[]
        {
            0,0,
            50,0,
            50,550,
            750,550,
            750,350,
            600,350,
            600,300,
            750,300,
            750,0,
            800,0,
            800,600,
            0,600            
        };
        levelBase = new Polygon(polygonPoint);
        platform = new Rectangle(500,400,100,50);
    }

    /**
     * Update method, runs every frame (insert logic here)
     * @param container the window for the game
     * @param delta delta value, difference in milliseconds between each frame
     * @throws SlickException 
     */
    public void update(GameContainer container, int delta) throws SlickException {
        
    }

    /**
     * Render method, draws every update on the screen (draw in here)
     * @param container the window for the game
     * @param g graphics of the screen
     * @throws SlickException 
     */
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setColor(Color.green);
        
        g.draw(levelBase);
        g.draw(platform);
    }
    
    public boolean collidesWith(Shape s){
       return levelBase.intersects(s) || platform.intersects(s); 
    }
}
