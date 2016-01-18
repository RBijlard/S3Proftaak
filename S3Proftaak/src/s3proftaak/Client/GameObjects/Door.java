package s3proftaak.Client.GameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.Client.ResourceManager;

/**
 *
 * @author Berry-PC
 */
public class Door extends GameObject implements IStateChangeable, IRenderable {

    private boolean isActive = false;
    private Image sprite;
    private Image sprite1;
    private boolean finished;

    public Door(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.changeImage(isActive);
        finished = false;
    }

    public void finish() {
        if (!finished) {
            finished = true;
            ClientAdministration.getInstance().getGame().doFinish();
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        //render door animation/img
        sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset());
        sprite1.draw(this.getRect().getX(), this.getRect().getY() + calculateOffset());
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public void setActive(boolean active) {
        this.isActive = active;
        changeImage(active);
    }

    private void changeImage(boolean active) {
        System.out.println("DOOR STATUS: " + active);
                
        if (active) {
            sprite = ResourceManager.Images.DOOR_OPENMID.getImage();
            sprite1 = ResourceManager.Images.DOOR_OPENTOP.getImage();
        } else {
            sprite = ResourceManager.Images.DOOR_CLOSEDMID.getImage();
            sprite1 = ResourceManager.Images.DOOR_CLOSEDTOP.getImage();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " -- DOOR " + this.getMatches();
    }

}
