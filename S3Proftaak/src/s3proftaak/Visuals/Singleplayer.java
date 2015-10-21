/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Visuals;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
public class Singleplayer extends BasicScene {
    
    @FXML Button btnStart;
    @FXML TextField tfAmount;
    @FXML Button btnBack;
    @FXML ComboBox cbLevel;
    
    @Override
    public BasicScene load(String s){
        BasicScene bs = super.load(s);
        
        bs.getScene().addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                fillLevelList();
            }
        });
        
        return bs;
    }
    
    private void fillLevelList(){
        ArrayList levels = new ArrayList<>();
        
        for (File f : new File(getClass().getResource("/Resources/Levels/").getPath()).listFiles()){
            System.out.println(f.getName());
            levels.add(f.getName());
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

            Main.setGame(new Game("DEE game", amountOfPlayers));
            Main.getApp().setDisplayMode(width, height, false);
            Main.getApp().setTargetFrameRate(60);
            Main.getApp().setForceExit(false);
            Main.getApp().start();

            try {
                Main.getApp().reinit();
            } catch (Exception ex) {}
            
        } catch (SlickException ex) {}
    }
    
    public void btnBackClick(Event e){
        changeScreen(Main.Screens.Menu);
    }
    
    public void cbLevelClick(Event e){
        
    }
        
}
