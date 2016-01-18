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
import s3proftaak.Shared.IHost;
import s3proftaak.Shared.ILobby;
import s3proftaak.Shared.IMessage;
import s3proftaak.Shared.IPlayer;
import s3proftaak.fontys.BasicPublisher;
import s3proftaak.fontys.RemotePropertyListener;
import s3proftaak.util.CustomException;
import s3proftaak.util.ICare;

/**
 *
 * @author S33D
 */
public class Lobby extends UnicastRemoteObject implements ILobby, ICare {

    private final List<Player> players = new ArrayList<>();
    private final String name;
    private int max;
    private final BasicPublisher publisher;
    private String level = "";
    private String currentHost;
    private LobbyState state = LobbyState.Waiting;


    public Lobby(String lobbyname) throws RemoteException {
        this.name = lobbyname;
        this.max = 1;
        this.publisher = new BasicPublisher(this, new String[]{"Administrative", "Chat", "Level", "Players", "Rect", "Host", "Objects"});
    }

    @Override
    public void playerLostConnection(String playerName) {
        if (players.contains(getPlayer(playerName))) {
            kickPlayer(playerName);
        }
    }

    @Override
    public void sendMessage(IMessage message) {
        publisher.inform(this, "Chat", null, message);
    }

    @Override
    public void updateLevel(String level) {
        if (!hasStarted()) {
            int amount = 1;

            if (!level.isEmpty()) {
                amount = Integer.parseInt(level.substring(level.indexOf('(') + 1, level.indexOf(')')));
            }

            max = amount;

            publisher.inform(this, "Level", null, this.level = level);
            ServerAdministration.getInstance().informLobbyListMembers();
        }
    }

    @Override
    public void toggleReadyState(String username) {
        if (!hasStarted()) {
            Player p = getPlayer(username);
            if (p != null) {
                p.toggleReady();
                updatePlayers();
                checkStartGame();
            }
        }
    }

    @Override
    public void loadedGame(String username) throws RemoteException {
        if (hasStarted()) {
            Player p = getPlayer(username);
            if (p != null) {
                p.setReady(true);
                checkReallyStartGame();
            }
        }
    }

    @Override
    public void updatePlayers() {
        if (!hasStarted()) {
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
                publisher.inform(this, "Players", null, players);
            }

            ServerAdministration.getInstance().informLobbyListMembers();
        }
    }

    @Override
    public void closedGame() {
        stopGame();
    }

    @Override
    public String getAmountOfPlayers() {
        return this.players.size() + "/" + this.max;
    }

    @Override
    public String getLevel() {
        return level;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<IPlayer> getPlayers() {
        List<IPlayer> playerz = new ArrayList<>();
        playerz.addAll(players);
        return playerz;
    }

    @Override
    public void kickPlayer(String username) {
        Player p = getPlayer(username);
        if (p != null) {
            this.removePlayer(username);
            
            if (!hasStarted()){
                publisher.inform(this, "Administrative", "Kick", username);
            }
            
            updatePlayers();
        }

        if (hasStarted()) {
            stopGame();
        }
    }

    @Override
    public void addPlayer(String username, String ipAddress) throws RemoteException, CustomException {

        if (!hasStarted()) {
            if (username != null && !username.isEmpty()) {
                if (players.size() < max) {
                    if (!getNames().contains(username)) {
                        Player tempPlayer = new Player(username, ipAddress);
                        if (players.add(tempPlayer)) {
                            tempPlayer.setCurrentLobby(this);
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
        } else {
            throw new CustomException("Game has already begun.");
        }

    }

    @Override
    public void removePlayer(String username) {
        if (username != null && !username.isEmpty()) {
            Player tempPlayer = getPlayer(username);
            if (tempPlayer != null) {
                if (players.remove(tempPlayer)) {
                    tempPlayer.setCurrentLobby(null);
                    updatePlayers();
                }

                if (players.isEmpty()) {
                    ServerAdministration.getInstance().removeLobby(this);
                    publisher.closeCachedThreadPool();
                }
            }
        }
    }

    @Override
    public void addListener(String username, RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(username, listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }

    public boolean hasStarted() {
        return state != LobbyState.Waiting;
    }

    @Override
    public String getCurrentHost() {
        return this.currentHost;
    }

    private Player getPlayer(String username) {
        for (Player p : players) {
            if (p.getName().equals(username)) {
                return p;
            }
        }

        return null;
    }

    private void checkStartGame() {
        if (!hasStarted()) {
            if (level != null) {
                if (players.size() == max) {
                    if (allReady()) {
                        state = LobbyState.Loading;
                        publisher.inform(this, "Administrative", "ipAddress", this.getPlayer(this.currentHost).getIpAddress());
                    }
                }
            }
        }
    }

    private boolean allReady() {
        boolean allReady = true;

        for (Player p : players) {
            if (!p.isReady()) {
                allReady = false;
            }
        }

        if (allReady) {
            // Everyone is ready, toggle it since we need the ready state for more stuff.
            for (Player p : players) {
                p.setReady(false);
            }
        }

        return allReady;
    }

    private void checkHostReceived() {
        if (state == LobbyState.Loading) {
            if (allReady()) {
                publisher.inform(this, "Administrative", "StartGame", level);
            }
        }
    }

    private void checkReallyStartGame() {
        if (state == LobbyState.Loading) {
            if (allReady()) {
                state = LobbyState.Playing;
                publisher.inform(this, "Administrative", "ReallyStartGame", null);
            }
        }
    }

    @Override
    public void stopGame() {
        if (hasStarted()) {
            state = LobbyState.Waiting;
            publisher.inform(this, "Administrative", "StopGame", null);
        }
    }

    @Override
    public void restartGame() {
        if (state == LobbyState.Playing) {
            state = LobbyState.Loading;
            publisher.inform(this, "Administrative", "RestartGame", null);
        }
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

    @Override
    public void bindHost(IHost hb1, String ipAddress) {
        this.publisher.inform(this, "Administrative", "ipAddressForNotHost", hb1);
    }

    @Override
    public void receivedHost(String username) throws RemoteException {
        if (state == LobbyState.Loading) {
            Player p = getPlayer(username);
            if (p != null) {
                p.setReady(true);
                checkHostReceived();
            }
        }
    }

    private enum LobbyState {

        Waiting, // Waiting for players to join. (In lobby)
        Loading, // Waiting for players to load. (In game)
        Playing
    }
}
