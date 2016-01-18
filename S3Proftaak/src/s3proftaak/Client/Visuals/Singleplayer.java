/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;
import s3proftaak.Client.Game;

/**
 *
 * @author Stan
 */
public class Singleplayer extends BasicScene implements Initializable {

    @FXML
    Button btnStart;
    @FXML
    TextField tfAmount;
    @FXML
    Button btnBack;
    @FXML
    ComboBox cbLevel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList levels = new ArrayList<>();

        for (File f : new File(getClass().getResource("/Resources/Levels/").getPath().replaceAll("%20", " ")).listFiles()) {
            if (f.getName().endsWith(".tmx")) {
                levels.add(f.getName());
            }
        }

        if (!levels.isEmpty() && cbLevel != null) {
            cbLevel.setItems(FXCollections.observableArrayList(levels));
        }
    }

    public void btnStartClick(Event e) {
        int amountOfPlayers = 0;

        try {
            amountOfPlayers = Integer.parseInt(tfAmount.getText());
        } catch (NumberFormatException ex) {
        }

        if (cbLevel.getSelectionModel().getSelectedItem() != null) {
            ClientAdministration.getInstance().startGame(new Game("De Game", amountOfPlayers, cbLevel.getSelectionModel().getSelectedItem().toString(), null));
        }
    }

    public void btnBackClick(Event e) {
        changeScreen(ClientAdministration.Screens.Menu);
    }

    public void cbLevelClick(Event e) {

    }

}
