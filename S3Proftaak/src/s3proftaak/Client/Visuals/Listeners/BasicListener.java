/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals.Listeners;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import s3proftaak.fontys.RemotePropertyListener;

/**
 *
 * @author Stan
 */
public abstract class BasicListener extends UnicastRemoteObject implements Serializable, RemotePropertyListener {
    
    public BasicListener() throws RemoteException {};
    
    public void startListening(){}
    public void stopListening(){}
}
