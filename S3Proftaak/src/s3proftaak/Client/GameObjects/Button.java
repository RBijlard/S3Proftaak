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
public class Button extends GameObject implements IPressable, IRenderable {

    private boolean isActive = false;
    private Image sprite;
    private final Image spriteUp, spriteDown;

    public Button(float x, float y, float width, float height) {
        super(x, y, width, height);

        spriteDown = ResourceManager.Images.BUTTONRED_PRESSED.getImage();
        spriteUp = ResourceManager.Images.BUTTONRED.getImage();

        this.changeImage(isActive);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        //render button animation/img
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
        if (isActive != active) {
            this.isActive = active;
            changeImage(active);

            if (!getMatchedObjects().isEmpty()) {

                for (GameObject po : getMatchedObjects()) {
                    boolean enable = true;

                    for (GameObject mo : po.getMatchedObjects()) {
                        if (mo instanceof IPressable) {
                            if (!((IPressable) mo).isActive()) {
                                enable = false;
                                break;
                            }
                        }
                    }

                    if (enable) {
                        ((IStateChangeable) po).setActive(true);
                    }
                }
            }
        }
    }

    private void changeImage(boolean active) {
        if (active) {
            this.sprite = spriteDown;
            SoundManager.getInstance().playSound(Sounds.BUTTONPRESS);
        } else {
            this.sprite = spriteUp;
            SoundManager.getInstance().playSound(Sounds.BUTTONRELEASE);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " -- BUTTON " + this.getMatches();
    }
}
