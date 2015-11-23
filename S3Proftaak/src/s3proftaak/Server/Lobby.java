/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import s3proftaak.Client.Account;
import s3proftaak.Shared.ILobby;
import s3proftaak.Shared.Message;

/**
 *
 * @author S33D
 */
public class Lobby implements ILobby {
    private final int id;
    private String level;
    private ArrayList<Account> players;
    private String name;
    private String amountOfPlayers;
    private int max;

    public Lobby(int id, String name, int maxPlayers) {
        this.id = id;
        this.name = name;
        this.max = maxPlayers;
        amountOfPlayers = 0 + "/" + max;
    }
    
    public int getId(){
        return this.id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public ArrayList<Account> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Account> players) {
        this.players = players;
    }

    @Override
    public void sendMessage(String username, Message message) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}