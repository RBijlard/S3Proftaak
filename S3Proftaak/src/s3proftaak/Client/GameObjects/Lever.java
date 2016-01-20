package s3proftaak.Client.GameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import s3proftaak.Client.GameObjects.Interfaces.IPressable;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.Client.ResourceManager;
import s3proftaak.Client.SoundManager;
import s3proftaak.Client.SoundManager.Sounds;

/**
 *
 * @author Berry-PC
 */
public class Lever extends GameObject implements IPressable, IRenderable {

    private boolean isActive = false;
    private Image sprite;

    public Lever(float x, float y, float width, float height) {
        super(x, y, width, height, false);
        this.changeImage(isActive);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset());
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public void setActive(boolean active) {
        if (isActive != active) {
            this.isActive = active;
            changeImage(active);

            if (!getMatchedObjects().isEmpty()) {

                for (GameObject po : getMatchedObjects()) {
                    boolean enable = true;

                    for (GameObject mo : po.getMatchedObjects()) {
                        if (mo instanceof IPressable && mo != this) {
                            if (!((IPressable) mo).isActive()) {
                                enable = false;
                                break;
                            }
                        }
                    }

                    if (enable) {
                        ((IStateChangeable) po).setActive(active);
                    }
                }
            }

        }
    }

    private void changeImage(boolean active) {
        if (active) {
            this.sprite = ResourceManager.Images.SWITCHLEFT.getImage();
            SoundManager.getInstance().playSound(Sounds.LEVERPULL);
        } else {
            this.sprite = ResourceManager.Images.SWITCHRIGHT.getImage();
            SoundManager.getInstance().playSound(Sounds.LEVERPUSH);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " -- LEVER " + this.getMatches();
    }
}
