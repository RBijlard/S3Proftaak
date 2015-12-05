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
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s3proftaak.Client.RMIClient;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;

/**
 *
 * @author Stan
 */
public class Highscores extends BasicScene {

    @FXML TableView tableHighscore;
    @FXML Button btnBack;
    @FXML Button btnRefresh;

    public Highscores() {
        TableColumn timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn CollectablesCol = new TableColumn("Collectables");
        CollectablesCol.setCellValueFactory(new PropertyValueFactory<>("amountOfStars"));

        TableColumn playerCol = new TableColumn("Players");
        playerCol.setCellValueFactory(new PropertyValueFactory<>("playerNames"));

        TableColumn lvlCol = new TableColumn("Level");
        lvlCol.setCellValueFactory(new PropertyValueFactory<>("gamename"));

        TableColumn scoreCol = new TableColumn("Total Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("totalScore"));

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (tableHighscore != null) {
                    tableHighscore.getColumns().addAll(timeCol, CollectablesCol, playerCol, lvlCol, scoreCol);
                    btnRefreshClick(null);
                }
            }
        });
    }

    public void btnBackClick(Event e) {
        changeScreen(ClientAdministration.Screens.Menu);
    }

    public void btnRefreshClick(Event e) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                // 
            }
        });
    }
}
