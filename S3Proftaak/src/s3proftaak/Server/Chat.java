/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Server;

import fontys.BasicPublisher;
import fontys.RemotePropertyListener;
import fontys.RemotePublisher;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import s3proftaak.Shared.IChat;
import s3proftaak.Shared.IMessage;

/**
 *
 * @author Stan
 */
public class Chat extends UnicastRemoteObject implements IChat, RemotePublisher {
    
    private final BasicPublisher publisher;
    
    public Chat() throws RemoteException {
        publisher = new BasicPublisher(new String[]{"Chat"});
    }
    
    @Override
    public void sendMessage(IMessage message){
        publisher.inform(this, "Chat", null, message);
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }
    
}
