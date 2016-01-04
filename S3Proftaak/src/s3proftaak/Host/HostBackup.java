/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Host;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import s3proftaak.Server.Player;
import s3proftaak.Server.ServerAdministration;
import s3proftaak.Shared.IHostBackup;
import s3proftaak.Shared.IMessage;
import s3proftaak.Shared.IPlayer;
import s3proftaak.Shared.PlayerPosition;
import s3proftaak.fontys.BasicPublisher;
import s3proftaak.fontys.RemotePropertyListener;
import s3proftaak.util.CustomException;

/**
 *
 * @author Berry-PC
 */
public class HostBackup extends UnicastRemoteObject implements IHostBackup {

    private static Remote getInstance() {
        return instance;
    }

    private final List<Player> players = new ArrayList<>();
    private int max;
    private final BasicPublisher publisher;
    private String level = "";
    private static HostBackup instance;
    private String currentHost;
    private LobbyState state = LobbyState.Waiting;

    private enum LobbyState {

        Waiting, // Waiting for players to join. (In lobby)
        Loading, // Waiting for players to load. (In game)
        Playing; // Playing.
    }

    public HostBackup(String ipAddress) throws RemoteException {
        instance = (HostBackup)this;
        try {
            //new ServerAdministration();
            LocateRegistry.createRegistry(1098).rebind("HostGame", HostBackup.getInstance());
            System.out.println("Host is online at: '" + ipAddress + "'.");
            System.out.println("Using port: 1098");

        } catch (RemoteException ex) {
            System.out.println("Server is offline. \n" + ex);
        }
        
        this.max = 1;
        this.publisher = new BasicPublisher(new String[]{"Administrative", "Chat", "Level", "Players", "Rect", "Host", "Objects"});
    }

    @Override
    public void sendMessage(IMessage message) {
        publisher.inform(this, "Chat", null, message);
    }

    @Override
    public void loadedGame(String username) throws RemoteException {
        if (hasStarted()) {
            Player p = getPlayer(username);
            if (p != null) {
                p.toggleReady();
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
    public void updatePlayer(String username, PlayerPosition pp) {
        //if (hasStarted()) {
            publisher.inform(this, "Rect", username, pp);
        //}
    }

    @Override
    public void updateObject(int id, boolean state) throws RemoteException {
        //if (hasStarted()) {
            publisher.inform(this, "Objects", id, state);
        //}
    }

    @Override
    public void updateMoveableObject(int id, int dx) throws RemoteException {
        //if (hasStarted()) {
            publisher.inform(this, "Objects", id, dx);
        //}
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
    public List<IPlayer> getPlayers() {
        List<IPlayer> playerz = new ArrayList<>();
        playerz.addAll(players);
        return playerz;
    }

    @Override
    public void addListener(String username, RemotePropertyListener listener, String property) throws RemoteException {
        System.out.println("username added to listeners:  " + username + ", to property:  " + property);
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

    public Player getPlayer(String username) {
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
                    boolean allReady = true;

                    for (Player p : players) {
                        if (!p.isReady()) {
                            allReady = false;
                        }
                    }

                    if (allReady) {
                        for (Player p : players) {
                            p.setReady(false);
                        }

                        state = LobbyState.Loading;
                        publisher.inform(this, "Administrative", "StartGame", level);
                    }
                }
            }
        }
    }

    private void checkReallyStartGame() {
        if (state == LobbyState.Loading) {
            boolean allReady = true;

            for (Player p : players) {
                if (!p.isReady()) {
                    allReady = false;
                }
            }

            if (allReady) {
                state = LobbyState.Playing;
                publisher.inform(this, "Administrative", "ReallyStartGame", null);
            }
        }
    }

    @Override
    public void stopGame() {
        if (hasStarted()) {
            for (Player p : players) {
                p.setReady(false);
            }

            state = LobbyState.Waiting;
            publisher.inform(this, "Administrative", "StopGame", null);
        }
    }

    @Override
    public void restartGame() {
        if (state == LobbyState.Playing) {
            for (Player p : players) {
                p.setReady(false);
            }

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
}