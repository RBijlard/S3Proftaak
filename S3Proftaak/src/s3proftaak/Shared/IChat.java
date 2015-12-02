/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import s3proftaak.fontys.RemotePropertyListener;

/**
 *
 * @author Stan
 */
public interface IChat extends Remote {
    public void sendMessage(IMessage message) throws RemoteException;
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException;
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException;
}
