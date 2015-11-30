/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import s3proftaak.Client.Visuals.Lobby;
import s3proftaak.Shared.IClient;

/**
 *
 * @author Stan
 */
public class ClientData extends UnicastRemoteObject implements IClient {
    
    public ClientData() throws RemoteException{}
    
    @Override
    public String getName() throws RemoteException {
        return ClientAdministration.getInstance().getAccount().getUsername();
    }

    @Override
    public void updatePlayerList(List<String> players) throws RemoteException {
        if (ClientAdministration.getCurrentScreen() instanceof Lobby){
            ((Lobby) ClientAdministration.getCurrentScreen()).updatePlayerList(players);
        }
    }
    
}
