/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import s3proftaak.Shared.ILobby;
import s3proftaak.Shared.IServer;

/**
 *
 * @author Stan
 */
public class ServerAdministration extends UnicastRemoteObject implements IServer {
    private final List<ILobby> lobbies = new ArrayList<>();
    
    public ServerAdministration() throws RemoteException{
        lobbies.add(new Lobby(3, "AWEOSMEE", 2));
    }

    @Override
    public ILobby createLobby(String username, String lobbyname) throws RemoteException {
        return null;
    }

    @Override
    public void leaveLobby(String username) throws RemoteException {
        
    }

    @Override
    public List<ILobby> getLobbies() throws RemoteException {
        return this.lobbies;
    }
}
