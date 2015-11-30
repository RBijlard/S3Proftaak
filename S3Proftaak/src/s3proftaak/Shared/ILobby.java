/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

import fontys.RemotePropertyListener;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Stan
 */
public interface ILobby extends Remote {

    public void sendMessage(IMessage message) throws RemoteException;

    public void updateLevel(String level) throws RemoteException;

    public void toggleReadyState(String username) throws RemoteException;

    public void updatePlayers() throws RemoteException;

    public void updateX(String username, int x) throws RemoteException;

    public void updateY(String username, int y) throws RemoteException;

    public String getName() throws RemoteException;

    public boolean addPlayer(String username) throws RemoteException;

    public void removePlayer(String username) throws RemoteException;

    public void addListener(RemotePropertyListener listener, String property) throws RemoteException;

    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException;
}
