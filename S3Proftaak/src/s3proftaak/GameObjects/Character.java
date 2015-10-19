package s3proftaak.GameObjects;


import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.Game;
import s3proftaak.Visuals.Menu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Berry-PC
 */
public class Character extends GameObject{

    private float gravity = 0.5f;
    private float jumpStrength = -12;
    private float speed = 4;
    private int interations = 5;
    private float vX = 0;
    private float vY = 0;
    private int controlSet;
    
    private Game game;
    private SpriteSheet playerSheet;
    private Animation animate;
    
    public Character(Game game, float x, float y, float width, float height, int controlSet, int match) throws SlickException {
        super(x, y, width, height, match);
        this.game = game;
        playerSheet = new SpriteSheet(getClass().getResource("/Resources/player3_sprites.png").getPath().replace("%20", " "), 70, 93);
        animate = new Animation(playerSheet, 100);
        this.controlSet = controlSet;
        switch(this.controlSet){
            case 0:
                playerSheet = new SpriteSheet(getClass().getResource("/Resources/player1_sprites.png").getPath().replace("%20", " "), 70, 93);
                animate = new Animation(playerSheet, 100);
            break;
            case 1:
                playerSheet = new SpriteSheet(getClass().getResource("/Resources/player2_sprites.png").getPath().replace("%20", " "), 70, 93);
                animate = new Animation(playerSheet, 100);
            break;
            case 2:
                playerSheet = new SpriteSheet(getClass().getResource("/Resources/player3_sprites.png").getPath().replace("%20", " "), 70, 93);
                animate = new Animation(playerSheet, 100);
            break;              
        }
        this.hitbox = new Rectangle(this.x,this.y,this.width,this.height);
    }
    
    public void update(GameContainer gc, int i) {
        //update player (move)
        switch(this.controlSet){
            case 0:
                this.moveHorizontal(gc);
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
        if(gc.getInput().isKeyDown(Input.KEY_ESCAPE)){
            Menu.getAppContainer().exit();
        }
    }
    
    public void render(GameContainer gc, Graphics g){
        //render animation
        animate.draw(this.getX(), this.getY());
    }
    
    public void moveHorizontal(GameContainer gc) {
        //move with arrow keys
        Input input = gc.getInput();
        if(input.isKeyDown(Input.KEY_LEFT)){
            //move leftt -> x min
            this.vX = -this.speed;
        }
        else if (input.isKeyDown(Input.KEY_RIGHT)){
            //move right -> x plus
            this.vX = this.speed;
        }
        else{
            this.vX=0;
        }
        
        //check for collision in 5 small steps for higher precision
        float vXtemp = this.vX / this.interations;
        for(int i = 0; i < this.interations; i++){
            this.setX(this.getX() + vXtemp);
                this.updateHitbox();
            if(this.isColliding()){
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
        if(input.isKeyDown(Input.KEY_UP)){
            //move up -> y min
                this.setY(this.getY() + 0.1f);
                this.updateHitbox();
            if(this.isColliding()){
                this.vY = this.jumpStrength;
            }
                this.setY(this.getY() - 0.1f);
                this.updateHitbox();
        }
        
        //check for collision in 5 small steps for higher precision
        float vYtemp = this.vY / this.interations;
        for (int i = 0; i < this.interations; i++){
                this.setY(this.getY() + vYtemp);
                this.updateHitbox();
            if(this.isColliding()){
                this.setY(this.getY() - vYtemp);
                this.updateHitbox();
                this.vY=0;
            }
        }
    }
    
    public boolean isColliding() {
        Rectangle rect = this.getRect();
        for(GameObject go : game.getGameObjects()){
            //check if colliding
            //instanceof on GO not working
            if(go.getRect().intersects(rect) || go.getRect().contains(rect)){
                if(go == this){
                }
                else{
                    //check what object
                    if(go instanceof Block){
                        return true;
                    }
                    else if(go instanceof Spike){
                        return false;
                    }
                    else if(go instanceof Character){
                        return true;
                    }
                    else if(go instanceof Button){
                        if(this.getY() + this.height - 1 < go.getY()){
                            ((Button) go).setActive(true); 
                        }
                        return true;
                    }
                    else if(go instanceof Door){
                        if(((Door) go).isActive()){
                            //TODO
                            //((Door) go).finish(); 
                            return false;
                        }                      
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        return super.toString() + " -- CHARACTER";
    }
        
    public void moveHorizontal1(GameContainer gc) {
        //move with arrow keys
        Input input = gc.getInput();
        if(input.isKeyDown(Input.KEY_A)){
            //move leftt -> x min
            this.vX = -this.speed;
        }
        else if (input.isKeyDown(Input.KEY_D)){
            //move right -> x plus
            this.vX = this.speed;
        }
        else{
            this.vX=0;
        }
        
        //check for collision in 5 small steps for higher precision
        float vXtemp = this.vX / this.interations;
        for(int i = 0; i < this.interations; i++){
            this.setX(this.getX() + vXtemp);
                this.updateHitbox();
            if(this.isColliding()){
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
        if(input.isKeyDown(Input.KEY_W)){
            //move up -> y min
                this.setY(this.getY() + 0.1f);
                this.updateHitbox();
            if(this.isColliding()){
                this.vY = this.jumpStrength;
            }
                this.setY(this.getY() - 0.1f);
                this.updateHitbox();
        }
        
        //check for collision in 5 small steps for higher precision
        float vYtemp = this.vY / this.interations;
        for (int i = 0; i < this.interations; i++){
                this.setY(this.getY() + vYtemp);
                this.updateHitbox();
            if(this.isColliding()){
                this.setY(this.getY() - vYtemp);
                this.updateHitbox();
                this.vY=0;
            }
        }
    }
        
    public void moveHorizontal2(GameContainer gc) {
        //move with arrow keys
        Input input = gc.getInput();
        if(input.isKeyDown(Input.KEY_J)){
            //move leftt -> x min
            this.vX = -this.speed;
        }
        else if (input.isKeyDown(Input.KEY_L)){
            //move right -> x plus
            this.vX = this.speed;
        }
        else{
            this.vX=0;
        }
        
        //check for collision in 5 small steps for higher precision
        float vXtemp = this.vX / this.interations;
        for(int i = 0; i < this.interations; i++){
            this.setX(this.getX() + vXtemp);
                this.updateHitbox();
            if(this.isColliding()){
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
        if(input.isKeyDown(Input.KEY_I)){
            //move up -> y min
                this.setY(this.getY() + 0.1f);
                this.updateHitbox();
            if(this.isColliding()){
                this.vY = this.jumpStrength;
            }
                this.setY(this.getY() - 0.1f);
                this.updateHitbox();
        }
        
        //check for collision in 5 small steps for higher precision
        float vYtemp = this.vY / this.interations;
        for (int i = 0; i < this.interations; i++){
                this.setY(this.getY() + vYtemp);
                this.updateHitbox();
            if(this.isColliding()){
                this.setY(this.getY() - vYtemp);
                this.updateHitbox();
                this.vY=0;
            }
        }
    }
}
