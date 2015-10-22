/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.GameObjects.Interfaces;

import java.util.List;
import s3proftaak.GameObjects.GameObject;

/**
 *
 * @author Stan
 */
public interface IPressable {
    List<GameObject> getMatchedObjects();
    void setActive(boolean active);
    boolean isActive();
}
