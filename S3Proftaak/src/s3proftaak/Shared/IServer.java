/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Stan
 */
public interface IServer extends Remote{
    public ILobby createLobby(String username, String lobbyname) throws RemoteException;
    public ILobby joinLobby(String username, String lobbyname) throws RemoteException;
    public void leaveLobby(String username) throws RemoteException;
    public List<ILobby> getLobbies() throws RemoteException;
}
