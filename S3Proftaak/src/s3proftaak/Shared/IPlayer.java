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
public interface IPlayer extends Remote {
    public String getName() throws RemoteException;
    public boolean isReady() throws RemoteException;
    public String getReady() throws RemoteException;
}
