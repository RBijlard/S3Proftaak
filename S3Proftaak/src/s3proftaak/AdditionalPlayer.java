/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author Berry-PC
 */
public class AdditionalPlayer{

    private static float gravity = 0.5f;
    private static float jumpStrength = -12;
    private static float speed = 4;

    private SpriteSheet playerSheet;
    private Animation animate;
    
    private static int interations = 5;
    private List<Rectangle> rectList;

    private Shape player;

    private float vX = 0;
    private float vY = 0;

    public AdditionalPlayer(List<Rectangle> rectList, int x, int y, int width, int height) throws SlickException {
        this.rectList = rectList;
        player = new Rectangle(x,y,width,height);
        playerSheet = new SpriteSheet(getClass().getResource("/Resources/player2_sprites.png").getPath().replace("%20", " "), 70, 93);
        animate = new Animation(playerSheet, 100);
    }

    public Shape getPlayer(){
        return this.player;
    }
    
    public Animation getAnimation(){
        return this.animate;
    }
    /**
     * Update method, runs every frame (insert logic here)
     *
     * @param container the window for the game
     * @param delta delta value, difference in milliseconds between each frame
     * @throws SlickException
     */
    public void update(GameContainer container, int delta) throws SlickException {
        moveHorizontal(container);
        moveVertical(container);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        animate.draw(player.getX(), player.getY());
    }


    private boolean isBlocked2(Shape s) {
        for (Rectangle r : rectList) {
            if (r.intersects(s) && r != s) {
                return true;
            }
        }
        return false;
    }

    public void moveHorizontal(GameContainer container) {
        //X acceleration
        if (container.getInput().isKeyDown(Input.KEY_A)) {
            vX = -speed;
        } else if (container.getInput().isKeyDown(Input.KEY_D)) {
            vX = speed;
        } else {
            vX = 0;
        }

        //X movement-collisions
        float vXtemp = vX / interations;
        for (int i = 0; i < interations; i++) {
            player.setX(player.getX() + vXtemp);
            if (isBlocked2(player)) {
                player.setX(player.getX() - vXtemp);
                vX = 0;
            }
        }
    }

    public void moveVertical(GameContainer container) {
        //Y acceleration
        vY += gravity;
        if (container.getInput().isKeyDown(Input.KEY_W)) {
            player.setY(player.getY() + 0.1f);
            if (isBlocked2(player)) {
                vY = jumpStrength;
            }
            player.setY(player.getY() - 0.1f);
        }
        //Y movement-collisions
        float vYtemp = vY / interations;
        for (int i = 0; i < interations; i++) {
            player.setY(player.getY() + vYtemp);
            if (isBlocked2(player)) {
                player.setY(player.getY() - vYtemp);
                vY = 0;
            }
        }
    }
}
