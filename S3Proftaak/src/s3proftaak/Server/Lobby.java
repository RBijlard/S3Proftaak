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
    private final List<Player> players = new ArrayList<>();
    private final String name;
    private String amountOfPlayers;
    private final int max;
    private final BasicPublisher publisher;

    public Lobby(int id, String name, int maxPlayers) throws RemoteException {
        this.id = id;
        this.name = name;
        this.max = maxPlayers;
        updateAmountOfPlayers();

        this.publisher = new BasicPublisher(new String[]{"Administrative", "Chat", "Level", "Ready", "Players", "X", "Y"});
    }
    
    private void updateAmountOfPlayers(){
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
        if (p != null){
            p.toggleReady();
            publisher.inform(this, "Ready", username, p.isReady());
        }
        
        checkStartGame();
    }

    @Override
    public void updatePlayers() {
        updateAmountOfPlayers();
        
        List<String> names = new ArrayList<>();
        
        for (Player p : players){
            names.add(p.getName());
        }
        if(players.size() == 1){
            publisher.inform(this, "Players", "ISHOST", names);
        }
        
        publisher.inform(this, "Players", null, names);
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

    @Override
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String getAmountOfPlayers() {
        return amountOfPlayers;
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public boolean addPlayer(String username) {
        if (username != null && !username.isEmpty()) {
            if (players.size() < max) {
                if (players.add(new Player(username))) {
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
            Player p = getPlayer(username);
            if (p != null) {
                if (players.remove(p)) {
                    updatePlayers();
                }

                if (players.isEmpty()) {
                    // Kill the lobby
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
        if (players.size() == max){
            boolean allReady = true;
            
            for (Player p : players){
                if (!p.isReady()){
                    allReady = false;
                }
            }
            
            if (allReady){
                publisher.inform(this, "Administrative", "StartGame", level);
            }
        }
    }
}
