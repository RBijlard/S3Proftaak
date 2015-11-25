/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Stan
 */
public interface ILobby extends Remote {
    public void ready(String username) throws RemoteException;
    public void unready(String username) throws RemoteException;
    public void move(int userid, int x, int y, int crouch) throws RemoteException;
    public void interact(int objectid, int state) throws RemoteException;
    public String getName() throws RemoteException;
    public String getLevel() throws RemoteException;
    public boolean addPlayer(String username) throws RemoteException;
    public void removePlayer(String username) throws RemoteException;
    public IChat getChat() throws RemoteException;
}
