/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;
import s3proftaak.Client.Score;

/**
 *
 * @author Stan
 */
public class Gameover extends BasicScene {
    
    @FXML Label lblTime;
    @FXML Label lblStars;
    @FXML Label lblUsername;
    @FXML Button btnBack;
    
    public void loadFinished(){
        if (ClientAdministration.getGame() != null){
            Score score = ClientAdministration.getGame().getScore();
            lblTime.setText("" + score.getTime());
            lblStars.setText("" + score.getAmountOfStars());
            lblUsername.setText("" + score.getPlayerNames());
        }
    }
    
    public void btnBackClick(Event e){
        changeScreen(ClientAdministration.Screens.Singleplayer);
    }
}
