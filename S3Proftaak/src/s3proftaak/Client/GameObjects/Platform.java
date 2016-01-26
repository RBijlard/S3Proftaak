package s3proftaak.Client.GameObjects;

import java.rmi.RemoteException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IUpdateable;
import s3proftaak.Client.ResourceManager;
import s3proftaak.Shared.Wrappers.PlatformPosition;

/**
 *
 * @author Stan
 */
public class Platform extends MoveableGameObject implements IUpdateable, IRenderable {

    private final Image sprite;
    private int currentTarget;
    private final int speed = 2;
    private boolean goingDown;

    public Platform(float x, float y, float width, float height) {
        super(x, y, width, height, true);
        this.sprite = ResourceManager.Images.BOXITEM.getImage();

        // Return position
        this.addMatchedObject(new Point(x, y, width, height));
    }

    @Override
    public void update(GameContainer gc) {
        if (!this.getMatchedObjects().isEmpty()) {
            if (!ClientAdministration.getInstance().getGame().isMultiplayer() || (ClientAdministration.getInstance().getGame().isMultiplayer() && ClientAdministration.getInstance().isHost())) {
                if (!reachedTarget()) {
                    int diffX = 0;
                    int diffY = 0;

                    float offsetX = ClientAdministration.getInstance().getGame().getOffsetX();

                    if (this.getRect().getX() > getTargetRectangle().getX() - offsetX) {
                        diffX = -speed;
                    }

                    if (this.getRect().getX() < getTargetRectangle().getX() - offsetX) {
                        diffX = speed;
                    }

                    if (this.getRect().getY() > getTargetRectangle().getY()) {
                        diffY = -speed;
                    }

                    if (this.getRect().getY() < getTargetRectangle().getY()) {
                        diffY = speed;
                    }

                    goingDown = diffY > 0;

                    this.pushNearbyCharacters(new PlatformPosition(0, 0, diffX, diffY, speed, this.isGoingDown()), null);

                    this.getRect().setX(this.getRect().getX() + diffX);
                    this.getRect().setY(this.getRect().getY() + diffY);

                    if (ClientAdministration.getInstance().getGame().isMultiplayer()) {
                        if (ClientAdministration.getInstance().isHost()) {
                            try {
                                System.out.println("SENT");
                                ClientAdministration.getInstance().getHost().updatePlatform(this.getId(), new PlatformPosition(this.getRect().getX() + offsetX, this.getRect().getY()));
                            } catch (RemoteException ex) {
                                // Alleen host mag updaten. Lijkt me sterk dat host lokaal een error krijgt ..
                                System.out.println(ex);
                            }
                        }
                    }
                } else {
                    this.setNextTarget();
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset());
    }

    public boolean isGoingDown() {
        return this.goingDown;
    }

    public void updatePosition(PlatformPosition pp) {
        float x = pp.getX() - ClientAdministration.getInstance().getGame().getOffsetX();
        float y = pp.getY();

        float diffX = 0;
        float diffY = 0;

        float speedX = this.getRect().getX() - x;
        float speedY = this.getRect().getY() - y;

        if (this.getRect().getX() > x) {
            diffX = -speedX;
        }

        if (this.getRect().getX() < x) {
            diffX = speedX;
        }

        if (this.getRect().getY() > y) {
            diffY = -speedY;
        }

        if (this.getRect().getY() < y) {
            diffY = speedY;
        }

        goingDown = diffY > 0;

        this.pushNearbyCharacters(new PlatformPosition(x, y, diffX, diffY, -1, this.isGoingDown()), null);

        this.getRect().setX(x);
    }

    private void setNextTarget() {
        if (currentTarget + 1 < this.getMatchedObjects().size()) {
            currentTarget++;
        } else {
            currentTarget = 0;
        }
    }

    private Rectangle getTargetRectangle() {
        return this.getMatchedObjects().get(currentTarget).getRect();
    }

    private boolean reachedTarget() {
        float offsetX = ClientAdministration.getInstance().getGame().getOffsetX();
        getTargetRectangle().setX(getTargetRectangle().getX() - offsetX);
        boolean value = this.getRect().contains(getTargetRectangle()) || this.getRect().intersects(getTargetRectangle());
        getTargetRectangle().setX(getTargetRectangle().getX() + offsetX);
        return value;
    }

    @Override
    public String toString() {
        return super.toString() + " -- PLATFORM " + this.getMatches();
    }

}
