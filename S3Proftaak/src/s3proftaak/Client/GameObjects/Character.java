package s3proftaak.Client.GameObjects;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.Client.Game;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IUpdateable;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.SoundManager;
import s3proftaak.Client.SoundManager.Sounds;
import s3proftaak.Client.Visuals.Listeners.GameListener;
import s3proftaak.Shared.PlayerPosition;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Berry-PC
 */
public class Character extends GameObject implements IRenderable, IUpdateable {

    private final String name;
    private final boolean isControllabe;

    private float gravity = 0.5f;
    private float jumpStrength = -12;
    private float speed = 4;
    private int interations = 5;
    private float vX = 0;
    private float vY = 0;
    private final int controlSet;
    
    private float oldX, oldY;

    private Game game;
    private SpriteSheet playerSheet;
    private Animation animate;
    private GameObject MLO;
    private float offSetX;

    private boolean isCrouching;

    float marginy, marginx;

    public Character(Game game, float x, float y, float width, float height, int controlSet, String name) throws SlickException {
        super(x, y, width, height);
        this.name = name;

        this.isControllabe = this.name.equals(ClientAdministration.getInstance().getAccount().getUsername());

        this.game = game;
        this.controlSet = controlSet;

        MLO = new Block(1f, 1f, 1f, 1f);
        this.game.getGameObjects().add(MLO);
        for (GameObject go : this.game.getGameObjects()) {
            if (go.getRect().getX() < MLO.getRect().getX()) {
                MLO.getRect().setX(go.getRect().getX());
            }
        }
        marginx = 0 - MLO.getRect().getX();

        this.isCrouching = false;

        try {
            playerSheet = new SpriteSheet(getClass().getResource("/Resources/Levels/player" + (controlSet + 1 < 3 ? controlSet + 1 : 3) + "_sprites.png").getPath().replace("%20", " "), 70, 93);
            animate = new Animation(playerSheet, 100);
        } catch (SlickException ex) {
            Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(GameContainer gc, int i) {
        //update player (move)

        if (game.isMultiplayer()) {
            if (isControllabe) {
                this.moveHorizontalMap(gc);
                this.moveVertical(gc);
            } else {
                this.moveHorizontal1(gc);
                this.moveVertical1(gc);
            }

        } else {
            switch (this.controlSet) {
                case 0:
                    this.moveHorizontalMap(gc);
                    this.moveVertical(gc);
                    break;
                case 1:
                    this.moveHorizontal1(gc);
                    this.moveVertical1(gc);
                    break;
                case 2:
                    this.moveHorizontal2(gc);
                    this.moveVertical2(gc);
                    break;
            }
        }

        if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
            try {
                SoundManager.getInstance().restartSound();
                ClientAdministration.getInstance().getApp().reinit();
            } catch (SlickException ex) {
                Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (game.isMultiplayer() && isControllabe){
            PlayerPosition pp = new PlayerPosition(this.getOffsetX() + getRect().getX(), getRect().getY(), vX, vY, isCrouching);
            if (oldY != pp.getY() || oldX != pp.getX()){
                oldY = pp.getY();
                oldX = pp.getX();
                
                try {
                    ClientAdministration.getInstance().getCurrentLobby().updatePlayer(ClientAdministration.getInstance().getAccount().getUsername(), pp);
                } catch (RemoteException ex) {
                    Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        //render animation
        animate.draw(this.getRect().getX(), this.getRect().getY());

        if (game.isMultiplayer()) {
            //Draw Username Above Character
            g.setColor(Color.yellow);
            g.setFont(game.getSlickFontUsername());
            g.drawString(name, this.getRect().getX() + 23, this.getRect().getY() - 23);
        }

    }

    public void moveHorizontalMap(GameContainer gc) {

        this.offSetX = 0 - MLO.getRect().getX();
        //Move horizontal with arrow keys
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_LEFT)) {
            //move map right -> x minus speed
            this.vX = this.speed;
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            //move map left -> x plus speed
            this.vX = -this.speed;
        } else {
            //dont move the map
            this.vX = 0;
        }

        //check collisions
        float vXtemp = this.vX / this.interations;
        for (int i = 0; i < this.interations; i++) {
            for (GameObject go : game.getGameObjects()) {
                if (go != this) {
                    go.getRect().setX(go.getRect().getX() + vXtemp);
                }
            }
            if (this.isColliding(gc)) {
                for (GameObject go : game.getGameObjects()) {
                    if (go != this) {
                        go.getRect().setX(go.getRect().getX() - vXtemp);
                    }
                }
            }
        }
    }

    public float getOffsetX() {
        return this.offSetX - this.marginx;
    }

    public void moveHorizontal(GameContainer gc) {
        //move with arrow keys
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_LEFT)) {
            //move leftt -> x min
            this.vX = -this.speed;
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            //move right -> x plus
            this.vX = this.speed;
        } else {
            this.vX = 0;
        }

        //check for collision in 5 small steps for higher precision
        float vXtemp = this.vX / this.interations;
        for (int i = 0; i < this.interations; i++) {
            //ipv setx -> render map
            this.getRect().setX(this.getRect().getX() + vXtemp);
            if (this.isColliding(gc)) {
                //ipv setx -> render map 
                this.getRect().setX(this.getRect().getX() - vXtemp);
                this.vX = 0;
            }
        }
    }

    public void moveVertical(GameContainer gc) {
        //move with arrow keys
        Input input = gc.getInput();
        this.vY += this.gravity;
        if (input.isKeyDown(Input.KEY_UP)) {
            //move up -> y min
            //ipv sety -> render map
            this.getRect().setY(this.getRect().getY() + 0.1f);
            if (this.isColliding(gc) && !isObjectAbove()) {
                this.vY = this.jumpStrength;
                SoundManager.getInstance().playSound(Sounds.JUMP);
            }
            this.getRect().setY(this.getRect().getY() - 0.1f);
        }

        if (game.isMultiplayer()){
            this.checkCrouch(isCrouching);
        }else{
            this.checkCrouch(input.isKeyDown(Input.KEY_DOWN));
        }

        if (this.getRect().getY() > (70 * 15)) {
            this.die();
        }

        //check for collision in 5 small steps for higher precision
        float vYtemp = this.vY / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.getRect().setY(this.getRect().getY() + vYtemp);
            if (this.isColliding(gc)) {
                this.getRect().setY(this.getRect().getY() - vYtemp);
                this.vY = 0;
            }
        }
    }

    public void die() {
        try {            
            SoundManager.getInstance().playSound(Sounds.GAMEOVER);
            SoundManager.getInstance().restartSound();
            ClientAdministration.getInstance().getApp().reinit();

        } catch (SlickException ex) {
            Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isColliding(GameContainer gc) {
        Rectangle rect = this.getRect();
        for (GameObject go : game.getGameObjects()) {
            //check if colliding
            if (go.getRect().intersects(rect) || go.getRect().contains(rect)) {
                if (go != this) {
                    //check what object
                    if (go instanceof Block || go instanceof Character) {
                        return true;
                    } else if (go instanceof MoveableBlock) {
                        if (go.getRect().getMinY() + 1 < rect.getMaxY()) {
                            int i = 0;
                            if (go.getRect().getX() > rect.getX()) {
                                i = 1;
                            }
                            if (go.getRect().getX() < rect.getX()) {
                                i = -1;
                            }
                            ((MoveableBlock) go).setDx(i);
                        }
                        if (getRect().getMinX() < go.getRect().getMaxX() && getRect().getMaxX() > go.getRect().getMinX()) {

                            for (int b = 1; b < 20; b++) {
                                if (this.getRect().getMinY() >= go.getRect().getMaxY() - b) {
                                    this.die();
                                    return true;
                                }
                            }
                        }

                        return true;
                    } else if (go instanceof Spike) {
                        this.die();
                        return false;
                    } else if (go instanceof Button) {
                        if (this.getRect().getMinY() - 1 < go.getRect().getY()) {
                            if (!((Button) go).isActive()) {
                                ((Button) go).setActive(true);
                            }
                        }
                        return true;
                    } else if (go instanceof Lever && gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
                        if (!((Lever) go).isActive()) {
                            ((Lever) go).setActive(true);
                        } else {
                            ((Lever) go).setActive(false);
                        }
                        return true;
                    } else if (go instanceof Door) {
                        if (this.getRect().getX() < go.getRect().getX() && this.getRect().getX() + this.getRect().getWidth() > go.getRect().getX() + go.getRect().getWidth()) {
                            System.out.println(((Door) go).isActive());
                            if (((Door) go).isActive()) {
                                System.out.println("finish");
                                ((Door) go).finish();
                            }
                        }
                    } else if (go instanceof Star) {
                        if (!((Star) go).isRemoved()) {
                            ((Star) go).remove();
                            SoundManager.getInstance().playSound(Sounds.COINPICKUP);
                        }
                    } else if (go instanceof Weight) {
                        if (getRect().getMinX() < go.getRect().getMaxX() && getRect().getMaxX() > go.getRect().getMinX()) {
                            if (this.getRect().getMinY() >= go.getRect().getMaxY() - 5) {
                                if (!((Weight) go).isActive()) {
                                    this.die();
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + " -- CHARACTER";
    }

    public void moveHorizontal1(GameContainer gc) {
        //move with arrow keys

        if (!game.isMultiplayer()) {
            Input input = gc.getInput();
            if (input.isKeyDown(Input.KEY_A)) {
                //move leftt -> x min
                this.vX = -this.speed;
            } else if (input.isKeyDown(Input.KEY_D)) {
                //move right -> x plus
                this.vX = this.speed;
            } else {
                this.vX = 0;
            }
        }

        //check for collision in 5 small steps for higher precision
        float vXtemp = this.vX / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.getRect().setX(this.getRect().getX() + vXtemp);
            if (this.isColliding(gc)) {
                this.getRect().setX(this.getRect().getX() - vXtemp);
                this.vX = 0;
            }
        }
    }

    public void moveVertical1(GameContainer gc) {
        //move with arrow keys
        
        this.vY += this.gravity;
        
        if (!game.isMultiplayer()){
            Input input = gc.getInput();
            if (input.isKeyDown(Input.KEY_W)) {
                //move up -> y min
                this.getRect().setY(this.getRect().getY() + 0.1f);
                if (this.isColliding(gc)) {
                    this.vY = this.jumpStrength;
                    SoundManager.getInstance().playSound(Sounds.JUMP);
                }
                this.getRect().setY(this.getRect().getY() - 0.1f);
            }

            if (input.isKeyDown(Input.KEY_S)) {
                this.checkCrouch(true);
            } else {
                this.checkCrouch(false);
            }
        }

        if (this.getRect().getY() > (70 * 15)) {
            this.die();
        }

        //check for collision in 5 small steps for higher precision
        float vYtemp = this.vY / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.getRect().setY(this.getRect().getY() + vYtemp);
            if (this.isColliding(gc)) {
                this.getRect().setY(this.getRect().getY() - vYtemp);
                this.vY = 0;
            }
        }
    }

    public void moveHorizontal2(GameContainer gc) {
        //move with arrow keys
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_J)) {
            //move leftt -> x min
            this.vX = -this.speed;
        } else if (input.isKeyDown(Input.KEY_L)) {
            //move right -> x plus
            this.vX = this.speed;
        } else {
            this.vX = 0;
        }

        //check for collision in 5 small steps for higher precision
        float vXtemp = this.vX / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.getRect().setX(this.getRect().getX() + vXtemp);
            if (this.isColliding(gc)) {
                this.getRect().setX(this.getRect().getX() - vXtemp);
                this.vX = 0;
            }
        }
    }

    public void moveVertical2(GameContainer gc) {
        //move with arrow keys
        Input input = gc.getInput();
        this.vY += this.gravity;
        if (input.isKeyDown(Input.KEY_I)) {
            //move up -> y min
            this.getRect().setY(this.getRect().getY() + 0.1f);
            if (this.isColliding(gc)) {
                this.vY = this.jumpStrength;
                SoundManager.getInstance().playSound(Sounds.JUMP);
            }
            this.getRect().setY(this.getRect().getY() - 0.1f);
        }

        if (input.isKeyDown(Input.KEY_K)) {
            this.checkCrouch(true);
        } else {
            this.checkCrouch(false);
        }

        if (this.getRect().getY() > (70 * 15)) {
            this.die();
        }

        //check for collision in 5 small steps for higher precision
        float vYtemp = this.vY / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.getRect().setY(this.getRect().getY() + vYtemp);
            if (this.isColliding(gc)) {
                this.getRect().setY(this.getRect().getY() - vYtemp);
                this.vY = 0;
            }
        }
    }

    private void checkCrouch(boolean crouching) {
        if (isCrouching != crouching) {
            if (!crouching) {
                if (isObjectAbove()) {
                    return;
                }
            }

            isCrouching = crouching;

            try {
                playerSheet = new SpriteSheet(getClass().getResource("/Resources/Levels/player" + (controlSet + 1 < 3 ? controlSet + 1 : 3) + "_sprites" + (crouching ? "_crouch" : "") + ".png").getPath().replace("%20", " "), 70, !crouching ? 93 : 69);
                animate = new Animation(playerSheet, 100);

                if (crouching) {
                    this.getRect().setHeight(69);
                    this.getRect().setY(this.getRect().getY() + 24);
                } else {
                    this.getRect().setY(this.getRect().getY() - 24);
                    this.getRect().setHeight(93);
                }

            } catch (Exception ex) {
                Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean isObjectAbove() {
        for (GameObject go : game.getGameObjects()) {
            if (!(go instanceof Door) && !(go instanceof Star)) {
                if (getRect().getMinX() <= go.getRect().getMaxX() && getRect().getMaxX() >= go.getRect().getMinX()) {
                    if (getRect().getMinY() - 23 <= go.getRect().getMaxY() && getRect().getMaxY() > go.getRect().getMaxY()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    
    public String getName(){
        return this.name;
    }

    public void setIsCrouching(boolean isCrouching) {
        //this.isCrouching = isCrouching;
        this.checkCrouch(isCrouching);
    }

    public void setvX(float vX) {
        this.vX = vX;
    }

    public void setvY(float vY) {
        this.vY = vY;
    }
    
    public void safeMoveTo(float x, float y){
        GameObject tempGo = new Block(x, y, getRect().getWidth(), getRect().getHeight());
        
        for (GameObject go : game.getGameObjects()) {
            //check if colliding
            if (go.getRect().intersects(tempGo.getRect()) || go.getRect().contains(tempGo.getRect())) {
                if (go != this) {
                    //check what object
                    if (go instanceof MoveableBlock || go instanceof Character) {
                        return;
                    }
                }
            }
        }
        
        getRect().setX(x);
        getRect().setY(y);
    }
}
