/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import java.rmi.RemoteException;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;
import s3proftaak.Client.RMIClient;
import s3proftaak.Client.Visuals.Listeners.MultiplayerListener;
import s3proftaak.Shared.ILobby;
import s3proftaak.util.CustomException;

/**
 *
 * @author Stan
 */
public class Multiplayer extends BasicScene {

    @FXML
    TableView tableLobbies;
    @FXML
    Button btnBack;
    @FXML
    Button btnCreate;
    @FXML
    Button btnJoin;

    public Multiplayer() {

        try {
            this.setListener(new MultiplayerListener(this));
            this.getListener().startListening();
        } catch (RemoteException ex) {
            System.out.println(ex);
            ClientAdministration.getInstance().connectionLost();
        }

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMinWidth(175);

        TableColumn level = new TableColumn("Level");
        level.setCellValueFactory(new PropertyValueFactory<>("level"));
        level.setMinWidth(175);

        TableColumn players = new TableColumn("Players");
        players.setCellValueFactory(new PropertyValueFactory<>("amountOfPlayers"));

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (tableLobbies != null) {
                    tableLobbies.getColumns().addAll(name, level, players);
                    try {
                        tableLobbies.setItems(FXCollections.observableArrayList(RMIClient.getInstance().getServerAdministration().getLobbies()));
                    } catch (RemoteException ex) {
                        System.out.println(ex);
                        ClientAdministration.getInstance().connectionLost();
                    }

                    tableLobbies.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                                btnJoinClick(null);
                            }
                        }
                    });
                }
            }
        });
    }

    public void btnCreateClick(Event e) {
        this.getListener().stopListening();

        changeScreen(ClientAdministration.Screens.CreateLobby);
    }

    public void btnJoinClick(Event e) {
        this.getListener().stopListening();

        if (tableLobbies.getSelectionModel() != null && tableLobbies.getSelectionModel().getSelectedItem() != null) {
            try {
                ((ILobby) tableLobbies.getSelectionModel().getSelectedItem()).addPlayer(ClientAdministration.getInstance().getAccount().getUsername(), ClientAdministration.getInstance().getAccount().getIp());
                ClientAdministration.getInstance().setCurrentLobby((ILobby) tableLobbies.getSelectionModel().getSelectedItem());
                changeScreen(ClientAdministration.Screens.Lobby);
            } catch (CustomException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Failed.", 1);
            } catch (RemoteException ex) {
                System.out.println(ex);
                ClientAdministration.getInstance().connectionLost();
            }
        }
    }

    public void btnBackClick(Event e) {
        this.getListener().stopListening();

        changeScreen(ClientAdministration.Screens.Menu);
    }

    public void updateList(List<ILobby> lobbies) {
        // The line below prevents: 'java.lang.IllegalStateException: Toolkit not initialized'
        //new JFXPanel();

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (tableLobbies != null) {
                    tableLobbies.getItems().clear();
                    tableLobbies.setItems(FXCollections.observableArrayList(lobbies));
                }
            }
        });
    }
}
