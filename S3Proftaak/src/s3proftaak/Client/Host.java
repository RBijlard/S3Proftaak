/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.Client.GameObjects.Block;
import s3proftaak.Client.GameObjects.Button;
import s3proftaak.Client.GameObjects.Door;
import s3proftaak.Client.GameObjects.GameObject;
import s3proftaak.Client.GameObjects.Lever;
import s3proftaak.Client.GameObjects.MoveableBlock;
import s3proftaak.Client.GameObjects.Spike;
import s3proftaak.Client.GameObjects.Star;
import s3proftaak.Client.GameObjects.Weight;
import s3proftaak.fontys.RemotePropertyListener;

/**
 *
 * @author Berry-PC
 */
public class Host extends UnicastRemoteObject implements RemotePropertyListener, Serializable{
    
    private Game game; 
    private Rectangle r;
    
    public Host() throws RemoteException{
        ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Host-rect");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        Rectangle r = (Rectangle)evt.getNewValue();
        this.r = r;
        
        if(isColliding()){
            ClientAdministration.getInstance().getCurrentLobby().updateMove(r,false);
        }
        else {
            ClientAdministration.getInstance().getCurrentLobby().updateMove(r,true);            
        }
    }
    
    public boolean isColliding() {//GameContainer gc
        Rectangle rect = this.r;
        for (GameObject go : game.getGameObjects()) {
            //check if colliding
            if (go.getRect().intersects(rect) || go.getRect().contains(rect)) {
                if(go.getRect() != this.r){
                    //check what object
                    if (go instanceof Block || go instanceof s3proftaak.Client.GameObjects.Character) {
                        return true;
                    } else if (go instanceof MoveableBlock) {
                        if (go.getRect().getMinY() + 1 < rect.getMaxY()) {
                            int i = 0;
                            if (go.getRect().getX() > rect.getX()) {
                                i = 1;
                            }
                            if (go.getRect().getX() < rect.getX()) {
                                i = -1;
                            }
                            ((MoveableBlock) go).setDx(i);
                        }
                        if (this.r.getMinX() < go.getRect().getMaxX() && this.r.getMaxX() > go.getRect().getMinX()) {

                            for (int b = 1; b < 20; b++) {
                                if (this.r.getMinY() >= go.getRect().getMaxY() - b) {
                                    //this.die();
                                    return true;
                                }
                            }
                        }

                        return true;
                    } else if (go instanceof Spike) {
                        //this.die();
                        return false;
                    } else if (go instanceof Button) {
                        if (this.r.getMinY() - 1 < go.getRect().getY()) {
                            if (!((Button) go).isActive()) {
                                ((Button) go).setActive(true);
                            }
                        }
                        return true;
                    } else if (go instanceof Lever ) {//&& gc.getInput().isKeyPressed(Input.KEY_ENTER)
                        if (!((Lever) go).isActive()) {
                            ((Lever) go).setActive(true);
                        } else {
                            ((Lever) go).setActive(false);
                        }
                        return true;
                    } else if (go instanceof Door) {
                        if (this.r.getX() < go.getRect().getX() && this.r.getX() + this.r.getWidth() > go.getRect().getX() + go.getRect().getWidth()) {
                            System.out.println(((Door) go).isActive());
                            if (((Door) go).isActive()) {
                                System.out.println("finish");
                                ((Door) go).finish();
                            }
                        }
                    } else if (go instanceof Star) {
                        if (!((Star) go).isRemoved()) {
                            ((Star) go).remove();
                            SoundManager.getInstance().playSound(SoundManager.Sounds.COINPICKUP);
                        }
                    } else if (go instanceof Weight) {
                        if (this.r.getMinX() < go.getRect().getMaxX() && this.r.getMaxX() > go.getRect().getMinX()) {
                            if (this.r.getMinY() >= go.getRect().getMaxY() - 5) {
                                if (!((Weight) go).isActive()) {
                                    //this.die();
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
