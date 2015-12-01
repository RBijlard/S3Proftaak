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

    private final List<Lobby> lobbies = new ArrayList<>();

    public ServerAdministration() throws RemoteException {
        this.lobbies.add(new Lobby("Awesome", 2));
    }

    @Override
    public ILobby createLobby(String lobbyname) throws RemoteException {
        Lobby tempLobby = new Lobby(lobbyname, 2);
        this.lobbies.add(tempLobby);
        return tempLobby;
    }

    @Override
    public List<ILobby> getLobbies() throws RemoteException {
        List<ILobby> tempLobbies = new ArrayList<>();
        
        for (Lobby lobby : lobbies){
            if (!lobby.hasStarted()){
                tempLobbies.add(lobby);
            }
        }
        
        return tempLobbies;
    }
}
