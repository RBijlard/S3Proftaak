
package s3proftaak.Client.Visuals;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;
import s3proftaak.Client.Message;
import s3proftaak.Client.Visuals.Listeners.LobbyListener;
import s3proftaak.Client.Visuals.Lobby_Utils.CheckBoxTableCell;
import s3proftaak.Client.Visuals.Lobby_Utils.LocalPlayer;
import s3proftaak.Shared.IMessage;
import s3proftaak.Shared.IPlayer;

/**
 *
 * @author Stan
 */
public class Lobby extends BasicScene {

    @FXML
    Label lblLobbyName;
    @FXML
    ListView chatList;
    @FXML
    TableView playerList;
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

    private boolean isHost;
    private String currentlySelected;

    public Lobby() {
        try {
            this.setListener(new LobbyListener(this));
            this.getListener().startListening();
        } catch (RemoteException ex) {
            System.out.println(ex);
            ClientAdministration.getInstance().connectionLost();
        }

        TableColumn readyCol = new TableColumn("Ready");
        readyCol.setCellValueFactory(new PropertyValueFactory<>("ready"));

        // Adds the checkbox
        readyCol.setCellFactory(new Callback<TableColumn<IPlayer, Boolean>, TableCell<IPlayer, Boolean>>() {
            @Override
            public TableCell<IPlayer, Boolean> call(TableColumn<IPlayer, Boolean> p) {
                return new CheckBoxTableCell<>();
            }
        });

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (playerList != null) {
                    nameCol.setMinWidth(playerList.getWidth() - readyCol.getWidth());
                    playerList.getColumns().addAll(readyCol, nameCol);
                }

                try {
                    if (lblLobbyName != null) {
                        lblLobbyName.setText(ClientAdministration.getInstance().getCurrentLobby().getName());
                    }

                    if (cbLevel != null) {
                        try {
                            List<String> lvl = new ArrayList<>();
                            lvl.add(ClientAdministration.getInstance().getCurrentLobby().getLevel());
                            cbLevel.setItems(FXCollections.observableArrayList(lvl));
                            cbLevel.setValue(lvl.get(0));
                            currentlySelected = lvl.get(0);
                        } catch (RemoteException ex) {
                            System.out.println(ex);
                            ClientAdministration.getInstance().connectionLost();
                        }
                    }

                    if (playerList != null) {
                        updatePlayerList(ClientAdministration.getInstance().getCurrentLobby().getPlayers());
                    }

                    setIsHost(ClientAdministration.getInstance().getCurrentLobby().getCurrentHost() != null && ClientAdministration.getInstance().getCurrentLobby().getCurrentHost().equals(ClientAdministration.getInstance().getAccount().getUsername()));

                    if (chatText != null) {
                        chatText.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent ke) {
                                if (ke.getCode().equals(KeyCode.ENTER)) {
                                    btnSendClick(null);
                                }
                            }
                        });
                    }
                } catch (RemoteException ex) {
                    System.out.println(ex);
                    ClientAdministration.getInstance().connectionLost();
                }
            }
        });
    }

    public void btnSendClick(Event e) {
        if (!chatText.getText().isEmpty()) {
            try {
                ClientAdministration.getInstance().getCurrentLobby().sendMessage(new Message(ClientAdministration.getInstance().getAccount().getUsername(), chatText.getText()));
                chatText.setText("");
            } catch (RemoteException ex) {
                System.out.println(ex);
                ClientAdministration.getInstance().connectionLost();
            }
        }
    }

    public void btnKickClick(Event e) {
        if (playerList != null) {
            if (isHost) {
                if (playerList.getSelectionModel() != null && playerList.getSelectionModel().getSelectedItem() != null) {
                    try {
                        ClientAdministration.getInstance().getCurrentLobby().kickPlayer(((LocalPlayer) playerList.getSelectionModel().getSelectedItem()).getName());
                    } catch (RemoteException ex) {
                        System.out.println(ex);
                        ClientAdministration.getInstance().connectionLost();
                    }
                }
            }
        }
    }

    public void btnReadyClick(Event e) {
        try {
            ClientAdministration.getInstance().getCurrentLobby().toggleReadyState(ClientAdministration.getInstance().getAccount().getUsername());
        } catch (RemoteException ex) {
            System.out.println(ex);
            ClientAdministration.getInstance().connectionLost();
        }
    }

    public void btnLeaveClick(Event e) {
        if (this.getListener() != null) {
            this.getListener().stopListening();
        }

        changeScreen(ClientAdministration.Screens.Multiplayer);
    }

    public void displayMessage(IMessage message) {
        Platform.runLater(() -> {
            if (chatList != null) {
                chatList.getItems().add(message.toString());
            }
        });
    }

    public void updatePlayerList(List<IPlayer> players) {
        Platform.runLater(() -> {
            if (playerList != null && players != null) {
                // We have to clear the items first or it doesn't update the ready state.
                playerList.getItems().clear();

                List<LocalPlayer> tempPlayers = new ArrayList<>();
                for (IPlayer p : players) {
                    try {
                        tempPlayers.add(new LocalPlayer(p.getName(), p.isReady()));
                    } catch (RemoteException ex) {
                        System.out.println(ex);
                        ClientAdministration.getInstance().connectionLost();
                    }
                }
                playerList.setItems(FXCollections.observableArrayList(tempPlayers));
            }
        });
    }

    public void cbLevelClick() {
        if (cbLevel != null && cbLevel.getSelectionModel().getSelectedItem() != null) {
            if (isHost) {
                String level = cbLevel.getSelectionModel().getSelectedItem().toString();
                try {
                    int amount = 1;

                    if (!level.isEmpty()) {
                        amount = Integer.parseInt(level.substring(level.indexOf('(') + 1, level.indexOf(')')));
                    }

                    if (amount >= ClientAdministration.getInstance().getCurrentLobby().getPlayers().size()) {
                        ClientAdministration.getInstance().getCurrentLobby().updateLevel(level);
                        currentlySelected = level;
                    } else {
                        cbLevel.getSelectionModel().select(currentlySelected);
                        JOptionPane.showMessageDialog(null, "Too many players in the lobby for this map. (Kick some?)", "Failed.", 1);
                    }
                } catch (RemoteException ex) {
                    System.out.println(ex);
                    ClientAdministration.getInstance().connectionLost();
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
                    this.cbLevel.setItems(FXCollections.observableArrayList(list));
                    this.cbLevel.setValue(list.get(0));
                }
            }
        });
    }

    public void setIsHost(boolean b) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (b) {
                    btnKick.setDisable(false);
                } else {
                    btnKick.setDisable(true);
                }

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
                                System.out.println(ex);
                                ClientAdministration.getInstance().connectionLost();
                            }
                        }
                    }
                }
            }
        });
    }

    public TableView getPlayerList() {
        return this.playerList;
    }
}
