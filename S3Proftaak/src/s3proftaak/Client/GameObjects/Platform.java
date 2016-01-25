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

                    if (diffX != 0) {
                        if (diffX > 0) {
                            for (Character character : ClientAdministration.getInstance().getGame().getGameCharacters()) {
                                // Right side
                                if (character.getRect().getMinX() <= this.getRect().getMaxX() + speed && character.getRect().getMinX() >= this.getRect().getMinX()) {
                                    if (character.getRect().getMaxY() >= this.getRect().getMinY() && character.getRect().getMinY() <= this.getRect().getMaxY()) {
                                        character.move(diffX < 0 ? speed : -speed);
                                    }
                                }
                            }
                        }

                        if (diffX < 0) {
                            for (Character character : ClientAdministration.getInstance().getGame().getGameCharacters()) {
                                // Left side
                                if (character.getRect().getMaxX() >= this.getRect().getMinX() - speed && character.getRect().getMaxX() <= this.getRect().getMaxX()) {
                                    if (character.getRect().getMaxY() >= this.getRect().getMinY() && character.getRect().getMinY() <= this.getRect().getMaxY()) {
                                        character.move(diffX < 0 ? speed : -speed);
                                    }
                                }
                            }
                        }
                    }

                    this.getRect().setX(this.getRect().getX() + diffX);

                    if (diffY != 0) {
                        for (Character character : ClientAdministration.getInstance().getGame().getGameCharacters()) {
                            // Top side
                            if (getRect().getMinX() <= character.getRect().getMaxX() && getRect().getMaxX() >= character.getRect().getMinX()) {
                                if (getRect().getMinY() - speed <= character.getRect().getMaxY() && getRect().getMaxY() >= character.getRect().getMaxY()) {
                                    character.getRect().setY(character.getRect().getY() - (diffY < 0 ? speed : -speed));
                                }
                            }

                            // Bottom side
                            if (getRect().getMinX() + 2 <= character.getRect().getMaxX() && getRect().getMaxX() - 2 >= character.getRect().getMinX()) {
                                if (getRect().getMaxY() + speed >= character.getRect().getMinY() && getRect().getMinY() <= character.getRect().getMinY()) {
                                    if (character.isOnGround()) {
                                        if (this.isGoingDown()) {
                                            character.die();
                                        }
                                    } else {
                                        character.getRect().setY(character.getRect().getY() - (diffY < 0 ? speed : -speed));
                                    }
                                }
                            }
                        }
                    }

                    this.getRect().setY(this.getRect().getY() + diffY);

                    if (ClientAdministration.getInstance().getGame().isMultiplayer()) {
                        if (ClientAdministration.getInstance().isHost()) {
                            try {
                                ClientAdministration.getInstance().getHost().updatePlatform(this.getId(), new PlatformPosition(this.getRect().getX() + offsetX, this.getRect().getY()));
                            } catch (RemoteException ex) {
                                // Alleen host mag updaten. Lijkt me sterk dat host lokaal een error krijgt ..
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

    public void updatePosition(float x, float y) {
        //float diffX = x - this.getRect().getX();
        //float diffY = y - this.getRect().getY();

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

        if (true) {
            if (diffX > 0) {
                for (Character character : ClientAdministration.getInstance().getGame().getGameCharacters()) {
                    // Right side
                    if (character.getRect().getMinX() <= this.getRect().getMaxX() - speedX && character.getRect().getMinX() >= this.getRect().getMinX()) {
                        if (character.getRect().getMaxY() >= this.getRect().getMinY() && character.getRect().getMinY() <= this.getRect().getMaxY()) {
                            character.move(diffX < 0 ? speedX : -speedX);
                        }
                    }
                }
            }

            if (diffX < 0) {
                for (Character character : ClientAdministration.getInstance().getGame().getGameCharacters()) {
                    // Left side
                    if (character.getRect().getMaxX() >= this.getRect().getMinX() - speedX && character.getRect().getMaxX() <= this.getRect().getMaxX()) {
                        if (character.getRect().getMaxY() >= this.getRect().getMinY() && character.getRect().getMinY() <= this.getRect().getMaxY()) {
                            character.move(diffX < 0 ? speedX : -speedX);
                        }
                    }
                }
            }
        }

        this.getRect().setX(x);

        if (true) {
            for (Character character : ClientAdministration.getInstance().getGame().getGameCharacters()) {
                // Top side
                if (getRect().getMinX() <= character.getRect().getMaxX() && getRect().getMaxX() >= character.getRect().getMinX()) {
                    if (getRect().getMinY() - speedY <= character.getRect().getMaxY() && getRect().getMaxY() >= character.getRect().getMaxY()) {
                        character.getRect().setY(character.getRect().getY() - (diffY < 0 ? speedY : -speedY));
                    }
                }

                // Bottom side
                if (getRect().getMinX() + 2 <= character.getRect().getMaxX() && getRect().getMaxX() - 2 >= character.getRect().getMinX()) {
                    if (getRect().getMaxY() + speedY >= character.getRect().getMinY() && getRect().getMinY() <= character.getRect().getMinY()) {
                        if (character.isOnGround()) {
                            if (this.isGoingDown()) {
                                character.die();
                            }
                        } else {
                            character.getRect().setY(character.getRect().getY() - (diffY < 0 ? speedY : -speedY));
                        }
                    }
                }
            }
        }

        this.getRect().setY(y);
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
