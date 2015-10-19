/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Visuals;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import s3proftaak.Administration;
import s3proftaak.Main;

/**
 *
 * @author Stan
 */
public class Menu extends BasicScene{
    
    public Menu(){
        Group root = new Group();
 
        Button b1 = new Button("Start");
        Button b2 = new Button("Settings");
        
        b1.setOnMouseClicked((MouseEvent event) -> {
            try {
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int width = gd.getDisplayMode().getWidth();
                int height = gd.getDisplayMode().getHeight();
                
                AppGameContainer app = new AppGameContainer(new Administration("Start"));
                app.setDisplayMode(width, height, false);
                app.setTargetFrameRate(60);
                app.start();
            } catch (SlickException e) {}
        });
        
        b2.setOnMouseClicked((MouseEvent event) -> {
            Main.changeScene(new SettingsVisual().getScene());
        });
 
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(b1, b2);
        root.getChildren().add(hBox);
        
        setScene(new Scene(root));
    }
}
