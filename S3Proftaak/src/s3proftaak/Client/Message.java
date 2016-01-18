/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import s3proftaak.Shared.IMessage;

/**
 *
 * @author Stan
 */
public class Message implements IMessage {
    private final String message;
    private final String sender;
    private final long time = System.currentTimeMillis() + 5000;

    public Message(String sender, String message){
        this.sender = sender;
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getSender() {
        return sender;
    }
    
    @Override
    public long getTime(){
        return time;
    }
    
    @Override
    public String toString(){
        return this.sender + ": " + this.message;
    }
}
