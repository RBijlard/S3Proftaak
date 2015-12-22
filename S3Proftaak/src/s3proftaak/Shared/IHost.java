/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

import java.rmi.RemoteException;
import s3proftaak.fontys.RemotePublisher;

/**
 *
 * @author Berry-PC
 */
public interface IHost extends RemotePublisher {

    public void sendMessage(IMessage message) throws RemoteException;

    public void askForMove(float vX, float vY, int id, boolean isEntering) throws RemoteException;
}
