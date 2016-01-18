/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

import s3proftaak.Shared.Wrappers.PlatformPosition;
import java.rmi.Remote;
import java.rmi.RemoteException;
import s3proftaak.Shared.Wrappers.MoveableBlockPosition;
import s3proftaak.Shared.Wrappers.PlayerPosition;
import s3proftaak.fontys.RemotePublisher;

/**
 *
 * @author Stan
 */
public interface IHost extends Remote, RemotePublisher {

    public void sendMessage(IMessage message) throws RemoteException;
//
//    public void updatePlayers() throws RemoteException;

    public void updatePlayer(String username, PlayerPosition pp) throws RemoteException;

    public void updateObject(int id, boolean state) throws RemoteException;

    public void updateMoveableObject(int id, MoveableBlockPosition mb) throws RemoteException;
    
    public void updatePlatform(int id, PlatformPosition pp) throws RemoteException;
//
//    public void closedGame() throws RemoteException;
//
//    public void loadedGame(String username) throws RemoteException;
//
//    public void stopGame() throws RemoteException;
//
//    public void restartGame() throws RemoteException;
//
//    public String getCurrentHost() throws RemoteException;
//
//    public List<IPlayer> getPlayers() throws RemoteException;
//
//    public String getAmountOfPlayers() throws RemoteException;
}
