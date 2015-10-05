/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author Berry-PC
 */
public class Player {
    
    private static float gravity = 0.5f;
    private static float jumpStrength = -15;
    private static float speed = 4;
    
    private static int interations = 5;
    
    private Shape player;
    private StaticLevel level;
    
    private float vX = 0;
    private float vY = 0;
    
    public Player(StaticLevel level){
        this.level = level;
    }
    /**
     * Initialise method, runs once on the startup of the game
     * @param container the window for the game
     * @throws SlickException 
     */
    public void init(GameContainer container) throws SlickException {
        player = new Rectangle(200,200,49,49);
    }

    /**
     * Update method, runs every frame (insert logic here)
     * @param container the window for the game
     * @param delta delta value, difference in milliseconds between each frame
     * @throws SlickException 
     */
    public void update(GameContainer container, int delta) throws SlickException {
        //Y acceleration
        vY += gravity;
        if(container.getInput().isKeyDown(Input.KEY_UP)){
            player.setY(player.getY()+0.1f);
            if(level.collidesWith(player)){
                vY = jumpStrength;
            }
            player.setY(player.getY()-0.1f);
        }
        //Y movement-collisions
        float vYtemp = vY/interations;
        for(int i = 0; i < interations; i++){            
            player.setY(player.getY() + vYtemp);
            if(level.collidesWith(player)){
                player.setY(player.getY() - vYtemp);
                vY=0;
            }
        }
        
        //X acceleration
        if(container.getInput().isKeyDown(Input.KEY_LEFT)){
            vX = -speed;
        }else if (container.getInput().isKeyDown(Input.KEY_RIGHT)){
            vX = speed;
        }
        else{
            vX = 0;
        }
        
        //X movement-collisions
        float vXtemp = vX/interations;
        for(int i = 0; i < interations; i++){  
            player.setX(player.getX() + vXtemp);
            if(level.collidesWith(player)){
                player.setX(player.getX() - vXtemp);
                vX = 0;
            }
        }
        
    }

    /**
     * Render method, draws every update on the screen (draw in here)
     * @param container the window for the game
     * @param g graphics of the screen
     * @throws SlickException 
     */
    public void render(GameContainer container, Graphics g) throws SlickException {
       g.setColor(Color.red);
       g.draw(player);
    }
}
