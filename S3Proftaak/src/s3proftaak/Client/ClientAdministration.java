/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import s3proftaak.Client.Visuals.BasicScene;
import s3proftaak.Client.Visuals.Lobby;
import s3proftaak.Shared.ILobby;

/**
 *
 * @author Berry-PC
 */
public class ClientAdministration extends Application {

    private static ClientAdministration instance;

    private static Stage primaryStage;

    private AppGameContainer app;
    private Game game;
    private Account account;
    private Music music;
    private ILobby currentLobby;

    private BasicScene currentScreen;

    @Override
    public void start(Stage primarystage) throws IOException {
        instance = this;

        primaryStage = primarystage;
        primarystage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {

                if (getCurrentScreen() instanceof Lobby) {
                    if (((Lobby) getCurrentScreen()).getChatController() != null) {
                        ((Lobby) getCurrentScreen()).getChatController().leaveLobby();
                    }
                }

                System.exit(0);

            }
        });

        changeScreen(Screens.Login);
        primaryStage.show();

        new SoundManager();
    }

    public static void changeScreen(Screens s) {
        primaryStage.setScene(s.newInstance().getScene());
    }

    public static BasicScene getCurrentScreen() {
        return getInstance().currentScreen;
    }

    public enum Screens {

        Login,
        Menu,
        Settings,
        Singleplayer,
        Multiplayer,
        CreateLobby,
        Lobby,
        Highscores,
        Gameover;

        private BasicScene bs;

        public Screens newInstance() {
            try {
                bs = ((BasicScene) Class.forName("s3proftaak.Client.Visuals." + this.name()).newInstance()).load(this.getPath());
                ClientAdministration.getInstance().currentScreen = bs;
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ClientAdministration.class.getName()).log(Level.SEVERE, null, ex);
            }
            return this;
        }

        private String getPath() {
            return "/Resources/Visuals/" + this.name() + ".fxml";
        }

        public Scene getScene() {
            return this.bs.getScene();
        }
    }

    public AppGameContainer getApp() {
        return app;
    }

    public void setApp(AppGameContainer app) {
        this.app = app;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) throws SlickException {
        this.game = game;
        setApp(new AppGameContainer(getGame()));
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ILobby getCurrentLobby() {
        return currentLobby;
    }

    public void setCurrentLobby(ILobby currentlobby) {
        currentLobby = currentlobby;
    }

    public static ClientAdministration getInstance() {
        return instance;
    }

    public void startGame(Game game) {
        ((Lobby) getCurrentScreen()).getChatController().startGame();

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        try {
            setGame(game);
            getApp().setDisplayMode(width, height, false);
            getApp().setTargetFrameRate(60);
            getApp().setForceExit(false);
            getApp().start();

            try {
                SoundManager.getInstance().restartSound();
                ClientAdministration.getInstance().getApp().reinit();
            } catch (Exception ex) {
            }

        } catch (SlickException ex) {
        }
    }

    public void stopGame() {
        ((Lobby) getCurrentScreen()).getChatController().stopGame();

        getApp().exit();
        //changeScreen(ClientAdministration.Screens.Highscores);
    }
}
