/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.GameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import s3proftaak.GameObjects.Interfaces.IRenderable;
import s3proftaak.GameObjects.Interfaces.IUpdateable;
import s3proftaak.Main;

/**
 *
 * @author Stan
 */
public class MoveableBlock extends GameObject implements IUpdateable, IRenderable {
    
    private int dx;
    private Image sprite;
    
    public MoveableBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
        try {
            this.sprite = new Image("Resources/Levels/buttonRed_pressed.png");
        } catch (SlickException ex) {}
    }
    
    @Override
    public void update(GameContainer gc, int i) {
        if (dx != 0){
            if (!this.isColliding(gc)){
                this.x += dx;
            }
            dx = 0;
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        sprite.draw(this.x,this.y - calculateOffset());
    }
    
    public boolean isColliding(GameContainer gc) {
        for (GameObject go : Main.getGame().getGameObjects()) {
            if (go != this){
                if (go.getRect().intersects(this.getRect()) || go.getRect().contains(this.getRect())) {
                    if (go instanceof Block){
                        if (this.getRect().getMaxY() != go.getRect().getMinY()){
                            return true;
                        }
                    }else
                    if (go instanceof GameObject){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public int calculateOffset(){
        return (int) (70-this.height);
    }
    
    public void setDx(int dx){
        this.dx = dx;
    }
}
