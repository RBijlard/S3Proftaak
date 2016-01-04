/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.util.ArrayList;
import java.util.List;
import s3proftaak.Shared.IMessage;

/**
 *
 * @author Roel
 */
public class InGameMessage {

    private List<IMessage> gameMessages;

    public InGameMessage() {
        this.gameMessages = new ArrayList<IMessage>();
    }

    public void addMessage(IMessage m) {
        if (this.gameMessages != null) {
            if (!this.gameMessages.contains(m)) {
                this.gameMessages.add(m);
            }
        }
    }
}
