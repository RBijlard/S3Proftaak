package s3proftaak;

import java.sql.SQLException;
import s3proftaak.GameObjects.Button;
import s3proftaak.GameObjects.Character;
import s3proftaak.GameObjects.Door;
import s3proftaak.GameObjects.Block;
import s3proftaak.GameObjects.GameObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import s3proftaak.GameObjects.Interfaces.IPressable;
import s3proftaak.GameObjects.Interfaces.IRenderable;
import s3proftaak.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.GameObjects.Interfaces.IUpdateable;
import s3proftaak.GameObjects.Lever;
import s3proftaak.GameObjects.MoveableBlock;
import s3proftaak.GameObjects.Spike;
import s3proftaak.GameObjects.Star;
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

    private Score score;
    private long startTime, endTime;
    private int starsCollected = 0;

    private float baseWidht = 1920;
    private float baseHight = 1080;

    private static Music music = null;
    private static Sound sound = null;

    private boolean gameOver;
    private static boolean dead = false;

    public Game(String title, int amountOfPlayers, String mapname) {
        super(title);
        this.amountOfPlayers = amountOfPlayers;
        this.mapname = mapname;
        gameOver = false;
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
            GameObject block = new Block(map.getObjectX(0, i), map.getObjectY(0, i), map.getObjectWidth(0, i), map.getObjectHeight(0, i));
            this.gameObjects.add(block);
        }

        //spikes
        for (int i = 0; i < map.getObjectCount(5); i++) {
            GameObject spike = new Spike(map.getObjectX(5, i), map.getObjectY(5, i), map.getObjectWidth(5, i), map.getObjectHeight(5, i));
            this.gameObjects.add(spike);
        }

        //buttons
        for (int i = 0; i < map.getObjectCount(1); i++) {
            GameObject button = new Button(map.getObjectX(1, i), map.getObjectY(1, i), map.getObjectWidth(1, i), map.getObjectHeight(1, i));
            button.setMatches(this.getProperty(map, 1, i, "button"));
            this.gameObjects.add(button);
        }

        //doors
        for (int i = 0; i < map.getObjectCount(2); i++) {
            GameObject door = new Door(map.getObjectX(2, i), map.getObjectY(2, i), map.getObjectWidth(2, i), map.getObjectHeight(2, i));
            door.setMatches(this.getProperty(map, 2, i, "door"));
            this.gameObjects.add(door);
        }

        //levers
        for (int i = 0; i < map.getObjectCount(3); i++) {
            GameObject lever = new Lever(map.getObjectX(3, i), map.getObjectY(3, i), map.getObjectWidth(3, i), map.getObjectHeight(3, i));
            lever.setMatches(this.getProperty(map, 3, i, "lever"));
            this.gameObjects.add(lever);
        }

        //weights
        for (int i = 0; i < map.getObjectCount(4); i++) {
            GameObject weight = new Weight(map.getObjectX(4, i), map.getObjectY(4, i), map.getObjectWidth(4, i), map.getObjectHeight(4, i));
            weight.setMatches(this.getProperty(map, 4, i, "weight"));
            this.gameObjects.add(weight);
        }

        //moveblock
        for (int i = 0; i < map.getObjectCount(6); i++) {
            GameObject moveblock = new MoveableBlock(map.getObjectX(6, i), map.getObjectY(6, i), map.getObjectWidth(6, i), map.getObjectHeight(6, i));
            this.gameObjects.add(moveblock);
        }

        //stars
        for (int i = 0; i < map.getObjectCount(7); i++) {
            GameObject star = new Star(map.getObjectX(7, i), map.getObjectY(7, i), map.getObjectWidth(7, i), map.getObjectHeight(7, i));
            this.gameObjects.add(star);
        }

        // Deze sick dubbele for lus linked alle gameobjects die met elkaar gelinked moeten worden
        for (GameObject g1 : this.getGameObjects()) {
            for (GameObject g2 : this.getGameObjects()) {
                if (g1 != g2) {
                    if (g2 instanceof IPressable && g1 instanceof IStateChangeable) {

                        for (int possibleMatch : g1.getMatches()) {
                            if (g2.getMatches().contains(possibleMatch)) {
                                System.out.println(g1.toString() + " gekoppeld met " + g2.toString());
                                g2.addMatchedObject(g1);
                                g1.addMatchedObject(g2);
                            }
                        }
                    }
                }
            }
        }

        for (int i = 1; i < this.amountOfPlayers; i++) {
            this.gameObjects.add(new Character(this, (72f * i + 500f), 72f, 70f, 93f, i));
        }
        main_character = new Character(this, 500f, 72f, 70f, 93f, 0);
        this.gameObjects.add(main_character);

        // Moet keer weg
        for (GameObject go : this.gameObjects) {
            System.out.println(go.toString());
        }

        // Dit gebruiken we niet? Kan weg?
        most_left_object = new Block(72f, 500f, 70f, 93f);
        for (GameObject go : this.gameObjects) {
            if (go.getX() < most_left_object.getX()) {
                most_left_object.setX(go.getX());
            }
        }

        if (!dead) {
            Game.playMusic();
        }

        startTime = System.currentTimeMillis();
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        //update game and player

        List<GameObject> tempStarList = new ArrayList<>();

        for (GameObject go : this.gameObjects) {
            if (go instanceof Star) {
                if (((Star) go).isActive() == false) {
                    tempStarList.add(go);
                    this.starsCollected++;
                }
            }
        }

        for (GameObject go : tempStarList) {
            this.gameObjects.remove(go);
        }

        for (GameObject go : this.gameObjects) {

            if (go instanceof IUpdateable) {
                //move all characters & weights
                ((IUpdateable) go).update(gc, i);
            }

            if (go instanceof IPressable && !(go instanceof Lever)) {
                boolean bool = false;
                Rectangle r = go.getRect();
                Rectangle temp = new Rectangle(r.getX(), r.getY() - 1, r.getWidth(), r.getHeight());

                for (GameObject co : this.gameObjects) {
                    if (co instanceof Character || co instanceof MoveableBlock) {
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
        //scaling the game to your resolution
        grphcs.scale(Display.getWidth() / this.baseWidht, Display.getHeight() / this.baseHight);
        grphcs.setBackground(new Color(0, 191, 255));
        for (GameObject go : this.gameObjects) {
            // Teken hitboxes, moet keer weg
            //grphcs.draw(go.getRect());

            if (go instanceof IRenderable) {
                ((IRenderable) go).render(gc, grphcs);
            }
        }
        //render game and player
        for (int i = 0; i < map.getLayerCount(); i++) {
            this.map.render(0 - (int) main_character.getOffsetX(), i);
        }
    }

    public List<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    private ArrayList<Integer> getProperty(TiledMap map, int layer, int tilenumber, String type) {
        ArrayList<Integer> matches = new ArrayList<>();

        if (map.getObjectProperty(layer, tilenumber, type, "desc") != null) {
            String match = map.getObjectProperty(layer, tilenumber, type, "desc");
            match += " ";

            for (String m : match.split(" ")) {
                try {
                    int matchNumber = Integer.parseInt(m);
                    matches.add(matchNumber);
                } catch (Exception x) {
                    System.out.println(x.toString());
                }

            }
        }
        return matches;
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

    public Score getScore() {
        return this.score;
    }

    public void doFinish() {

        if (!gameOver) {
            gameOver = true;

            Game.playSound("GAMEOVER");

            endTime = System.currentTimeMillis();

            long timeDiff = endTime - startTime;

            try {
                this.score = new Score((int) timeDiff, starsCollected, Main.getAccount().getUsername(), this.mapname);
                DBConnect.getInstance().insertScore(this.score);
            } catch (SQLException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }

            Main.getApp().exit();
            Main.changeScreen(Main.Screens.Highscores.load());
            Main.playMenuMusic();
        }
    }

    public static void setDead() {
        dead = true;
    }

    public static void playMusic() {
        Thread musicThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Random rand = new Random();
                int randomNum = rand.nextInt((5 - 1) + 1) + 1;

                String path = "";

                try {
                    switch (randomNum) {
                        case 1:
                            path = getClass().getResource("/Resources/Music/music1.ogg").getPath().replace("%20", " ");
                            break;
                        case 2:
                            path = getClass().getResource("/Resources/Music/music2.ogg").getPath().replace("%20", " ");
                            break;
                        case 3:
                            path = getClass().getResource("/Resources/Music/music3.ogg").getPath().replace("%20", " ");
                            break;
                        case 4:
                            path = getClass().getResource("/Resources/Music/music4.ogg").getPath().replace("%20", " ");
                            break;
                        case 5:
                            path = getClass().getResource("/Resources/Music/music5.ogg").getPath().replace("%20", " ");
                            break;
                    }
                    music = new Music(path);
                    music.loop(1, 0.25f);

                } catch (SlickException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        musicThread.start();
    }

    public static void playSound(String soundType) {
        Thread soundThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String path = "";

                    switch (soundType) {
                        case "JUMP":
                            path = getClass().getResource("/Resources/Music/jump.ogg").getPath().replace("%20", " ");
                            break;
                        case "GAMEOVER":
                            path = getClass().getResource("/Resources/Music/gameOver.ogg").getPath().replace("%20", " ");
                            break;
                        case "COINPICKUP":
                            path = getClass().getResource("/Resources/Music/coinPickUp.ogg").getPath().replace("%20", " ");
                            break;
                        case "BLOCKFALL":
                            path = getClass().getResource("/Resources/Music/blockFall.ogg").getPath().replace("%20", " ");
                            break;
                        case "BUTTONPRESS":
                            path = getClass().getResource("/Resources/Music/buttonPress.ogg").getPath().replace("%20", " ");
                            break;
                        case "BUTTONRELEASE":
                            path = getClass().getResource("/Resources/Music/buttonRelease.ogg").getPath().replace("%20", " ");
                            break;
                        case "LEVERPULL":
                            path = getClass().getResource("/Resources/Music/leverPull.ogg").getPath().replace("%20", " ");
                            break;
                        case "LEVERPUSH":
                            path = getClass().getResource("/Resources/Music/leverPush.ogg").getPath().replace("%20", " ");
                            break;
                        case "WEIGHTDOWN":
                            path = getClass().getResource("/Resources/Music/weightDown.ogg").getPath().replace("%20", " ");
                            break;
                        case "WEIGHTUP":
                            path = getClass().getResource("/Resources/Music/weightUp.ogg").getPath().replace("%20", " ");
                            break;
                    }

                    System.out.println("Playing: " + soundType);

                    sound = new Sound(path);

                    if (music != null && music.playing()) {
                        music.pause();
                        sound.play(1, 0.6f);
                        music.resume();
                    } else {
                        sound.play(1, 0.6f);
                    }
                } catch (SlickException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        soundThread.start();
    }
}
