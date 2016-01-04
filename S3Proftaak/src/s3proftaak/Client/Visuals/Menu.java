/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;
import s3proftaak.Client.RMIClient;

/**
 *
 * @author Stan
 */
public class Menu extends BasicScene {

    @FXML
    Button btnSingleplayer;
    @FXML
    Button btnMultiplayer;
    @FXML
    Button btnSettings;
    @FXML
    Button btnHighscores;
    @FXML
    Button btnQuit;
    @FXML
    Label lblTitle;
    @FXML
    GridPane grid;

    public void btnSingleplayerClick(Event e) {
        changeScreen(ClientAdministration.Screens.Singleplayer);
    }

    public void btnMultiplayerClick(Event e) {

        new RMIClient();

        if (RMIClient.getInstance() != null) {
            changeScreen(ClientAdministration.Screens.Multiplayer);
        } else {
            JOptionPane.showMessageDialog(null, "Connection unsuccessfull!", "Connection Error", 0);
        }
    }

    public void btnSettingsClick(Event e) {
        changeScreen(ClientAdministration.Screens.Settings);
    }

    public void btnHighscoresClick(Event e) {
        changeScreen(ClientAdministration.Screens.Highscores);
    }

    public void btnQuitClick(Event e) {
        System.exit(0);
    }
}
