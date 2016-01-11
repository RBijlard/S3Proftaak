/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Host;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.GameObjects.Block;
import s3proftaak.Client.GameObjects.Button;
import s3proftaak.Client.GameObjects.Character;
import s3proftaak.Client.GameObjects.Door;
import s3proftaak.Client.GameObjects.GameObject;
import s3proftaak.Client.GameObjects.Lever;
import s3proftaak.Client.GameObjects.MoveableBlock;
import s3proftaak.Client.GameObjects.Spike;
import s3proftaak.Client.GameObjects.Star;
import s3proftaak.Client.GameObjects.Weight;
import s3proftaak.Shared.IHost;
import s3proftaak.Shared.IMessage;
import s3proftaak.Shared.PlayerPosition;
import s3proftaak.fontys.BasicPublisher;
import s3proftaak.fontys.RemotePropertyListener;
import s3proftaak.util.ICare;

/**
 *
 * @author Berry-PC
 */
public class Host extends UnicastRemoteObject implements IHost, ICare {

    private List<GameObject> gameObjects;
    private BasicPublisher publisher;

    public Host(List<GameObject> gameObjects) throws RemoteException {
        this.gameObjects = gameObjects;
        this.publisher = new BasicPublisher(this, new String[]{"PlayerPosition", "ObjectPosition", "GameRestart", "GameStop", "ObjectActivation"});
    }

    public void collisionCheck(float vX, float vY, int id, boolean isEntering) {
        float x = this.gameObjects.get(id).getRect().getX();
        float y = this.gameObjects.get(id).getRect().getY();
        float temp_vX = 0, temp_vY = 0;
        boolean is_colliding = false;
        temp_vX = vX / 5;
        temp_vY = vY / 5;
        for (int i = 0; i < 5; i++) {
            this.gameObjects.get(id).getRect().setX(this.gameObjects.get(id).getRect().getX() + temp_vX);
            this.gameObjects.get(id).getRect().setY(this.gameObjects.get(id).getRect().getY() + temp_vY);
            if (this.isColliding(vX, vY, id, isEntering)) {
                is_colliding = true;
                break;
            }
        }
        if (is_colliding) {
            this.gameObjects.get(id).getRect().setX(this.gameObjects.get(id).getRect().getX() - temp_vX);
            this.gameObjects.get(id).getRect().setY(this.gameObjects.get(id).getRect().getY() - temp_vY);
        }
    }

    public boolean isColliding(float vX, float vY, int id, boolean isEntering) {
        Rectangle rect = this.gameObjects.get(id).getRect();
        for (GameObject go : this.gameObjects) {
            //check if colliding
            if (go.getRect().intersects(rect) || go.getRect().contains(rect)) {
                if (go.getId() != id) {
                    //check what object
                    if (go instanceof Block || go instanceof Character) {
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
                            this.updatePositionObject(go.getRect(), go.getId());

                        }
                        if (rect.getMinX() < go.getRect().getMaxX() && rect.getMaxX() > go.getRect().getMinX()) {

                            for (int b = 1; b < 20; b++) {
                                if (rect.getMinY() >= go.getRect().getMaxY() - b) {
                                    this.updateRestartGame();
                                    return true;
                                }
                            }
                        }

                        return true;
                    } else if (go instanceof Spike) {
                        this.updateRestartGame();
                        return false;
                    } else if (go instanceof Button) {
                        if (rect.getMinY() - 1 < go.getRect().getY()) {
                            if (!((Button) go).isActive()) {
                                ((Button) go).setActive(true);
                                this.updateActivationObject(go.getId(), true);
                            }
                        }
                        return true;
                    } else if (go instanceof Lever && isEntering) {
                        if (!((Lever) go).isActive()) {
                            ((Lever) go).setActive(true);
                        } else {
                            ((Lever) go).setActive(false);
                        }
                        this.updateActivationObject(go.getId(), ((Lever) go).isActive());
                    } else if (go instanceof Door) {
                        if (rect.getX() < go.getRect().getX() && rect.getX() + rect.getWidth() > go.getRect().getX() + go.getRect().getWidth()) {
                            System.out.println(((Door) go).isActive());
                            if (((Door) go).isActive()) {
                                System.out.println("finish");
                                ((Door) go).finish();
                                try {
                                    ClientAdministration.getInstance().getCurrentLobby().stopGame();
                                } catch (RemoteException ex) {
                                    ClientAdministration.getInstance().stopGame();
                                }
                            }
                        }
                    } else if (go instanceof Star) {
                        if (!((Star) go).isRemoved()) {
                            ((Star) go).setActive(false);
                            this.updateActivationObject(go.getId(), false);
                        }
                    } else if (go instanceof Weight) {
                        if (rect.getMinX() < go.getRect().getMaxX() && rect.getMaxX() > go.getRect().getMinX()) {
                            if (rect.getMinY() >= go.getRect().getMaxY() - 5) {
                                if (!((Weight) go).isActive()) {
                                    this.updateRestartGame();
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

    @Override
    public void askForMove(float vX, float vY, int id, boolean isEntering) {
        if (this.gameObjects.get(id) != null) {

            this.collisionCheck(vX, vY, id, isEntering);

            PlayerPosition pp = null;
            if (this.gameObjects.get(id).getRect().getHeight() > 69) {
                //pp = new PlayerPosition(this.gameObjects.get(id).getRect().getX(), this.gameObjects.get(id).getRect().getY(), 0, false);
            } else {
                //pp = new PlayerPosition(this.gameObjects.get(id).getRect().getX(), this.gameObjects.get(id).getRect().getY(), 0, true);
            }

            updatePositionPlayer(pp, id);
        }
    }

    public void updatePositionPlayer(PlayerPosition pp, int id) {
        this.publisher.inform(this, "PlayerPosition", id, pp);
    }

    public void updatePositionObject(Rectangle rect, int id) {
        this.publisher.inform(this, "ObjectPosition", id, rect);
    }

    public void updateActivationObject(int id, boolean b) {
        this.publisher.inform(this, "ObjectActivation", id, b);
    }

    public void updateRestartGame() {
        this.publisher.inform(this, "GameRestart", null, null);
    }

    public void updateStopGame() {
        this.publisher.inform(this, "GameStop", null, null);
    }

    @Override
    public void addListener(String username, RemotePropertyListener listener, String property) throws RemoteException {
        this.publisher.addListener(username, listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        this.publisher.removeListener(listener, property);
    }

    @Override
    public void sendMessage(IMessage message) throws RemoteException {
        //#ROEL BIJLARD -> INGAME CHAT FUNCTIE
    }

    @Override
    public void playerLostConnection(String playerName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
