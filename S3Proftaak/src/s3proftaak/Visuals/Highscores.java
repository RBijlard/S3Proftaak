/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Visuals;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import s3proftaak.DBConnect;
import s3proftaak.Main;
import static s3proftaak.Main.changeScreen;
import s3proftaak.Score;

/**
 *
 * @author Stan
 */
public class Highscores extends BasicScene {
    
    @FXML ListView lvHighscores;
    @FXML Button btnBack;
    @FXML Button btnRefresh;
    
    public void btnBackClick(Event e){
        changeScreen(Main.Screens.Menu.load());
    }
    
    public void btnRefreshClick(Event e){
        lvHighscores.getItems().clear();
        
        try {
            for (Score score : DBConnect.getInstance().getScores()){
                lvHighscores.getItems().add(score.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Highscores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
