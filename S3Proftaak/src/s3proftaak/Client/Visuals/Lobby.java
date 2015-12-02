/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import s3proftaak.Client.ChatController;
import s3proftaak.Shared.IMessage;
import s3proftaak.Client.Message;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;
import s3proftaak.Client.RMIClient;

/**
 *
 * @author Stan
 */
public final class Lobby extends BasicScene {

    @FXML
    Label lblLobbyName;
    @FXML
    ListView chatList;
    @FXML
    ListView playerList;
    @FXML
    TextField chatText;
    @FXML
    Button btnSend;
    @FXML
    Button btnKick;
    @FXML
    Button btnLeave;
    @FXML
    Button btnReady;
    @FXML
    ComboBox cbLevel;

    private ChatController chatController;
    private boolean isHost;

    public Lobby() {
        createChatController();

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                try {
                    if (lblLobbyName != null) {
                        lblLobbyName.setText(ClientAdministration.getInstance().getCurrentLobby().getName());
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        try {
            ClientAdministration.getInstance().getCurrentLobby().updatePlayers();
        } catch (RemoteException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void btnSendClick(Event e) {
        if (!chatText.getText().isEmpty()) {
            if (chatController != null) {
                chatController.sendMessage(new Message(ClientAdministration.getInstance().getAccount().getUsername(), chatText.getText()));
                chatText.setText("");
            }
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
        chatController.leaveLobby();

        changeScreen(ClientAdministration.Screens.Multiplayer);
    }

    public void displayMessage(IMessage message) {
        Platform.runLater(() -> {
            if (chatList != null) {
                chatList.getItems().add(message.toString());
            }
        });
    }

    private void createChatController() {
        try {
            chatController = new ChatController(this);
        } catch (RemoteException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
            displayMessage(new Message("ERROR", "Chat failed to initialize."));
        }
    }

    public void updatePlayerList(List<String> players) {
        Platform.runLater(() -> {
            if (playerList != null && players != null) {
                playerList.setItems(FXCollections.observableArrayList(players));
            }
        });
    }

    public void cbLevelClick() {
        if (cbLevel != null && cbLevel.getSelectionModel().getSelectedItem() != null) {
            if (isHost) {
                String level = cbLevel.getSelectionModel().getSelectedItem().toString();
                try {
                    ClientAdministration.getInstance().getCurrentLobby().updateLevel(level);
                } catch (RemoteException ex) {
                    Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void comboboxSet(String s) {
        Platform.runLater(() -> {
            if (cbLevel != null) {
                ArrayList<String> list = new ArrayList<>();
                list.add(s);
                if (!isHost) {
                    System.out.println("Set selection model : " + s);
                    this.cbLevel.getSelectionModel().select(s);
                    this.cbLevel.setItems(FXCollections.observableArrayList(list));
                }
            }
        });
    }

    public void setIsHost(boolean b) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (cbLevel != null) {
                    if (b != isHost) {
                        isHost = b;
                        
                        if (isHost) {
                            ArrayList levels = new ArrayList<>();

                            for (File f : new File(getClass().getResource("/Resources/Levels/").getPath().replaceAll("%20", " ")).listFiles()) {
                                if (f.getName().endsWith(".tmx")) {
                                    levels.add(f.getName());
                                }
                            }

                            if (!levels.isEmpty()) {
                                cbLevel.setItems(FXCollections.observableArrayList(levels));
                                cbLevel.setValue(levels.get(0));
                            }
                        } else {
                            try {
                                List<String> lvl = new ArrayList<>();
                                lvl.add(ClientAdministration.getInstance().getCurrentLobby().getLevel());
                                cbLevel.setItems(FXCollections.observableArrayList(lvl));
                            } catch (RemoteException ex) {
                                Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        });
    }

    public ChatController getChatController() {
        return this.chatController;
    }
}
