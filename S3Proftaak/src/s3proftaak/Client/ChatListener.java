/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.List;
import s3proftaak.Shared.IMessage;
import s3proftaak.fontys.RemotePropertyListener;

/**
 *
 * @author Stan
 */
public class ChatListener implements RemotePropertyListener {

    private final ChatController chatController;

    public ChatListener(ChatController chatController) {
        this.chatController = chatController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        switch (evt.getPropertyName()) {
            case "Administrative":
                if (evt.getOldValue().toString().equals("StartGame")) {
                    ClientAdministration.getInstance().startGame(new Game("De Game", 1, evt.getNewValue().toString()));
                }
                break;

            case "Chat":
                chatController.getLobby().displayMessage((IMessage) evt.getNewValue());
                break;

            case "Players":
                if (evt.getOldValue() != null && evt.getOldValue().toString().equals("ISHOST")) {
                    System.out.println("Setting is host : ");
                    chatController.getLobby().setIsHost(true);
                } else {
                    chatController.getLobby().updatePlayerList((List<String>) evt.getNewValue());
                }
                break;

            case "Ready":
                // Old = username, New = State
                break;

            case "Level":
                // 
                chatController.getLobby().comboboxSet(evt.getNewValue().toString());
                break;
        }
    }
}
