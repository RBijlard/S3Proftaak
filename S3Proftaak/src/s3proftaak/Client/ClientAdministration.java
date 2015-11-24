/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import s3proftaak.Client.SoundManager;
import s3proftaak.Client.Game;
import s3proftaak.Client.Account;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import s3proftaak.Client.Visuals.BasicScene;

/**
 *
 * @author Berry-PC
 */
public class ClientAdministration extends Application {
    private static Stage primaryStage;
    
    private static AppGameContainer app;
    private static Game game;
    private static Account account;
    private static Music music;
    
    @Override
    public void start(Stage primarystage) throws IOException {
        primaryStage = primarystage;
        changeScreen(Screens.Login);
        primaryStage.show();
        
        new SoundManager();
    }
    
    public static void changeScreen(Screens s){
        primaryStage.setScene(s.newInstance().getScene());
    }
    
    public enum Screens{
        Login,
        Menu,
        Settings,
        Singleplayer,
        Multiplayer,
        Lobby,
        Highscores,
        Gameover;
        
        private BasicScene bs;
        
        public Screens newInstance(){
            try {
                bs = ((BasicScene) Class.forName("s3proftaak.Client.Visuals." + this.name()).newInstance()).load(this.getPath());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ClientAdministration.class.getName()).log(Level.SEVERE, null, ex);
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
        ClientAdministration.app = app;
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) throws SlickException {
        ClientAdministration.game = game;
        setApp(new AppGameContainer(getGame()));
    }

    public static Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        ClientAdministration.account = account;
    }
}
