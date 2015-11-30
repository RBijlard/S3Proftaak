/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import s3proftaak.Client.ChatController;
import s3proftaak.Shared.IMessage;
import s3proftaak.Client.Message;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;

/**
 *
 * @author Stan
 */
public final class Lobby extends BasicScene {
    
    @FXML Label lblLobbyName;
    @FXML ListView chatList;
    @FXML ListView playerList;
    @FXML TextField chatText;
    @FXML Button btnSend;
    @FXML Button btnKick;
    @FXML Button btnLeave;
    @FXML Button btnReady;
    
    private ChatController chatController;
    
    public Lobby(){
        createChatController();
        
        try {
            ClientAdministration.getInstance().getCurrentLobby().updatePlayers();
        } catch (RemoteException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void btnSendClick(Event e) {
        if (!chatText.getText().isEmpty()){
            chatController.sendMessage(new Message(ClientAdministration.getInstance().getAccount().getUsername(), chatText.getText()));
            chatText.setText("");
        }
    }
    
    public void btnKickClick(Event e) {
        
    }
    
    public void btnReadyClick(Event e) {
        try {
            ClientAdministration.getInstance().getCurrentLobby().toggleReadyState(ClientAdministration.getInstance().getAccount().getUsername());
        } catch (RemoteException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void btnLeaveClick(Event e) {
        try {
            chatController.removeListener(chatController, "Chat");
        } catch (RemoteException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ClientAdministration.getInstance().getCurrentLobby().removePlayer(ClientAdministration.getInstance().getAccount().getUsername());
        } catch (RemoteException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        changeScreen(ClientAdministration.Screens.Menu);
    }
    
    public void displayMessage(IMessage message) {
        Platform.runLater(() -> {
            if (chatList != null){
                chatList.getItems().add(message.toString());
            }
        });
    }
    
    private void createChatController(){
        try {
            chatController = new ChatController(this);
        } catch (RemoteException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
            displayMessage(new Message("ERROR", "Chat failed to initialize."));
        }
    }
    
    public void updatePlayerList(List<String> players){
        Platform.runLater(() -> {
            if (playerList != null){
                playerList.setItems(FXCollections.observableArrayList(players));
            }
        });
    }
}
