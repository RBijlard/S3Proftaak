package s3proftaak.Client;

import java.awt.Font;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import s3proftaak.Client.GameObjects.Button;
import s3proftaak.Client.GameObjects.Character;
import s3proftaak.Client.GameObjects.Door;
import s3proftaak.Client.GameObjects.Block;
import s3proftaak.Client.GameObjects.GameObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import s3proftaak.Client.GameObjects.Interfaces.IPressable;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.Client.GameObjects.Interfaces.IUpdateable;
import s3proftaak.Client.GameObjects.Lever;
import s3proftaak.Client.GameObjects.MoveableBlock;
import s3proftaak.Client.GameObjects.Spike;
import s3proftaak.Client.GameObjects.Star;
import s3proftaak.Client.GameObjects.Weight;
import s3proftaak.Client.SoundManager.Sounds;

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

    private final boolean multiplayer;
    private boolean waitingforotherplayers;
    private boolean restart;

    private List<GameObject> gameObjects;
    private List<Character> gameCharacters;
    private List<String> gameCharacterNames;
    private List<GameObject> removableGameObjects;

    private TiledMap map;
    private float x = 70f, y = 70f;
    private String path;
    private int amountOfPlayers = 0;
    private Character main_character;
    private String mapname;

    private Score score;
    private long startTime, currentTime, endTime;
    private int starsCollected = 0;

    private float baseWidht = 1920;
    private float baseHight = 1080;

    private boolean gameOver;

    private TrueTypeFont slickFontTimer;
    private TrueTypeFont slickFontUserName;

    private int objectId = 0;

    public Game(String title, int amountOfPlayers, String mapname, List<String> names) {
        super(title);

        this.multiplayer = names != null;

        if (this.multiplayer) {
            this.gameCharacterNames = names;
        }

        this.amountOfPlayers = amountOfPlayers;
        this.mapname = mapname;
        this.gameOver = false;
    }

    public TiledMap getMap() {
        return this.map;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        //set stars
        this.starsCollected = 0;

        //Set Fonts
        this.objectId = 0;

        this.slickFontTimer = new TrueTypeFont(new Font("Montserrat", Font.BOLD, 40), false);
        this.slickFontUserName = new TrueTypeFont(new Font("Montserrat", Font.BOLD, 18), false);

        //play deathsound
        SoundManager.getInstance().playDeathSound();

        //initialise map, players and objects
        this.path = getClass().getResource("/Resources/Levels/" + this.mapname).getPath().replace("%20", " ");

        //map and list
        this.map = new TiledMap(path);
        this.gameObjects = new ArrayList<>();
        this.gameCharacters = new ArrayList<>();
        this.removableGameObjects = new ArrayList<>();

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
            g1.setId(nextObjectId());
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

        for (int i = 0; i < this.amountOfPlayers; i++) {

            Character c = new Character(this, (72f * i + 500f), 72f, 70f, 93f, i, multiplayer ? gameCharacterNames.get(i) : "");

            if ((!multiplayer && i == 0) || (multiplayer && ClientAdministration.getInstance().getAccount().getUsername().equals(gameCharacterNames.get(i)))) {
                this.gameCharacters.add(main_character = c);
            }

            if (!this.gameCharacters.contains(c)) {
                this.gameCharacters.add(c);
            }
        }

        this.gameObjects.addAll(this.gameCharacters);

        // Moet keer weg
        for (GameObject go : this.gameObjects) {
            System.out.println(go.toString());
        }

        // set current Time to 0
        this.currentTime = 0;

        SoundManager.getInstance().playMusic();

        startTime = System.currentTimeMillis();

        if (isMultiplayer()) {
            this.waitingforotherplayers = true;

            try {
                ClientAdministration.getInstance().getCurrentLobby().loadedGame(ClientAdministration.getInstance().getAccount().getUsername());
            } catch (RemoteException ex) {
                ClientAdministration.getInstance().stopGame("");
            }
        }
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        if (this.restart) {
            this.restart = false;

            try {
                SoundManager.getInstance().restartSound();
                ClientAdministration.getInstance().getApp().reinit();
            } catch (SlickException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (!this.waitingforotherplayers) {
            //update game and player
            //update currentTime
            this.currentTime += i;
            
            // Handle GameObjects that should be removed.
            if (!removableGameObjects.isEmpty()){
                List<GameObject> tempObjects = new ArrayList<>();
                tempObjects.addAll(removableGameObjects);
                
                for (GameObject go : tempObjects){
                    
                    if (go instanceof Star){
                        this.starsCollected++;
                    }
                    
                    gameObjects.remove(go);
                    removableGameObjects.remove(go);
                }
            }

            for (GameObject go : this.gameObjects) {

                if (go instanceof IUpdateable) {
                    ((IUpdateable) go).update(gc);
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
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        //scaling the game to your resolution
        grphcs.scale(Display.getWidth() / this.baseWidht, Display.getHeight() / this.baseHight);
        grphcs.setBackground(new Color(0, 191, 255));
        for (GameObject go : this.gameObjects) {
            // Teken hitboxes, moet keer weg
            // grphcs.draw(go.getRect());

            if (go instanceof IRenderable) {
                ((IRenderable) go).render(gc, grphcs);
            }
        }
        //render game and player
        for (int i = 0; i < map.getLayerCount(); i++) {
            this.map.render(0 - (int) main_character.getOffsetX(), i);
        }

        //Amount of stars in String format
        String Stars = "x " + starsCollected;

        //render Timer
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String strDate = sdf.format(this.currentTime);
        grphcs.setColor(Color.white);
        grphcs.setFont(slickFontTimer);
        grphcs.drawString(("Time: " + strDate), 50, 50);
        grphcs.drawImage(ResourceManager.getImage(ResourceManager.Images.STAR), 40, 90);
        grphcs.drawString(Stars, 100, 100);

        if (this.waitingforotherplayers) {
            String text = "Waiting for other players.";
            grphcs.drawString(text, (Display.getWidth() / 2) - (text.getBytes().length), Display.getHeight() / 2);
        }
    }

    public List<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    public List<Character> getGameCharacters() {
        return this.gameCharacters;
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
                } catch (Exception ex) {
                    System.out.println(ex.toString());
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

            //add stop
            SoundManager.getInstance().playSound(Sounds.GAMEOVER);

            endTime = System.currentTimeMillis();

            long timeDiff = endTime - startTime;

            try {
                String players = "";

                if (isMultiplayer()) {
                    for (String s : gameCharacterNames) {
                        players += s + ", ";
                    }

                    if (players.endsWith(", ")) {
                        players = players.substring(0, players.length() - 2);
                    }
                }else{
                    players = ClientAdministration.getInstance().getAccount().getUsername();
                }

                this.score = new Score((int) timeDiff, starsCollected, players, this.mapname);
                if (DBConnect.getInstance() != null) {
                    DBConnect.getInstance().insertScore(this.score);
                }

            } catch (SQLException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }

            ClientAdministration.getInstance().stopGame(null);
            SoundManager.getInstance().playMenuMusic();
        }
    }

    public boolean isMultiplayer() {
        return this.multiplayer;
    }

    public TrueTypeFont getSlickFontUsername() {
        return this.slickFontUserName;
    }

    @Override
    public boolean closeRequested() {
        try {
            ClientAdministration.getInstance().setGame(null);
        } catch (SlickException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (ClientAdministration.getInstance().getCurrentLobby() != null) {
            try {
                ClientAdministration.getInstance().getCurrentLobby().closedGame();
            } catch (RemoteException ex) {
                ClientAdministration.getInstance().stopGame("");
            }
        }

        return super.closeRequested();
    }

    public int nextObjectId() {
        return this.objectId++;
    }

    public GameObject getGameObject(int id) {
        return id < this.gameObjects.size() ? this.gameObjects.get(id) : null;
    }

    public void waitingForOtherPlayers() {
        this.waitingforotherplayers = false;
    }

    public void doRestart() {
        this.restart = true;
    }
    
    public void removeGameObject(GameObject go){
        removableGameObjects.add(go);
    }
}
