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
    
    private final String name, ipAddress;
    private boolean ready;

    public Player(String name, String ipAddress) throws RemoteException {
        this.name = name;
        this.ipAddress = ipAddress;
    }

    @Override
    public String getIpAddress() {
        return ipAddress;
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
}
