/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Server;

import s3proftaak.fontys.BasicPublisher;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import s3proftaak.util.CustomException;
import s3proftaak.Shared.ILobby;
import s3proftaak.Shared.IMessage;
import s3proftaak.fontys.RemotePropertyListener;

/**
 *
 * @author S33D
 */
public class Lobby extends UnicastRemoteObject implements ILobby {

    private String level = "";
    private final List<Player> players = new ArrayList<>();
    private final String name;
    private String amountOfPlayers;
    private final int max;
    private final BasicPublisher publisher;
    private String currentHost;
    private boolean started;

    public Lobby(String lobbyname, int maxPlayers) throws RemoteException {
        this.name = lobbyname;
        this.max = maxPlayers;

        updateAmountOfPlayers();

        this.publisher = new BasicPublisher(new String[]{"Administrative", "Chat", "Level", "Ready", "Players", "X", "Y", "Host"});
    }

    private void updateAmountOfPlayers() {
        this.amountOfPlayers = this.players.size() + "/" + this.max;
    }

    @Override
    public void sendMessage(IMessage message) {
        publisher.inform(this, "Chat", null, message);
    }

    @Override
    public void updateLevel(String level) {
        this.level = level;
        publisher.inform(this, "Level", null, level);
    }

    @Override
    public void toggleReadyState(String username) {
        Player p = getPlayer(username);
        if (p != null) {
            p.toggleReady();
            publisher.inform(this, "Ready", username, p.isReady());
        }

        checkStartGame();
    }

    @Override
    public void updatePlayers() {
        updateAmountOfPlayers();

        if (!getNames().contains(currentHost)) {
            currentHost = null;
        }

        if (players.size() == 1) {
            currentHost = players.get(0).getName();
        }

        if (currentHost != null) {
            publisher.inform(this, "Host", null, currentHost);
        }

        if (!players.isEmpty()) {
            publisher.inform(this, "Players", null, getNames());
        }
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

    @Override
    public String getLevel() {
        return level;
    }

    @Override
    public String getAmountOfPlayers() {
        return amountOfPlayers;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addPlayer(String username) throws RemoteException, CustomException {

        if (username != null && !username.isEmpty()) {
            if (players.size() < max) {
                if (!getNames().contains(username)) {
                    if (players.add(new Player(username))) {
                        updatePlayers();
                    } else {
                        throw new CustomException("Failed to join this lobby.");
                    }
                } else {
                    throw new CustomException("Username is already in this lobby.");
                }
            } else {
                throw new CustomException("Game is full.");
            }
        } else {
            throw new CustomException("Username is empty.");
        }

    }

    @Override
    public void removePlayer(String username) throws RemoteException {
        if (username != null && !username.isEmpty()) {
            Player p = getPlayer(username);
            if (p != null) {
                if (players.remove(p)) {
                    updatePlayers();
                }

                if (players.isEmpty()) {
                    ServerAdministration.getInstance().removeLobby(this);
                }
            }
        }
    }

    public Player getPlayer(String username) {
        for (Player p : players) {
            if (p.getName().equals(username)) {
                return p;
            }
        }

        return null;
    }

    public void checkStartGame() {
        if (!hasStarted()) {
            if (level != null) {
                if (players.size() == max) {
                    boolean allReady = true;

                    for (Player p : players) {
                        if (!p.isReady()) {
                            allReady = false;
                        }
                    }

                    if (allReady) {
                        started = true;
                        publisher.inform(this, "Administrative", "StartGame", level);
                    }
                }
            }
        }
    }

    public boolean hasStarted() {
        return this.started;
    }

    private List<String> getNames() {
        List<String> names = new ArrayList<>();

        // Use a copied list to prevent a ConcurrentModificationException from happening.
        List<Player> tempPlayers = new ArrayList<>();
        tempPlayers.addAll(players);

        for (Player p : tempPlayers) {
            names.add(p.getName());
        }

        return names;
    }
}
