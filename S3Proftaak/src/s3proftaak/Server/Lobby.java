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
import java.util.logging.Level;
import java.util.logging.Logger;
import s3proftaak.Shared.IChat;
import s3proftaak.Shared.IClient;
import s3proftaak.Shared.ILobby;

/**
 *
 * @author S33D
 */
public class Lobby extends UnicastRemoteObject implements ILobby {

    private final int id;
    private String level;
    private final List<IClient> players = new ArrayList<>();
    private String name;
    private String amountOfPlayers;
    private int max;
    private Chat chat;

    public Lobby(int id, String name, int maxPlayers) throws RemoteException {
        this.id = id;
        this.name = name;
        this.max = maxPlayers;
        amountOfPlayers = 0 + "/" + max;

        try {
            this.chat = new Chat();
        } catch (RemoteException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getId() {
        return this.id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public void ready(String username) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void unready(String username) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void move(int userid, int x, int y, int crouch) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void interact(int objectid, int state) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public boolean addPlayer(String username) {
        if (username != null && !username.isEmpty()) {
            if (players.size() < max) {
                IClient client = RMIServer.getClientData(username);
                if (client != null) {
                    if (players.add(client)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void removePlayer(String username) throws RemoteException {
        if (username != null && !username.isEmpty()) {
            IClient client = RMIServer.getClientData(username);
            if (client != null) {
                if (players.remove(client)) {
                    updatePlayerList();
                }

                if (players.isEmpty()) {
                    // Kill the lobby
                }
            }
        }
    }

    @Override
    public void updatePlayerList() {
        try {
            List<String> playerz = new ArrayList<>();

            for (IClient client : players) {
                playerz.add(client.getName());
            }

            for (IClient client : players) {
                client.updatePlayerList(playerz);
            }
        } catch (RemoteException ex) {
        }
    }

    @Override
    public IChat getChat() throws RemoteException {
        return this.chat;
    }
}
