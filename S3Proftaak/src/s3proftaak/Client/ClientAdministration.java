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
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import s3proftaak.Client.Visuals.BasicScene;
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
    private ILobby currentLobby;

    private BasicScene currentScreen;

    @Override
    public void start(Stage primarystage) throws IOException {
        instance = this;

        primaryStage = primarystage;
        primarystage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                if (getCurrentScreen().getListener() != null) {
                    getCurrentScreen().getListener().stopListening();
                }

                System.exit(0);

            }
        });

        changeScreen(Screens.Login);
        primaryStage.show();

        new SoundManager();
    }

    public static void changeScreen(Screens s) {
        if (getInstance().getCurrentScreen() != null && getInstance().getCurrentScreen().getListener() != null) {
            getInstance().getCurrentScreen().getListener().stopListening();
        }

        primaryStage.setScene(s.newInstance().getScene());
    }

    public BasicScene getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(BasicScene bs) {
        currentScreen = bs;
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
            bs = new BasicScene().load(this.getPath());
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
        if (this.game != null) {
            setApp(new AppGameContainer(getGame()));
        }
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

    private Thread gameThread;

    public void startGame(Game game) {
        if (this.game == null) {
            try {
                setGame(game);
            } catch (SlickException ex) {
                Logger.getLogger(ClientAdministration.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (game != null) {
                gameThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                        int width = gd.getDisplayMode().getWidth();
                        int height = gd.getDisplayMode().getHeight();

                        try {
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
                });
                gameThread.start();
            }
        }
    }

    public void stopGame() {
        getApp().exit();
        //changeScreen(ClientAdministration.Screens.Highscores);

        this.game = null;
    }
}
