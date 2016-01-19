/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import s3proftaak.Client.Visuals.BasicScene;
import s3proftaak.Shared.IHost;
import s3proftaak.Shared.ILobby;

/**
 *
 * @author Berry-PC
 */
public class ClientAdministration extends Application {

    private static ClientAdministration instance;

    private static Stage primaryStage;

    public static void changeScreen(Screens s) {
        changeScreen(s, false);
    }

    public static void changeScreen(Screens s, boolean force) {
        if (!force) {
            if (getInstance().getCurrentScreen() != null && getInstance().getCurrentScreen().getListener() != null) {
                getInstance().getCurrentScreen().getListener().stopListening();
            }
        }

        changeStage(s);
    }

    private static void changeStage(Screens s) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                primaryStage.setScene(s.newInstance().getScene());
                try {
                    primaryStage.getScene().getStylesheets().add(new URL(getInstance().getClass().getResource("/Resources/Visuals/style.css").toExternalForm()).toString());
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ClientAdministration.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public static ClientAdministration getInstance() {
        return instance;
    }

    private AppGameContainer app;
    private Game game;
    private Account account;
    private ILobby currentLobby;
    private IHost host;

    private BasicScene currentScreen;

    private boolean isHost;

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

    public IHost getHost() {
        return host;
    }

    public void setHost(IHost host) {
        this.host = host;
    }

    public BasicScene getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(BasicScene bs) {
        currentScreen = bs;
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

    public void startGame(Game game) {
        if (this.game == null) {
            try {
                setGame(game);
            } catch (SlickException ex) {
                Logger.getLogger(ClientAdministration.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (game != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                        int width = gd.getDisplayMode().getWidth();
                        int height = gd.getDisplayMode().getHeight();
                        try {
                            getApp().setDisplayMode(width, height, getAccount().getSettings().isFullscreen());
                            getApp().setTargetFrameRate(60);
                            getApp().setForceExit(false);
                            getApp().start();
                            try {
                                SoundManager.getInstance().restartSound();
                                ClientAdministration.getInstance().getApp().reinit();
                            } catch (SlickException | RuntimeException ex) {
                            }
                        } catch (SlickException ex) {
                        }
                    }
                }).start();
            }
        }
    }

    public void stopGame() {
        getApp().exit();
        this.game = null;
    }

    public void connectionLost() {
        connectionLost("");
    }

    public void connectionLost(String reason) {
        if (this.game != null) {
            this.stopGame();
        }

        if (reason != null) {
            JOptionPane.showMessageDialog(null, reason.isEmpty() ? "Connection lost." : reason, "Failed.", 1);
        }

        RMIClient.clearInstance();

        changeScreen(ClientAdministration.Screens.Menu, true);
    }

    public boolean isHost() {
        return isHost;
    }

    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
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
        Register,
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
}
