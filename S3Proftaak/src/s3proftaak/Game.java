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
import s3proftaak.GameObjects.Interfaces.IPressable;
import s3proftaak.GameObjects.Interfaces.IRenderable;
import s3proftaak.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.GameObjects.Lever;
import s3proftaak.GameObjects.Spike;
import s3proftaak.GameObjects.Weight;

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
    private int amountOfPlayers = 0;
    private Character main_character;
    private GameObject most_left_object;
    private String mapname;

    public Game(String title, int amountOfPlayers, String mapname) {
        super(title);
        this.amountOfPlayers = amountOfPlayers;
        this.mapname = mapname;
    }

    public TiledMap getMap() {
        return this.map;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        //initialise map, players and objects
        this.path = getClass().getResource("/Resources/Levels/" + this.mapname).getPath().replace("%20", " ");

        //map and list
        this.map = new TiledMap(path);
        this.gameObjects = new ArrayList<>();

        //blocks
        for (int i = 0; i < map.getObjectCount(0); i++) {
            GameObject block = new Block(map.getObjectX(0, i), map.getObjectY(0, i), map.getObjectWidth(0, i), map.getObjectHeight(0, i), -1);
            this.gameObjects.add(block);
        }

        //spikes
        for (int i = 0; i < map.getObjectCount(5); i++) {
            GameObject spike = new Spike(map.getObjectX(5, i), map.getObjectY(5, i), map.getObjectWidth(5, i), map.getObjectHeight(5, i), -1);
            this.gameObjects.add(spike);
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
            this.gameObjects.add(door);
        }

        //levers TODO
        for (int i = 0; i < map.getObjectCount(3); i++) {
            int match = this.getProperty(map, 3, i, "lever");
            GameObject lever = new Lever(map.getObjectX(3, i), map.getObjectY(3, i), map.getObjectWidth(3, i), map.getObjectHeight(3, i), match);
            this.gameObjects.add(lever);
        }

        //weights TODO
        for (int i = 0; i < map.getObjectCount(4); i++) {
            int match = this.getProperty(map, 4, i, "weight");
            GameObject weight = new Weight(map.getObjectX(4, i), map.getObjectY(4, i), map.getObjectWidth(4, i), map.getObjectHeight(4, i), match);
            this.gameObjects.add(weight);
        }
        
        // Deze sick dubbele for lus linked alle gameobjects die met elkaar gelinked moeten worden
        for (GameObject g1 : this.getGameObjects()){
            for (GameObject g2 : this.getGameObjects()) {
                if (g1 != g2){
                    if (g2 instanceof IPressable) {
                        int doorMatch = g2.getMatch();
                        if (doorMatch == g1.getMatch()) {
                            g2.addMatchedObject(g1);
                            g1.addMatchedObject(g2);
                        }
                    }
                }
            }
        }
        
        
        for (int i = 1; i < this.amountOfPlayers; i++) {
            this.gameObjects.add(new Character(this, (72f * i + 200f), 150f, 70f, 93f, i, -1)); // + 500f, 100f
        }
        main_character = new Character(this, 72f + 100f, 150f, 70f, 93f, 0, -1); // + 400f, 100f
        this.gameObjects.add(main_character);

        for (GameObject go : this.gameObjects) {
            System.out.println(go.toString());
        }

        most_left_object = new Block(72f, 500f, 70f, 93f, -1);
        for(GameObject go : this.gameObjects){
            if(go.getX() < most_left_object.getX()){
                most_left_object.setX(go.getX());
            }
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
            if (go instanceof IPressable) {
                boolean bool = false;
                Rectangle r = go.getRect();
                Rectangle temp = new Rectangle(r.getX(), r.getY() - 1, r.getWidth(), r.getHeight());
                for (GameObject co : this.gameObjects) {
                    if (co instanceof Character) {
                        if (co.getRect().intersects(temp)) {
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
        this.map.render(0 - (int) main_character.getOffsetX(), 0);

        for (GameObject go : this.gameObjects) {

            grphcs.draw(go.getRect());

            if (go instanceof IRenderable) {
                ((IRenderable) go).render(gc, grphcs);
            }
        }
    }

    public List<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    private int getProperty(TiledMap map, int layer, int tilenumber, String type) {
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
        if (((IPressable) go).isActive()) {
            ((IPressable) go).setActive(false);
            for (GameObject mo : ((IPressable) go).getMatchedObjects()) {
                this.checkMatchedObject(mo);
            }
        }
    }

    public void checkMatchedObject(GameObject mo) {
        if (mo instanceof IStateChangeable) {
            if (((IStateChangeable) mo).isActive()) {
                ((IStateChangeable) mo).setActive(false);
            }
        }
    }
}
