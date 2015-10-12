/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import s3proftaak.Visuals.Menu;

/**
 *
 * @author Berry-PC
 */
public class Main extends Application {
    private static Stage primaryStage;
    
    public static void main(String[] arguments) {
        launch();
    }
    
    @Override
    public void start(Stage primarystage) throws Exception {
        primaryStage = primarystage;
        primaryStage.setScene(new Menu().getScene());
        primaryStage.show();
    }
    
    public static void changeScene(Scene s){
        primaryStage.setScene(s);
    }
}
