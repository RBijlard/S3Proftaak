/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.GameObjects.Interfaces;

import java.util.List;
import s3proftaak.Client.GameObjects.GameObject;

/**
 *
 * @author Stan
 */
public interface IStateChangeable extends IRemoteUpdatable {
    List<GameObject> getMatchedObjects();
    boolean isActive();
}
