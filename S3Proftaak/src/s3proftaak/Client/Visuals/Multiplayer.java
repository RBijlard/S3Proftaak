/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s3proftaak.Client.RMIClient;
import s3proftaak.Main;
import static s3proftaak.Main.changeScreen;
import s3proftaak.Server.Lobby;
import s3proftaak.Shared.ILobby;

/**
 *
 * @author Stan
 */
public class Multiplayer extends BasicScene {
    
    @FXML TableView tableLobbies;
    @FXML Button btnBack;
    @FXML Button btnCreate;
    @FXML Button btnJoin;
    @FXML Button btnRefresh;
    
    public Multiplayer(){
        TableColumn firstNameCol = new TableColumn("Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstNameCol.setMinWidth(175);

        TableColumn lastNameCol = new TableColumn("Level");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("level"));
        lastNameCol.setMinWidth(175);

        TableColumn emailCol = new TableColumn("Players");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("amountOfPlayers"));
        
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (tableLobbies != null){
                    tableLobbies.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
                    btnRefreshClick(null);
                }
            }
        });
    }
    
    public void btnRefreshClick(Event e) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                try {
                    tableLobbies.setItems(FXCollections.observableArrayList(RMIClient.getServerAdministration().getLobbies()));
                } catch (RemoteException ex) {
                    Logger.getLogger(Multiplayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void btnCreateClick(Event e) {
        
    }
    
    public void btnJoinClick(Event e) {
        if (tableLobbies.getSelectionModel() != null){
            System.out.println(tableLobbies.getSelectionModel().getSelectedItem());
        }
    }
    
    public void btnBackClick(Event e) {
        changeScreen(Main.Screens.Menu);
    }
}
