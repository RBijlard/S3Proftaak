/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;

/**
 *
 * @author Stan
 */
public class Menu extends BasicScene {

    @FXML Button btnSingleplayer;
    @FXML Button btnMultiplayer;
    @FXML Button btnSettings;
    @FXML Button btnHighscores;
    @FXML Button btnQuit;

    public void btnSingleplayerClick(Event e) {
        changeScreen(ClientAdministration.Screens.Singleplayer);
    }

    public void btnMultiplayerClick(Event e) {
        changeScreen(ClientAdministration.Screens.Multiplayer);
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
