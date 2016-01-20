package s3proftaak.Client.GameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import s3proftaak.Client.ClientAdministration;
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
        super(x, y, width, height, false);
        this.sprite = ResourceManager.Images.STAR.getImage();
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        //render star animation/img
        if (sprite != null) {
            sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset());
        }

    }

    @Override
    public void setActive(boolean active) {
        if (!this.removed) {
            SoundManager.getInstance().playSound(SoundManager.Sounds.COINPICKUP);
            this.sprite = null;
            this.removed = true;
            ClientAdministration.getInstance().getGame().starCollected();
            ClientAdministration.getInstance().getGame().removeGameObject(this);
        }
    }

    public boolean isRemoved() {
        return this.removed;
    }

    @Override
    public String toString() {
        return super.toString() + " -- STAR " + this.getMatches();
    }

}
