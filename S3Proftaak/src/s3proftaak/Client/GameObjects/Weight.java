package s3proftaak.Client.GameObjects;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.Client.GameObjects.Interfaces.IUpdateable;
import s3proftaak.Client.ResourceManager;

/**
 *
 * @author Berry-PC
 */
public class Weight extends GameObject implements IStateChangeable, IRenderable, IUpdateable {

    private boolean isActive = false;
    private Image sprite;
    private Image sprite1;

    private int minus = 0;
    private float height;

    public Weight(float x, float y, float width, float height) {
        super(x, y, width, height);

        this.height = height;
        sprite = ResourceManager.Images.WEIGHTCHAINED.getImage();
        sprite1 = ResourceManager.Images.CHAIN.getImage();
    }

    @Override
    public void update(GameContainer gc) {
        this.getRect().setHeight(height - minus);

        if (isActive) {
            if (minus < height - 70) {
                minus += 5;
            }
        } else {
            if (minus > 0) {
                minus -= 5;
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        //render door animation/img
        for (int i = -1; height - i * 70 + 70 - minus > 0; i++) {
            sprite1.draw(this.getRect().getX(), height - i * 70 - 70 - minus);
        }

        sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset() - minus);
    }

    public int calculateOffset() {
        return (int) (70 - height);
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public void setActive(boolean active) {
        this.isActive = active;
    }

    @Override
    public String toString() {
        return super.toString() + " -- WEIGHT " + this.getMatches();
    }

}
