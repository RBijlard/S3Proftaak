/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author panos
 */
public class TestTileMapTiled extends BasicGame {

    private static float gravity = 0.5f;
    private static float jumpStrength = -15;
    private static float speed = 4;
    private Shape player;
    private float vX = 0;
    private float vY = 0;
    private static int interations = 5;
    private TiledMap grassMap;
    private float x = 70f, y = 70f;
    private List<Rectangle> rectList;

    public TestTileMapTiled(String title) {
        super(title);
    }

    public static void main(String[] arguments) {
        try {
            AppGameContainer app = new AppGameContainer(new TestTileMapTiled("Game"));
                app.setDisplayMode(1500, 1500, false);
            app.setTargetFrameRate(60);
            app.start();
        } catch (SlickException e) {
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        player = new Rectangle(80, 80, 50, 50);       
        grassMap = new TiledMap("C:\\Users\\Berry-PC\\Desktop\\backup 07-09-2015\\SCHOOL\\HBO ICT\\LEERJAAR 2 PERIODE 1\\JSF3\\WEEK4\\testTileMapTiled\\Data\\tilemapBerry2.tmx");
        rectList = new ArrayList<Rectangle>();
        for(int i = 0; i < grassMap.getObjectCount(0); i++){
            Rectangle r = new Rectangle(grassMap.getObjectX(0, i) ,grassMap.getObjectY(0, i),
            grassMap.getObjectWidth(0, i), grassMap.getObjectHeight(0, i));
            System.out.println(r);
            rectList.add(r);
        }

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        jumpStrength = -(width / 110);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        moveHorizontal(container);
        moveVertical(container);
        
        if (container.getInput().isKeyDown(Input.KEY_ESCAPE)) {
            closeGame();
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        grassMap.render(0, 0);
        g.draw(player);
//        for(Rectangle r : rectList){
//            g.draw(r);
//        }
    }
    
    private boolean isBlocked2(Shape s){        
        for(Rectangle r : rectList){
            if(r.intersects(s)){
                return true;
            }
        }
        return false;
    }

    
    public void moveHorizontal(GameContainer container) {
        //X acceleration
        if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
            vX = -speed;
        } else if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
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
        if (container.getInput().isKeyDown(Input.KEY_UP)) {
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

    public static void closeGame() {
        System.exit(0);
    }
}
