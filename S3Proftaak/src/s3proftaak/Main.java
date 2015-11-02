/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import s3proftaak.Visuals.BasicScene;

/**
 *
 * @author Berry-PC
 */
public class Main extends Application {
    private static Stage primaryStage;
    
    private static AppGameContainer app;
    private static Game game;
    private static Account account;

    public static void main(String[] arguments) {
        launch();
    }
    
    @Override
    public void start(Stage primarystage) throws IOException {
        primaryStage = primarystage;
        changeScreen(Screens.Login.load());
        primaryStage.show();
    }
    
    public static void changeScreen(Screens s){
        primaryStage.setScene(s.getScene());
    }
    
    public enum Screens{
        Login,
        Menu,
        Settings,
        Singleplayer,
        Multiplayer,
        Highscores,
        Gameover;
        
        private BasicScene bs;
        
        public Screens load(){
            try {
                bs = ((BasicScene) Class.forName("s3proftaak.Visuals." + this.name()).newInstance()).load(this.getPath());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            return this;
        }
        
        private String getPath(){
            return "/Resources/Visuals/" + this.name() + ".fxml";
        }
        
        public Scene getScene(){
            return this.bs.getScene();
        }
    }
    
    public static AppGameContainer getApp() {
        return app;
    }

    public static void setApp(AppGameContainer app) {
        Main.app = app;
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) throws SlickException {
        Main.game = game;
        setApp(new AppGameContainer(getGame()));
    }

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        Main.account = account;
    }
}
