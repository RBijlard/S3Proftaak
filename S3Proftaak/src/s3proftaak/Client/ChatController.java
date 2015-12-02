package s3proftaak.Client;

import s3proftaak.Shared.IMessage;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import s3proftaak.Client.Visuals.Lobby;
import s3proftaak.fontys.RemotePropertyListener;

/**
 *
 * @author Stan
 */
public class ChatController extends UnicastRemoteObject implements RemotePropertyListener {

    private final Lobby lobby;

    public ChatController(Lobby lobby) throws RemoteException {
        this.lobby = lobby;

        try {
            addListener(this, "Administrative");
            addListener(this, "Chat");
            addListener(this, "Players");
            addListener(this, "Ready");
            addListener(this, "Level");
            addListener(this, "Host");
        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        switch (evt.getPropertyName()) {
            case "Administrative":
                if (evt.getOldValue().toString().equals("StartGame")) {
                    ClientAdministration.getInstance().startGame(new Game("De Game", 1, evt.getNewValue().toString()));
                }
                break;

            case "Chat":
                this.lobby.displayMessage((IMessage) evt.getNewValue());
                break;

            case "Players":
                System.out.println("received players");
                this.lobby.updatePlayerList((List<String>) evt.getNewValue());
                break;

            case "Ready":
                // Old = username, New = State
                break;

            case "Level":
                // 
                System.out.println(evt.getNewValue().toString());
                this.lobby.comboboxSet(evt.getNewValue().toString());
                break;
            case "Host":
                System.out.println("received host");
                if (ClientAdministration.getInstance().getAccount().getUsername().equals(evt.getNewValue().toString())) {
                    System.out.println("Setting is host : ");
                    this.lobby.setIsHost(true);
                }
                break;
        }

    }

    public void sendMessage(IMessage message) {
        try {
            ClientAdministration.getInstance().getCurrentLobby().sendMessage(message);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        ClientAdministration.getInstance().getCurrentLobby().addListener(listener, property);
    }

    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        ClientAdministration.getInstance().getCurrentLobby().removeListener(listener, property);
    }

    public void leaveLobby() {
        try {
            removeListener(this, "Administrative");
            removeListener(this, "Chat");
            removeListener(this, "Players");
            removeListener(this, "Ready");
            removeListener(this, "Level");
            removeListener(this, "Host");

            ClientAdministration.getInstance().getCurrentLobby().removePlayer(ClientAdministration.getInstance().getAccount().getUsername());

        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Lobby getLobby() {
        return this.lobby;
    }
}
