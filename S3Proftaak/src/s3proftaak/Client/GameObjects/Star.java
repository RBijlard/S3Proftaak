package s3proftaak.Client.GameObjects;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import s3proftaak.Client.GameObjects.Interfaces.IRemoteUpdatable;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.ResourceManager;
import s3proftaak.Client.SoundManager;

/**
 *
 * @author Berry-PC
 */
public class Star extends GameObject implements IRemoteUpdatable, IRenderable {

    private Image sprite;
    private boolean removed;

    public Star(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.sprite = ResourceManager.getImage(ResourceManager.Images.STAR);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        //render star animation/img
        if (sprite != null) {
            sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset());
        }

    }

    public int calculateOffset() {
        return (int) (70 - this.getRect().getHeight());
    }

    @Override
    public void setActive(boolean active) {
        SoundManager.getInstance().playSound(SoundManager.Sounds.COINPICKUP);
        this.sprite = null;
        this.removed = true;
    }

    public boolean isRemoved() {
        return this.removed;
    }

    @Override
    public String toString() {
        return super.toString() + " -- STAR " + this.getMatches();
    }

}
