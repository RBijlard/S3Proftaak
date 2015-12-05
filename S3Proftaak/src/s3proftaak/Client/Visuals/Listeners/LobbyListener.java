package s3proftaak.Client.Visuals.Listeners;

import s3proftaak.Shared.IMessage;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.Game;
import s3proftaak.Client.Visuals.Lobby;
import s3proftaak.fontys.RemotePropertyListener;

/**
 *
 * @author Stan
 */
public class LobbyListener extends BasicListener implements Serializable, RemotePropertyListener {

    private final Lobby lobbyScreen;

    private int amount = 1;
    private List<String> names;

    public LobbyListener(Lobby lobby) throws RemoteException {
        this.lobbyScreen = lobby;

        this.names = new ArrayList<>();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "Administrative":
                if (evt.getOldValue().toString().equals("StartGame")) {
                    ClientAdministration.getInstance().startGame(new Game("De Game", amount, evt.getNewValue().toString(), this.names));
                }

                if (evt.getOldValue().toString().equals("StopGame")) {
                    ClientAdministration.getInstance().stopGame();
                }
                break;

            case "Chat":
                this.lobbyScreen.displayMessage((IMessage) evt.getNewValue());
                break;

            case "Players":
                System.out.println("received players");
                if (evt.getNewValue() != null) {
                    this.names = (List<String>) evt.getNewValue();
                    this.amount = this.names.size();
                    this.lobbyScreen.updatePlayerList(this.names);
                }
                break;

            case "Ready":
                // Old = username, New = State
                break;

            case "Level":
                // 
                System.out.println(evt.getNewValue().toString());
                this.lobbyScreen.comboboxSet(evt.getNewValue().toString());
                break;
            case "Host":
                System.out.println("received host");
                if (ClientAdministration.getInstance().getAccount().getUsername().equals(evt.getNewValue().toString())) {
                    System.out.println("Setting is host : ");
                    this.lobbyScreen.setIsHost(true);
                }
                break;
        }
    }

    @Override
    public void startListening() {
        try {
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Administrative");
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Chat");
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Players");
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Ready");
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Level");
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Host");
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stopListening() {
        try {
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Administrative");
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Chat");
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Players");
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Ready");
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Level");
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Host");

            ClientAdministration.getInstance().getCurrentLobby().removePlayer(ClientAdministration.getInstance().getAccount().getUsername());

        } catch (RemoteException ex) {
            Logger.getLogger(LobbyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     public void startGame() {
     try {
     removeListener(this, "Players");
     removeListener(this, "Ready");
     removeListener(this, "Level");
     removeListener(this, "Host");

     addListener(this, "X");
     addListener(this, "Y");
     } catch (RemoteException ex) {
     Logger.getLogger(LobbyListener.class.getName()).log(Level.SEVERE, null, ex);
     }
     }

     public void stopGame() {
     try {
     addListener(this, "Players");
     addListener(this, "Ready");
     addListener(this, "Level");
     addListener(this, "Host");

     removeListener(this, "X");
     removeListener(this, "Y");
     } catch (RemoteException ex) {
     Logger.getLogger(LobbyListener.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     */
}
