/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.GameObjects;

import java.util.ArrayList;
import java.util.List;
import s3proftaak.Client.ClientAdministration;

/**
 *
 * @author Stan
 */
public class MoveableGameObject extends GameObject {

    public MoveableGameObject(float x, float y, float width, float height, boolean collision) {
        super(x, y, width, height, collision);
    }
    
    public boolean isOnGround(){
        for (GameObject go : this.getGameObjectsBelow(2)){
            if (go.hasCollision()){
                return true;
            }
        }
        return false;
    }
    
    private List<GameObject> getGameObjectsBelow(int radius){
        List<GameObject> gameObjects = new ArrayList<>();
        for (GameObject go : ClientAdministration.getInstance().getGame().getGameObjects()){
            if (this.getRect().getMinX() <= go.getRect().getMaxX() && getRect().getMaxX() >= go.getRect().getMinX()) {
                if (this.getRect().getMaxY() <= go.getRect().getMinY() && go.getRect().getMinY() <= this.getRect().getMaxY() + radius){
                    gameObjects.add(go);
                }
            }
        }
        return gameObjects;
    }
}
