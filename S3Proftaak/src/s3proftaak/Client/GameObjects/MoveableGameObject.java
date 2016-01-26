/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.GameObjects;

import java.util.ArrayList;
import java.util.List;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Shared.Wrappers.PlatformPosition;

/**
 *
 * @author Stan
 */
public abstract class MoveableGameObject extends GameObject {

    public MoveableGameObject(float x, float y, float width, float height, boolean collision) {
        super(x, y, width, height, collision);
    }

    public void pushNearbyCharacters(PlatformPosition pp, MoveableGameObject pusher) {
        float diffX = pp.getDiffX();
        float diffY = pp.getDiffY();
        
        float speedX = pp.getSpeed() == -1 ? this.getRect().getX() - pp.getX() : pp.getSpeed();
        float speedY = pp.getSpeed() == -1 ? this.getRect().getY() - pp.getY() : pp.getSpeed();
        
        if (this instanceof Platform){
            System.out.println(speedY);
        }

        if (diffX != 0) {
            if (diffX > 0) {
                for (Character character : ClientAdministration.getInstance().getGame().getGameCharacters()) {
                    if (this instanceof Platform || (this != character && pusher != character)) {
                        // Right side
                        if (character.getRect().getMinX() <= this.getRect().getMaxX() + speedX && character.getRect().getMinX() >= this.getRect().getMinX()) {
                            if (character.getRect().getMaxY() >= this.getRect().getMinY() && character.getRect().getMinY() <= this.getRect().getMaxY()) {
                                character.move(diffX < 0 ? speedX : -speedX, 0, pp, this);
                            }
                        }
                    }
                }
            }

            if (diffX < 0) {
                for (Character character : ClientAdministration.getInstance().getGame().getGameCharacters()) {
                    if (this instanceof Platform || (this != character && pusher != character)) {
                        // Left side
                        if (character.getRect().getMaxX() >= this.getRect().getMinX() - speedX && character.getRect().getMaxX() <= this.getRect().getMaxX()) {
                            if (character.getRect().getMaxY() >= this.getRect().getMinY() && character.getRect().getMinY() <= this.getRect().getMaxY()) {
                                character.move(diffX < 0 ? speedX : -speedX, 0, pp, this);
                            }
                        }
                    }
                }
            }
        }

        

        if (diffY != 0) {
            for (Character character : ClientAdministration.getInstance().getGame().getGameCharacters()) {
                if (this instanceof Platform || (this != character && pusher != character)) {
                    // Top side
                    if (getRect().getMinX() <= character.getRect().getMaxX() && getRect().getMaxX() >= character.getRect().getMinX()) {
                        if (getRect().getMinY() - speedY <= character.getRect().getMaxY() && getRect().getMaxY() >= character.getRect().getMaxY()) {
                            character.move(0, diffY < 0 ? speedY : -speedY, pp, this);
                        }
                    }

                    // Bottom side
                    if (getRect().getMinX() + 2 <= character.getRect().getMaxX() && getRect().getMaxX() - 2 >= character.getRect().getMinX()) {
                        if (getRect().getMaxY() + speedY >= character.getRect().getMinY() && getRect().getMinY() <= character.getRect().getMinY()) {
                            if (character.isOnGround()) {
                                if (pp.isGoingDown()) {
                                    character.die();
                                }
                            } else {
                                character.move(0, diffY < 0 ? speedY : -speedY, pp, this);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isOnGround() {
        for (GameObject go : this.getGameObjectsBelow(2)) {
            if (go.hasCollision()) {
                return true;
            }
        }
        return false;
    }

    private List<GameObject> getGameObjectsBelow(int radius) {
        List<GameObject> gameObjects = new ArrayList<>();
        for (GameObject go : ClientAdministration.getInstance().getGame().getGameObjects()) {
            if (this.getRect().getMinX() <= go.getRect().getMaxX() && getRect().getMaxX() >= go.getRect().getMinX()) {
                if (this.getRect().getMaxY() <= go.getRect().getMinY() && go.getRect().getMinY() <= this.getRect().getMaxY() + radius) {
                    gameObjects.add(go);
                }
            }
        }
        return gameObjects;
    }
}
