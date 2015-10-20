/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Visuals;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import s3proftaak.Game;
import s3proftaak.Main;

/**
 *
 * @author Stan
 */
public class Menu extends BasicScene{
    
    private static AppGameContainer app;    
    private static Game game;
    
    public Menu(){
        Group root = new Group();
 
        Button b1 = new Button("Start");
        Button b2 = new Button("Settings");
        TextField t1 = new TextField();
        Label label = new Label("Players: ");
        
        b1.setOnMouseClicked((MouseEvent event) -> {
            try {
                if(!t1.getText().isEmpty()){
                    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                    int width = gd.getDisplayMode().getWidth();
                    int height = gd.getDisplayMode().getHeight();
                    int amountOfPlayers = 0;
                    try{
                    amountOfPlayers = Integer.parseInt(t1.getText());
                    }
                    catch(Exception ex){}
                    game = new Game("DEE game", amountOfPlayers);
                    app = new AppGameContainer(game);
                    app.setDisplayMode(width, height, false);
                    app.setTargetFrameRate(60);
                    app.setForceExit(false);
                    app.start();
                    app.reinit();
                }
                else{
                    
                }
            } catch (SlickException e) {}
        });
        
        b2.setOnMouseClicked((MouseEvent event) -> {
            Main.changeScene(new SettingsVisual().getScene());
        });
 
        GridPane pane = new GridPane();
        
        b1.setMinWidth(t1.getMinWidth());
        b2.setMinWidth(t1.getMinWidth());
        
        pane.add(label,0,0);
        pane.add(t1, 1, 0);
        pane.add(b1, 0, 1);
        pane.add(b2, 0, 2);        
        
        root.getChildren().add(pane);
        
//        HBox hBox = new HBox(10);
//        hBox.getChildren().addAll(b1, b2, t1);
//        root.getChildren().add(hBox);
        
        setScene(new Scene(root));
    }
    
    public static AppGameContainer getAppContainer(){
        return app;
    }
    public static Game getGame(){
        return game;
    }
}
