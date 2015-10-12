/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author S33D
 */
public class Game extends BasicGame {

    private static float gravity = 0.5f;
    private static float jumpStrength = -15;
    private static float speed = 4;
    private Player character;
    private AdditionalPlayer additional_character;
    private float vX = 0;
    private float vY = 0;
    private static int interations = 5;
    private TiledMap grassMap;
    private float x = 70f, y = 70f;
    private List<Rectangle> rectList;
    private String path;

    public Game(String title) {
        super(title);
        if(title.equals("Game1")){
            path = getClass().getResource("/Resources/tilemapBerry2.tmx").getPath().replace("%20", " ");
        }
        if(title.equals("Game2")){
            path = getClass().getResource("/Resources/tilemapBo.tmx").getPath().replace("%20", " ");
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {

        URL url = getClass().getResource("/Resources/");
        if (url == null) {
            // error - missing folder
        } else {
            File dir = null;
            try {
                dir = new File(url.toURI());
            } catch (URISyntaxException ex) {
                Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            for (File nextFile : dir.listFiles()) {
                System.out.println("PATH" + nextFile.getPath());
            }
        }

        grassMap = new TiledMap(path);
        rectList = new ArrayList<Rectangle>();
        for (int i = 0; i < grassMap.getObjectCount(0); i++) {
            Rectangle r = new Rectangle(grassMap.getObjectX(0, i), grassMap.getObjectY(0, i),
                    grassMap.getObjectWidth(0, i), grassMap.getObjectHeight(0, i));
            System.out.println(r);
            rectList.add(r);
        }
        
        character = new Player(rectList, 80, 80, 50, 50);
        additional_character = new AdditionalPlayer(rectList, 160, 80, 50, 50);
        
        rectList.add((Rectangle) character.getPlayer());
        rectList.add((Rectangle) additional_character.getPlayer());
        
        
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        character.moveHorizontal(container);
        character.moveVertical(container);
        
        additional_character.moveHorizontal(container);
        additional_character.moveVertical(container);
        
        if (container.getInput().isKeyDown(Input.KEY_ESCAPE)) {
            closeGame();
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        grassMap.render(0, 0);
        g.setColor(Color.white);
        g.draw(character.getPlayer());
        g.setColor(Color.green);
        g.draw(additional_character.getPlayer());
//        for(Rectangle r : rectList){
//            g.draw(r);
//        }
    }
    
    public static void closeGame() {
        System.exit(0);
    }
}
