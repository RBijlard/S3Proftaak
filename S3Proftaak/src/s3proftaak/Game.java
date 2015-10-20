package s3proftaak;

import s3proftaak.GameObjects.Button;
import s3proftaak.GameObjects.Character;
import s3proftaak.GameObjects.Door;
import s3proftaak.GameObjects.Block;
import s3proftaak.GameObjects.GameObject;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Berry-PC
 */
public class Game extends BasicGame {

    private List<GameObject> gameObjects;
    private TiledMap map;
    private float x = 70f, y = 70f;
    private String path;
    private Character main_character;

    public Game(String title) {
        super(title);
    }

    public Character getMainCharacter(){
        return this.main_character;
    }
    
    public TiledMap getMap(){
        return this.map;
    }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        //initialise map, players and objects
        this.path = getClass().getResource("/Resources/testFullMap1920.tmx").getPath().replace("%20", " ");

        //map and list
        this.map = new TiledMap(path);
        this.gameObjects = new ArrayList<GameObject>();

        //blocks
        for (int i = 0; i < map.getObjectCount(0); i++) {
            GameObject block = new Block(map.getObjectX(0, i), map.getObjectY(0, i), map.getObjectWidth(0, i), map.getObjectHeight(0, i), -1);
            this.gameObjects.add(block);
        }

        //buttons
        for (int i = 0; i < map.getObjectCount(1); i++) {
            int match = this.getProperty(map, 1, i, "button");
            GameObject button = new Button(map.getObjectX(1, i), map.getObjectY(1, i), map.getObjectWidth(1, i), map.getObjectHeight(1, i), match);
            this.gameObjects.add(button);
        }

        //doors
        for (int i = 0; i < map.getObjectCount(2); i++) {
            int match = this.getProperty(map, 2, i, "door");
            GameObject door = new Door(map.getObjectX(2, i), map.getObjectY(2, i), map.getObjectWidth(2, i), map.getObjectHeight(2, i), match);

            for (GameObject go : this.getGameObjects()) {
                if (go instanceof Button) {
                    int doorMatch = go.getMatch();
                    if (doorMatch == match) {
                        go.addMatchedObject(door);
                        door.addMatchedObject(go);
                    }
                }
            }

            this.gameObjects.add(door);
        }

        //characters
        main_character = new Character(this, 72f, 500f, 70f, 93f, 0, -1);
        GameObject characterTwo = new Character(this, 144f, 500f, 70f, 93f, 1, -1);
        GameObject characterThree = new Character(this, 216f, 500f, 70f, 93f, 2, -1);
        this.gameObjects.add(main_character);
        this.gameObjects.add(characterTwo);
        this.gameObjects.add(characterThree);

        for (GameObject go : this.gameObjects) {
            System.out.println(go.toString());
        }
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        //update game and player
        
        for (GameObject go : this.gameObjects) {
            if (go instanceof Character) {
                //move all characters
                ((Character) go).update(gc, i);
            }
            if (go instanceof Button /*|| go instanceof Lever*/) {
                boolean bool = false;
                Rectangle r = go.getRect();
                Rectangle temp = new Rectangle(r.getX(), r.getY() - 1, r.getWidth(), r.getHeight());
                for (GameObject co : this.gameObjects) {
                    if (co instanceof Character) {
                        if(co.getRect().intersects(temp)){
                            bool = true;
                        }
                    }
                }
                if (!bool) {
                    this.checkMatchedObjects(go);
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        //render game and player
        this.map.render(0,0);
        
        for (GameObject go : this.gameObjects) {

            grphcs.draw(go.getRect());

            if (go instanceof Button) {
                //render buttons
                ((Button) go).render(gc, grphcs);
            }
            if (go instanceof Door) {
                //render doors
                ((Door) go).render(gc, grphcs);
            }
            if (go instanceof Character) {
                //render characters
                ((Character) go).render(gc, grphcs);
            }
        }
    }

    public List<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    public int getProperty(TiledMap map, int layer, int tilenumber, String type) {
        if (map.getObjectProperty(layer, tilenumber, type, "desc") != null) {
            String match = map.getObjectProperty(layer, tilenumber, type, "desc");
            try {
                int matchNumber = Integer.parseInt(match);
                return matchNumber;
            } catch (Exception x) {
                System.out.println(x.toString());
                return -1;
            }
        }
        return -1;
    }

    public void checkMatchedObjects(GameObject go) {
        if (go instanceof Button) {
            if(((Button)go).isActive()){
                ((Button) go).changeImage(false);
                ((Button) go).setActive(false);
                for (GameObject mo : ((Button) go).getMatchedObjects()) {
                    this.checkMatchedObject(mo);
                }
            }
        }
//        if (go instanceof Lever) {
//            ((Lever) go).changeImage(false);
//            for (GameObject mo : ((Button) go).getMatchedObjects()) {
//                this.checkMatchedObject(mo);            
//            }
//        }
    }

    public void checkMatchedObject(GameObject mo) {
        if (mo instanceof Door) {
            if(((Door) mo).isActive()){
                System.out.println("setting door false");
                ((Door) mo).changeImage(false);
                ((Door) mo).setActive(false);
            }
        }

    }
}
