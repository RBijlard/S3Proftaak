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
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.util.CustomException;
import s3proftaak.Shared.ILobby;
import s3proftaak.Shared.IMessage;
import s3proftaak.Shared.IPlayer;
import s3proftaak.Shared.PlayerPosition;
import s3proftaak.fontys.RemotePropertyListener;

/**
 *
 * @author S33D
 */
public class Lobby extends UnicastRemoteObject implements ILobby {

    private final List<Player> players = new ArrayList<>();
    private final String name;
    private final int max;
    private final BasicPublisher publisher;
    private String level = "";
    private String currentHost;
    private boolean started;
    
    public Lobby(String lobbyname, int maxPlayers) throws RemoteException {
        this.name = lobbyname;
        this.max = maxPlayers;
        this.publisher = new BasicPublisher(new String[]{"Administrative", "Chat", "Level", "Players", "Rect", "Host", "Objects"});
    }

    @Override
    public void sendMessage(IMessage message) {
        publisher.inform(this, "Chat", null, message);
    }

    @Override
    public void updateLevel(String level) {
        publisher.inform(this, "Level", null, this.level = level);
    }

    @Override
    public void toggleReadyState(String username) {
        Player p = getPlayer(username);
        if (p != null) {
            p.toggleReady();
            updatePlayers();
        }

        checkStartGame();
    }

    @Override
    public void updatePlayers() {
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
    }

    @Override
    public void updatePlayer(String username, PlayerPosition pp) {
        publisher.inform(this, "Rect", username, pp);
    }
    
    @Override
    public void updateObject(int id, boolean state) throws RemoteException{
        publisher.inform(this, "Objects", id, state);
    }
        
    @Override
    public void updateMoveableObject(int id, int x) throws RemoteException{
        publisher.inform(this, "Objects", id, x);
    }
    
    @Override
    public void closedGame(){
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
    public List<IPlayer> getPlayers(){
        List<IPlayer> playerz = new ArrayList<>();
        playerz.addAll(players);
        return playerz;
    }
    
    @Override
    public void kickPlayer(String username){
        Player p = getPlayer(username);
        if (p != null) {
            this.removePlayer(username);
            publisher.inform(this, "Administrative", "Kick", username);
            updatePlayers();
        }
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
    public void removePlayer(String username) {
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

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }

    public boolean hasStarted() {
        return this.started;
    }
    
    @Override
    public String getCurrentHost(){
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
    
    private void stopGame(){
        for (Player p : players){
            p.setReady(false);
        }
        
        publisher.inform(this, "Administrative", "StopGame", level);
        started = false;
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
