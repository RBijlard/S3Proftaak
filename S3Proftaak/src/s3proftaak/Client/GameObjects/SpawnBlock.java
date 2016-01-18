package s3proftaak.Client.GameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.ResourceManager;

/**
 *
 * @author Berry-PC
 */
public class SpawnBlock extends GameObject implements IStateChangeable, IRenderable {

    private boolean isActive = false;
    private Image sprite;

    public SpawnBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.changeImage(isActive);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        //render door animation/img
        sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset());
    }

    public int calculateOffset() {
        return (int) (70 - this.getRect().getHeight());
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
        System.out.println("SPAWNBLOCK STATUS: " + active);
                
        if (active) {
            sprite = ResourceManager.Images.DOOR_OPENMID.getImage();
        } else {
            sprite = ResourceManager.Images.DOOR_CLOSEDMID.getImage();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " -- SPAWNBLOCK " + this.getMatches();
    }

}
