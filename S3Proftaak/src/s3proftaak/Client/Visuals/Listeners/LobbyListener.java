package s3proftaak.Client.Visuals.Listeners;

import s3proftaak.Shared.IMessage;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import org.newdawn.slick.SlickException;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.Game;
import s3proftaak.Client.SoundManager;
import s3proftaak.Client.Visuals.Lobby;
import s3proftaak.Shared.IPlayer;

/**
 *
 * @author Stan
 */
public class LobbyListener extends BasicListener {

    private final Lobby lobbyScreen;

    private GameListener gameListener;
    private List<IPlayer> players;

    public LobbyListener(Lobby lobby) throws RemoteException {
        this.lobbyScreen = lobby;

        this.players = new ArrayList<>();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "Administrative":
                if (evt.getOldValue().toString().equals("StartGame")) {
                    try {
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                ClientAdministration.getInstance().startGame(new Game("De Game", players.size(), evt.getNewValue().toString(), getNames()));
                            }
                        });

                        gameListener = new GameListener();
                        gameListener.startListening();

                    } catch (RemoteException ex) {
                        Logger.getLogger(LobbyListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                if (evt.getOldValue().toString().equals("ReallyStartGame")){
                    ClientAdministration.getInstance().getGame().waitingForOtherPlayers();
                }
                
                if (evt.getOldValue().toString().equals("RestartGame")) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                SoundManager.getInstance().restartSound();
                                ClientAdministration.getInstance().getApp().reinit();
                            } catch (SlickException ex) {
                                Logger.getLogger(LobbyListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }

                if (evt.getOldValue().toString().equals("StopGame")) {
                    gameListener.stopListening();
                    gameListener = null;
                    ClientAdministration.getInstance().stopGame();
                }

                if (evt.getOldValue().toString().equals("Kick")) {
                    if (evt.getNewValue().toString().equals(ClientAdministration.getInstance().getAccount().getUsername())) {
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                lobbyScreen.btnLeaveClick(null);
                            }
                        });
                    }
                }
                break;

            case "Chat":
                this.lobbyScreen.displayMessage((IMessage) evt.getNewValue());
                break;

            case "Players":
                if (evt.getNewValue() != null) {
                    this.players = (List<IPlayer>) evt.getNewValue();
                    this.lobbyScreen.updatePlayerList(this.players);
                }
                break;

            case "Level":
                this.lobbyScreen.comboboxSet(evt.getNewValue().toString());
                break;
            case "Host":
                if (ClientAdministration.getInstance().getAccount().getUsername().equals(evt.getNewValue().toString())) {
                    this.lobbyScreen.setIsHost(true);
                }
                break;
        }
    }

    @Override
    public void startListening() {
        try {
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Administrative");
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Chat");
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Players");
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Level");
            ClientAdministration.getInstance().getCurrentLobby().addListener(this, "Host");
        } catch (RemoteException ex) {
            Logger.getLogger(LobbyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stopListening() {
        try {
            if (gameListener != null) {
                gameListener.stopListening();
            }

            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Administrative");
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Chat");
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Players");
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Level");
            ClientAdministration.getInstance().getCurrentLobby().removeListener(this, "Host");

            ClientAdministration.getInstance().getCurrentLobby().removePlayer(ClientAdministration.getInstance().getAccount().getUsername());

        } catch (RemoteException ex) {
            Logger.getLogger(LobbyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<String> getNames() {
        List<String> names = new ArrayList<>();

        // Use a copied list to prevent a ConcurrentModificationException from happening.
        List<IPlayer> tempPlayers = new ArrayList<>();
        tempPlayers.addAll(players);

        try {
            for (IPlayer p : tempPlayers) {
                names.add(p.getName());
            }
        } catch (RemoteException ex) {
        }

        return names;
    }
}
