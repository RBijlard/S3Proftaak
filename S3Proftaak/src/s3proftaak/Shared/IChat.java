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
public interface IChat extends Remote {
    public void sendMessage(IMessage message) throws RemoteException;
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException;
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException;
}
