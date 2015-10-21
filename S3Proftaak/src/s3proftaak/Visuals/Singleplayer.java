/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Visuals;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import org.newdawn.slick.SlickException;
import s3proftaak.Game;
import s3proftaak.Main;
import static s3proftaak.Main.changeScreen;

/**
 *
 * @author Stan
 */
public class Singleplayer extends BasicScene implements Initializable {
    
    @FXML Button btnStart;
    @FXML TextField tfAmount;
    @FXML Button btnBack;
    @FXML ComboBox cbLevel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList levels = new ArrayList<>();
        
        //System.out.println(new File(getClass().getResource("/Resources/Levels/").getPath().replaceAll("%20", " ")).listFiles());
        
        for (File f : new File(getClass().getResource("/Resources/Levels/").getPath().replaceAll("%20", " ")).listFiles()){
            if (f.getName().endsWith(".tmx")){
                levels.add(f.getName());
            }
        }
        
        if (!levels.isEmpty() && cbLevel != null){
            cbLevel.setItems(FXCollections.observableArrayList(levels));
        }
    }
    
    public void btnStartClick(Event e){
        try {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();
            int amountOfPlayers = 0;

            try {
                amountOfPlayers = Integer.parseInt(tfAmount.getText());
            } catch (Exception ex) {}

            if (cbLevel.getSelectionModel().getSelectedItem() != null){
                Main.setGame(new Game("DEE game", amountOfPlayers, cbLevel.getSelectionModel().getSelectedItem().toString()));
                Main.getApp().setDisplayMode(width, height, false);
                Main.getApp().setTargetFrameRate(60);
                Main.getApp().setForceExit(false);
                Main.getApp().start();

                try {
                    Main.getApp().reinit();
                } catch (Exception ex) {}
            }
            
        } catch (SlickException ex) {}
    }
    
    public void btnBackClick(Event e){
        changeScreen(Main.Screens.Menu);
    }
    
    public void cbLevelClick(Event e){
        
    }
        
}
