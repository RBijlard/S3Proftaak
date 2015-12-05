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
import s3proftaak.fontys.BasicPublisher;
import s3proftaak.fontys.RemotePropertyListener;

/**
 *
 * @author Stan
 */
public class ServerAdministration extends UnicastRemoteObject implements IServer {

    private static ServerAdministration instance;
    private final List<Lobby> lobbies;
    private final BasicPublisher publisher;

    public ServerAdministration() throws RemoteException {
        instance = (ServerAdministration) this;
        this.lobbies = new ArrayList<>();
        this.publisher = new BasicPublisher(new String[]{"LobbyList"});

        // Remove later
        this.lobbies.add(new Lobby("Awesome", 2));
    }

    @Override
    public ILobby createLobby(String lobbyname) throws RemoteException {
        Lobby tempLobby = new Lobby(lobbyname, 2);
        this.lobbies.add(tempLobby);
        this.informLobbyListMembers();
        return tempLobby;
    }

    @Override
    public List<ILobby> getLobbies() throws RemoteException {
        return getLobbiesInLobby();
    }

    public void removeLobby(Lobby lobby) {
        this.lobbies.remove(lobby);
        this.informLobbyListMembers();
    }

    public static ServerAdministration getInstance() {
        return instance;
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }

    private List<ILobby> getLobbiesInLobby() {
        List<ILobby> tempLobbies = new ArrayList<>();

        for (Lobby lobby : this.lobbies) {
            if (!lobby.hasStarted()) {
                tempLobbies.add(lobby);
            }
        }

        return tempLobbies;
    }

    private void informLobbyListMembers() {
        if (!getLobbiesInLobby().isEmpty()) {
            this.publisher.inform(this, "LobbyList", null, getLobbiesInLobby());
        }
    }
}
