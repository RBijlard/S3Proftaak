/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals.Listeners;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import s3proftaak.Client.RMIClient;
import s3proftaak.Client.Visuals.Multiplayer;
import s3proftaak.Shared.ILobby;

/**
 *
 * @author Stan
 */
public class MultiplayerListener extends BasicListener {

    private final Multiplayer multiplayerScreen;
    
    public MultiplayerListener(Multiplayer multiplayerScreen) throws RemoteException {
        this.multiplayerScreen = multiplayerScreen;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        multiplayerScreen.updateList((List<ILobby>) evt.getNewValue());
    }
    
    @Override
    public void startListening(){
        try {
            RMIClient.getServerAdministration().addListener(this, "LobbyList");
        } catch (RemoteException ex) {
            Logger.getLogger(MultiplayerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void stopListening(){
        try {
            RMIClient.getServerAdministration().removeListener(this, "LobbyList");
        } catch (RemoteException ex) {
            Logger.getLogger(MultiplayerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
