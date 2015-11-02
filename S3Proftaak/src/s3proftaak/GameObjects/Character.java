package s3proftaak.GameObjects;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.Game;
import s3proftaak.GameObjects.Interfaces.IRenderable;
import s3proftaak.GameObjects.Interfaces.IUpdateable;
import s3proftaak.Main;

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

    private float gravity = 0.5f;
    private float jumpStrength = -12;
    private float speed = 4;
    private int interations = 5;
    private float vX = 0;
    private float vY = 0;
    private final int controlSet;

    private Game game;
    private SpriteSheet playerSheet;
    private Animation animate;
    private GameObject MLO;
    private float offSetX;
    
    private boolean isCrouching;
    
    float marginy,marginx;

    public Character(Game game, float x, float y, float width, float height, int controlSet) throws SlickException {
        super(x, y, width, height);
        this.game = game;
        this.controlSet = controlSet;

        this.hitbox = new Rectangle(this.x, this.y, this.width, this.height);
        MLO = new Block( 1f, 1f, 1f, 1f);
        this.game.getGameObjects().add(MLO);
        for(GameObject go: this.game.getGameObjects()){
            if(go.getX() < MLO.getX()){
                MLO.setX(go.getX());
            }
        }
        marginx = 0 - MLO.getX();
        
        this.isCrouching = false;
        
        try {
            playerSheet = new SpriteSheet(getClass().getResource("/Resources/Levels/player" + (controlSet+1 < 3 ? controlSet+1 : 3) + "_sprites.png").getPath().replace("%20", " "), 70, 93);
            animate = new Animation(playerSheet, 100);
        } catch (SlickException ex) {
            Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(GameContainer gc, int i) {
        //update player (move)
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
        if (gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
            try {
                Main.getApp().reinit();
            } catch (SlickException ex) {
                Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void render(GameContainer gc, Graphics g) {
        //render animation
        animate.draw(this.getX(), this.getY());
    }
    
    public void moveHorizontalMap(GameContainer gc) {
        
        this.offSetX = 0 - MLO.getX();
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
                    go.setX(go.getX() + vXtemp);
                    go.updateHitbox();
                }
            }
            if (this.isColliding(gc)) {
                for (GameObject go : game.getGameObjects()) {
                    if (go != this) {
                        go.setX(go.getX() - vXtemp);
                        go.updateHitbox();
                    }
                }
            }
        }
    }
    
    public float getOffsetX(){
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
            this.setX(this.getX() + vXtemp);
            if (this.isColliding(gc)) {
                //ipv setx -> render map 
                this.setX(this.getX() - vXtemp);
                this.updateHitbox();
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
            this.setY(this.getY() + 0.1f);
            this.updateHitbox();
            if (this.isColliding(gc)) {
                this.vY = this.jumpStrength;
            }
            this.setY(this.getY() - 0.1f);
            this.updateHitbox();
        }
        
        if (input.isKeyDown(Input.KEY_DOWN)){
            this.checkCrouch(true);
        }else{
           this.checkCrouch(false); 
        }

        if(this.getY() > (70*15)){
            this.die();
        }
        
        //check for collision in 5 small steps for higher precision
        float vYtemp = this.vY / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.setY(this.getY() + vYtemp);
            this.updateHitbox();
            if (this.isColliding(gc)) {
                this.setY(this.getY() - vYtemp);
                this.updateHitbox();
                this.vY = 0;
            }
        }
    }

    public void die() {
        try {
            Main.getApp().reinit();
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
                    } else if (go instanceof MoveableBlock){
                        if (go.getRect().getMinY() + 1 < rect.getMaxY()){
                            int i = 0;
                            if (go.getRect().getX() > rect.getX()){
                                i = 1;
                            }
                            if (go.getRect().getX() < rect.getX()){
                                i = -1;
                            }
                            ((MoveableBlock) go).setDx(i);
                        }                        
                        if(getRect().getMinX() < go.getRect().getMaxX() && getRect().getMaxX() > go.getRect().getMinX()){
                            
                            for(int b = 1; b < 20; b++){
                                if(this.getRect().getMinY() >= go.getRect().getMaxY() - b){
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
                        if (this.getRect().getMinY() - 1 < go.getY()) {
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
                        if (this.getX() < go.getX() && this.getX() + this.width > go.getX() + go.getWidth()) {
                            System.out.println(((Door) go).isActive());
                            if (((Door) go).isActive()) {
                                System.out.println("finish");
                                ((Door) go).finish();
                            }
                        }
                    } else if (go instanceof Star){
                        if(((Star)go).isActive()){
                            ((Star)go).setActive(false);
                        }
                    }
                    else if (go instanceof Weight){
                        if(getRect().getMinX() < go.getRect().getMaxX() && getRect().getMaxX() > go.getRect().getMinX()){
                            if(this.getRect().getMinY() >= go.getRect().getMaxY() - 5){
                                this.die();
                                return false;
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

        //check for collision in 5 small steps for higher precision
        float vXtemp = this.vX / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.setX(this.getX() + vXtemp);
            this.updateHitbox();
            if (this.isColliding(gc)) {
                this.setX(this.getX() - vXtemp);
                this.updateHitbox();
                this.vX = 0;
            }
        }
    }

    public void moveVertical1(GameContainer gc) {
        //move with arrow keys
        Input input = gc.getInput();
        this.vY += this.gravity;
        if (input.isKeyDown(Input.KEY_W)) {
            //move up -> y min
            this.setY(this.getY() + 0.1f);
            this.updateHitbox();
            if (this.isColliding(gc)) {
                this.vY = this.jumpStrength;
            }
            this.setY(this.getY() - 0.1f);
            this.updateHitbox();
        }
        
        if (input.isKeyDown(Input.KEY_S)){
            this.checkCrouch(true);
        }else{
           this.checkCrouch(false); 
        }
        
        if(this.getY() > (70*15)){
            this.die();
        }

        //check for collision in 5 small steps for higher precision
        float vYtemp = this.vY / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.setY(this.getY() + vYtemp);
            this.updateHitbox();
            if (this.isColliding(gc)) {
                this.setY(this.getY() - vYtemp);
                this.updateHitbox();
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
            this.setX(this.getX() + vXtemp);
            this.updateHitbox();
            if (this.isColliding(gc)) {
                this.setX(this.getX() - vXtemp);
                this.updateHitbox();
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
            this.setY(this.getY() + 0.1f);
            this.updateHitbox();
            if (this.isColliding(gc)) {
                this.vY = this.jumpStrength;
            }
            this.setY(this.getY() - 0.1f);
            this.updateHitbox();
        }
        
        if (input.isKeyDown(Input.KEY_K)){
            this.checkCrouch(true);
        }else{
           this.checkCrouch(false); 
        }
        
        if(this.getY() > (70*15)){
            this.die();
        }

        //check for collision in 5 small steps for higher precision
        float vYtemp = this.vY / this.interations;
        for (int i = 0; i < this.interations; i++) {
            this.setY(this.getY() + vYtemp);
            this.updateHitbox();
            if (this.isColliding(gc)) {
                this.setY(this.getY() - vYtemp);
                this.updateHitbox();
                this.vY = 0;
            }
        }
    }
    
    private void checkCrouch(boolean crouching){
        if (isCrouching != crouching){
            if (!crouching){
                if (isObjectAbove()){
                    return;
                }
            }
            
            isCrouching = crouching;

            try {
                playerSheet = new SpriteSheet(getClass().getResource("/Resources/Levels/player" + (controlSet+1 < 3 ? controlSet+1 : 3) + "_sprites" + (crouching ? "_crouch" : "") + ".png").getPath().replace("%20", " "), 70, !crouching ? 93 : 69);
                animate = new Animation(playerSheet, 100);

                if (crouching){
                    this.getRect().setHeight(69);
                    this.setY(this.getY() + 24);
                    this.updateHitbox();
                }else{
                    this.setY(this.getY() - 24);
                    this.getRect().setHeight(93);
                    this.updateHitbox();
                }

            } catch (SlickException ex) {
                Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private boolean isObjectAbove(){
        for (GameObject go : game.getGameObjects()){
            if (getRect().getMinX() <= go.getRect().getMaxX() && getRect().getMaxX() >= go.getRect().getMinX()){
                if (getRect().getMinY() - 23 <= go.getRect().getMaxY() && getRect().getMaxY() > go.getRect().getMaxY()){
                    return true;
                }
            }
        }
        
        return false;
    }
}