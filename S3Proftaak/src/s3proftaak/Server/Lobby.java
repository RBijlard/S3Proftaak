/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Server;

import fontys.BasicPublisher;
import fontys.RemotePropertyListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import s3proftaak.Shared.ILobby;
import s3proftaak.Shared.IMessage;

/**
 *
 * @author S33D
 */
public class Lobby extends UnicastRemoteObject implements ILobby {

    private final int id;
    private String level;
    private final List<String> players = new ArrayList<>();
    private String name;
    private String amountOfPlayers;
    private int max;
    private final BasicPublisher publisher;

    public Lobby(int id, String name, int maxPlayers) throws RemoteException {
        this.id = id;
        this.name = name;
        this.max = maxPlayers;
        amountOfPlayers = 0 + "/" + max;

        publisher = new BasicPublisher(new String[]{"Chat", "Level", "Ready", "Players", "X", "Y"});
    }

    @Override
    public void sendMessage(IMessage message) {
        publisher.inform(this, "Chat", null, message);
    }

    @Override
    public void updateLevel(String level) {
        publisher.inform(this, "Level", null, level);
    }

    @Override
    public void toggleReadyState(String username) {
        publisher.inform(this, "Ready", null, username);
    }

    @Override
    public void updatePlayers() {
        publisher.inform(this, "Players", null, players);
    }

    @Override
    public void updateX(String username, int x) {
        publisher.inform(this, "X", username, x);
    }

    @Override
    public void updateY(String username, int y) {
        publisher.inform(this, "Y", username, y);
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
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
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public boolean addPlayer(String username) {
        if (username != null && !username.isEmpty()) {
            if (players.size() < max) {
                if (players.add(username)) {
                    updatePlayers();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void removePlayer(String username) throws RemoteException {
        if (username != null && !username.isEmpty()) {
            if (players.remove(username)) {
                updatePlayers();
            }

            if (players.isEmpty()) {
                // Kill the lobby
            }
        }
    }
}
