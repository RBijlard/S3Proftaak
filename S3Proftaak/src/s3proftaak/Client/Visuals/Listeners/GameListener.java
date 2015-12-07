/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals.Listeners;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.Game;

/**
 *
 * @author Stan
 */
public class GameListener extends BasicListener {

    public GameListener() throws RemoteException {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        System.out.println("received property 3. " + evt.getPropertyName());
        Game game = ClientAdministration.getInstance().getGame();
        if (game != null) {
            if (!evt.getOldValue().toString().equals(ClientAdministration.getInstance().getAccount().getUsername())) {
                switch (evt.getPropertyName()) {
                    case "Rect":
                        int offset = 0;
                        if (game.getGameCharacters() != null) {
                            for(s3proftaak.Client.GameObjects.Character c : game.getGameCharacters()){
                                if(c.getName().equals(ClientAdministration.getInstance().getAccount().getUsername())){
                                    offset = (int)c.getOffsetX();
                                }
                            }
                            for (s3proftaak.Client.GameObjects.Character c : game.getGameCharacters()) {
                                
                                if (c.getName().equals(evt.getOldValue().toString())) {
                                    
                                    Rectangle rect = (Rectangle) evt.getNewValue();
                                    
                                    c.getRect().setX(rect.getX() - offset);
                                    c.getRect().setY(rect.getY());
                                    c.getRect().setHeight(rect.getHeight());
                                    
                                    break;
                                }
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

            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Rect");
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stopListening() {
        try {
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Players");
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Level");
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Host");

            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Rect");

        } catch (RemoteException ex) {
            Logger.getLogger(LobbyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
