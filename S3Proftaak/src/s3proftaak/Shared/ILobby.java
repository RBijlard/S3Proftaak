/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

import s3proftaak.util.CustomException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import org.newdawn.slick.geom.Rectangle;
import s3proftaak.fontys.RemotePropertyListener;

/**
 *
 * @author Stan
 */
public interface ILobby extends Remote {

    public void sendMessage(IMessage message) throws RemoteException;

    public void updateLevel(String level) throws RemoteException;

    public void toggleReadyState(String username) throws RemoteException;

    public void updatePlayers() throws RemoteException;

    public void updatePlayer(String username, PlayerPosition pp) throws RemoteException;
    
    public void updateObject(int id, boolean state) throws RemoteException;
    
    public void updateMoveableObject(int id, float x) throws RemoteException;
    
    public void closedGame() throws RemoteException;
    
    public void kickPlayer(String username) throws RemoteException;

    public String getName() throws RemoteException;
    
    public String getCurrentHost() throws RemoteException;
    
    public String getLevel() throws RemoteException;
    
    public List<IPlayer> getPlayers() throws RemoteException;
    
    public String getAmountOfPlayers() throws RemoteException;

    public void addPlayer(String username) throws RemoteException, CustomException;

    public void removePlayer(String username) throws RemoteException;

    public void addListener(RemotePropertyListener listener, String property) throws RemoteException;

    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException;
}
