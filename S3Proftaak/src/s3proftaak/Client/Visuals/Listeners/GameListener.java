/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals.Listeners;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.Game;
import s3proftaak.Client.GameObjects.GameObject;
import s3proftaak.Client.GameObjects.Interfaces.IRemoteUpdatable;
import s3proftaak.Client.GameObjects.MoveableBlock;
import s3proftaak.Shared.PlayerPosition;

/**
 *
 * @author Stan
 */
public class GameListener extends BasicListener {

    public GameListener() throws RemoteException {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        Game game = ClientAdministration.getInstance().getGame();
        if (game != null) {
            if (!evt.getOldValue().toString().equals(ClientAdministration.getInstance().getAccount().getUsername())) {
                switch (evt.getPropertyName()) {
                    case "Rect":
                        int offset = 0;
                        if (game.getGameCharacters() != null) {
                            for (s3proftaak.Client.GameObjects.Character c : game.getGameCharacters()) {
                                if (c.getName().equals(ClientAdministration.getInstance().getAccount().getUsername())) {
                                    offset = (int) c.getOffsetX();
                                }
                            }
                            for (s3proftaak.Client.GameObjects.Character c : game.getGameCharacters()) {

                                if (c.getName().equals(evt.getOldValue().toString())) {

                                    PlayerPosition pp = (PlayerPosition) evt.getNewValue();

                                    if (c.safeMoveTo(pp.getX() - offset, pp.getY())) {
                                        c.getRect().setX(pp.getX() - offset);
                                        c.getRect().setY(pp.getY());
                                    } else {
                                        c.setvY(pp.getvY());
                                    }

                                    c.setIsCrouching(pp.isCrouch());

                                    break;
                                }
                            }
                        }

                        break;

                    case "Objects":
                        GameObject go = ClientAdministration.getInstance().getGame().getGameObject(Integer.parseInt(evt.getOldValue().toString()));

                        if (go != null) {
                            if (go instanceof MoveableBlock) {
                                System.out.println("dx: " + Integer.parseInt(evt.getNewValue().toString()));
                                ((MoveableBlock) go).setDx(Integer.parseInt(evt.getNewValue().toString()));

                                ///ALTERED BY BERRY
//                                for (s3proftaak.Client.GameObjects.Character c : game.getGameCharacters()) {
//                                    offset = (int) c.getOffsetX();
//                                    ((MoveableBlock) go).getRect().setX(Integer.parseInt(evt.getNewValue().toString()) - offset);
//                                }
                                ///END
                            }

                            if (go instanceof IRemoteUpdatable) {
                                ((IRemoteUpdatable) go).setActive(Boolean.valueOf(evt.getNewValue().toString()));
                            }
                        }

                        break;
                }
            }
        }
    }

    @Override
    public void startListening() {
        try {
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Players");
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Level");
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Host");

            String username = ClientAdministration.getInstance().getAccount().getUsername();

//            ClientAdministration.getInstance().getCurrentLobby().addListener(username, this, "Rect");
//            ClientAdministration.getInstance().getCurrentLobby().addListener(username, this, "Objects");
            ClientAdministration.getInstance().getHostbackup().addListener(username, this, "Rect");
            ClientAdministration.getInstance().getHostbackup().addListener(username, this, "Objects");
            ClientAdministration.getInstance().getHostbackup().addListener(username, this, "Chat");

        } catch (RemoteException ex) {
            ClientAdministration.getInstance().stopGame("");
        }
    }

    @Override
    public void stopListening() {
        try {
            String username = ClientAdministration.getInstance().getAccount().getUsername();

            ClientAdministration.getInstance().getCurrentLobby().addListener(username, this, "Players");
            ClientAdministration.getInstance().getCurrentLobby().addListener(username, this, "Level");
            ClientAdministration.getInstance().getCurrentLobby().addListener(username, this, "Host");

            ClientAdministration.getInstance().getHostbackup().removeListener(this, "Rect");
            ClientAdministration.getInstance().getHostbackup().removeListener(this, "Objects");
            ClientAdministration.getInstance().getHostbackup().removeListener(this, "Chat");

        } catch (RemoteException ex) {
            ClientAdministration.getInstance().stopGame("");
        }
    }
}
