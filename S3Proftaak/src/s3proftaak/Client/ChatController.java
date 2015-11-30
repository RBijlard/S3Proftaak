package s3proftaak.Client;

import s3proftaak.Shared.IMessage;
import fontys.RemotePropertyListener;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import s3proftaak.Client.Visuals.Lobby;

/**
 *
 * @author Stan
 */
public class ChatController extends UnicastRemoteObject implements RemotePropertyListener {

    private Lobby lobby;
    
    public ChatController(Lobby lobby) throws RemoteException {
        this.lobby = lobby;
        
        try {
            addListener(this, "Chat");
            addListener(this, "Players");
        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
       switch(evt.getPropertyName()){
           case "Chat":
               this.lobby.displayMessage((IMessage) evt.getNewValue());
               break;
               
           case "Players":
               this.lobby.updatePlayerList((List<String>) evt.getNewValue());
               break;
       }
        
        
    }
    
    public void sendMessage(IMessage message){
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
}