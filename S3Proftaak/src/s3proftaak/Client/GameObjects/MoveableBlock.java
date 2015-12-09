/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.GameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IUpdateable;
import s3proftaak.Client.ClientAdministration;

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
            this.sprite = new Image("Resources/Levels/boxItem.png");
        } catch (SlickException ex) {
        }
    }

    @Override
    public void update(GameContainer gc, int i) {
        for (int a = 0; a < 5; a++) {
            if (dx != 0) {
                if (!this.isColliding(gc)) {
                    this.getRect().setX(this.getRect().getX() + dx);
                }
                dx = 0;
            }
        }

        boolean verticalCollision = false;
        for (GameObject go : ClientAdministration.getInstance().getGame().getGameObjects()) {
            if (go != this) {
                if ((go.getRect().intersects(this.getRect()) || go.getRect().contains(this.getRect())) && !((go instanceof Spike) || (go instanceof Button))) {
                    verticalCollision = true;
                }
                else if ((go.getRect().intersects(this.getRect()) || go.getRect().contains(this.getRect())) && (go instanceof Button)){
                    if (!((Button) go).isActive()) {
                        ((Button) go).setActive(true);
                    }                    
                    verticalCollision = true;
                }
            }
        }
        if (verticalCollision) {
            //dont fall down
        } else {
            //verticalCollision = false, fall down
            for (int b = 0; b < 5; b++) {
                this.getRect().setY(this.getRect().getY() + 2);
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset());
    }

    public boolean isColliding(GameContainer gc) {
        for (GameObject go : ClientAdministration.getInstance().getGame().getGameObjects()) {
            if (go != this) {
                if (go.getRect().intersects(this.getRect()) || go.getRect().contains(this.getRect())) {
                    if (go instanceof Block) {
                        if (this.getRect().getMaxY() != go.getRect().getMinY()) {
                            return true;
                        }
                    } else
                    if (go instanceof GameObject) {
                        System.out.println(go.toString());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int calculateOffset() {
        return (int) (70 - this.getRect().getHeight());
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    @Override
    public String toString() {
        return super.toString() + " -- MOVEABLEBLOCK";
    }
    
    
    
}
