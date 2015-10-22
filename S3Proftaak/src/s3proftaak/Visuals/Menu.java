/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Visuals;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import s3proftaak.Main;
import static s3proftaak.Main.changeScreen;

/**
 *
 * @author Stan
 */
public class Menu extends BasicScene {
    
    @FXML Button btnSingleplayer;
    @FXML Button btnMultiplayer;
    @FXML Button btnSettings;
    @FXML Button btnHighscores;
    
    public void btnSingleplayerClick(Event e){
        changeScreen(Main.Screens.Singleplayer);
    }
    
    public void btnMultiplayerClick(Event e){
        changeScreen(Main.Screens.Multiplayer);
    }
    
    public void btnSettingsClick(Event e){
        changeScreen(Main.Screens.Settings);
    }
    
    public void btnHighscoresClick(Event e){
        changeScreen(Main.Screens.Highscores);
    }
}
