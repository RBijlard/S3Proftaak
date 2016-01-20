package s3proftaak.Client.GameObjects;

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
    private final Image sprite;
    private final Image sprite1;

    private int minus = 0;
    private final float height;

    public Weight(float x, float y, float width, float height) {
        super(x, y, width, height, true);

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
        
        for (int i = 0; this.getRect().getHeight() - i * 70 + getRect().getY() > getRect().getY(); i++) {
            sprite1.draw(this.getRect().getX(), this.getRect().getHeight() - i * 70 + getRect().getY() - 70);
        }

        sprite.draw(this.getRect().getX(), this.getRect().getHeight() + (-1*(70-this.getRect().getY())));
        
        // Old.
        /*for (int i = 0; this.getRect().getY() - calculateOffset() - minus - i * 70 + 70 > 0; i++) {
            sprite1.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset() - minus - i * 70);
        }

        sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset() - minus);*/
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
