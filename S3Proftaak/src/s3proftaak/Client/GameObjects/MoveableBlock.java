package s3proftaak.Client.GameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IUpdateable;
import s3proftaak.Client.ResourceManager;

/**
 *
 * @author Stan
 */
public class MoveableBlock extends GameObject implements IUpdateable, IRenderable {

    private int dx;
    private final Image sprite;
    private boolean isFalling;

    public MoveableBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.sprite = ResourceManager.Images.BOXITEM.getImage();
        this.isFalling = false;
    }

    @Override
    public void update(GameContainer gc) {

        for (int a = 0; a < 5; a++) {
            if (dx != 0) {
                if (!this.isColliding()) {
                    this.getRect().setX(this.getRect().getX() + (dx));
                    if (this.isColliding()) {
                        this.getRect().setX(this.getRect().getX() - (dx));
                    }
                }
                dx = 0;
            }
        }

        boolean verticalCollision = false;
        for (GameObject go : ClientAdministration.getInstance().getGame().getGameObjects()) {
            if (go != this) {
                if ((go.getRect().intersects(this.getRect()) || go.getRect().contains(this.getRect())) && !((go instanceof Spike) || (go instanceof Button))) {
                    verticalCollision = true;
                } else if ((go.getRect().intersects(this.getRect()) || go.getRect().contains(this.getRect())) && (go instanceof Button)) {
                    if (!((Button) go).isActive()) {
                        ((Button) go).setActive(true);
                    }
                    verticalCollision = true;
                }
            }
        }
        if (verticalCollision) {
            this.isFalling = true;
            //dont fall down
        } else {
            this.isFalling = false;
            //verticalCollision = false, fall down
            for (int b = 0; b < 5; b++) {
                this.getRect().setY(this.getRect().getY() + 2);
            }
        }
    }

    public boolean isFalling() {
        return this.isFalling;
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset());
    }

    public boolean isColliding() {
        for (GameObject go : ClientAdministration.getInstance().getGame().getGameObjects()) {
            if (go != this) {
                if (go.getRect().intersects(this.getRect()) || go.getRect().contains(this.getRect())) {
                    if (go instanceof Block) {
                        if (this.getRect().getMaxY() != go.getRect().getMinY()) {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public boolean safeMoveTo(float x, float y) {
        GameObject tempGo = new Block(x, y, getRect().getWidth(), getRect().getHeight());

        for (GameObject go : ClientAdministration.getInstance().getGame().getGameObjects()) {
            //check if colliding
            if (go.getRect().intersects(tempGo.getRect()) || go.getRect().contains(tempGo.getRect())) {
                if (go != this) {
                    //check what object
                    if (go instanceof MoveableBlock || go instanceof Character) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " -- MOVEABLEBLOCK";
    }

}
