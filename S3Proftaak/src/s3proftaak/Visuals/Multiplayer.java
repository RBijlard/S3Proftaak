/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Visuals;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import s3proftaak.Main;
import static s3proftaak.Main.changeScreen;

/**
 *
 * @author Stan
 */
public class Multiplayer extends BasicScene {
    
    @FXML Button btnStart;
    @FXML TextField tfAmount;
    @FXML Button btnBack;
    
    public void btnStartClick(Event e){
        // Todo
    }
    
    public void btnBackClick(Event e){
        changeScreen(Main.Screens.Menu.load());
    }
}
