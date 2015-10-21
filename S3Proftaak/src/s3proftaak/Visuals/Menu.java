/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Visuals;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import s3proftaak.Game;
import s3proftaak.Main;
import static s3proftaak.Main.changeScreen;

/**
 *
 * @author Stan
 */
public class Menu extends BasicScene {
    
    @FXML Button btnStart;
    @FXML Button btnSettings;
    @FXML TextField tfAmount;
    
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
    
    public void btnSettingsClick(Event e){
        changeScreen(Main.Screens.Settings);
    }
}
