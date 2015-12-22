/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import s3proftaak.Shared.IPlayer;

/**
 *
 * @author Stan
 */
public class Player extends UnicastRemoteObject implements IPlayer {
    
    private final String name;
    private boolean ready;
    private Lobby currentLobby;

    public Player(String name) throws RemoteException {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public boolean isReady() {
        return ready;
    }

    public void toggleReady() {
        this.ready = !ready;
    }
    
    public void setReady(boolean ready){
        this.ready = ready;
    }
    
    public Lobby getCurrentLobby(){
        return this.currentLobby;
    }
    
    public void setCurrentLobby(Lobby currentLobby){
        this.currentLobby = currentLobby;
    }
}
