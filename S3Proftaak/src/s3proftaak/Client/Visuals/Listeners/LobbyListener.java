package s3proftaak.Client.Visuals.Listeners;

import s3proftaak.Shared.IMessage;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javax.swing.JOptionPane;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.Game;
import s3proftaak.Client.Visuals.Lobby;
import s3proftaak.Host.HostBackup;
import s3proftaak.Shared.IHostBackup;
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
                        ClientAdministration.getInstance().startGame(new Game("SpaceMen Adventures", players.size(), evt.getNewValue().toString(), getNames()));

                        gameListener = new GameListener();
                        gameListener.startListening();

                    } catch (RemoteException ex) {
                        System.out.println(ex);
                        ClientAdministration.getInstance().connectionLost();
                    }
                }

                if (evt.getOldValue().toString().equals("ipAddress")) {
                    String hostip = evt.getNewValue().toString();
                    if (hostip.equals(ClientAdministration.getInstance().getAccount().getIp())) {
                        try {
                            //CREATE HOST
                            IHostBackup hb1 = new HostBackup(hostip);
                            ClientAdministration.getInstance().getCurrentLobby().bindHost(hb1, hostip);
                        } catch (RemoteException ex) {
                            System.out.println(ex);
                            ClientAdministration.getInstance().connectionLost();
                        }
                    }
                }

                if (evt.getOldValue().toString().equals("ipAddressForNotHost")) {
                    // Omdat dit Host verbinding is zou dit anders kunnen afgevangen worden maar normaal RemoteException -> .connectionLost methode.

                    IHostBackup hb = (IHostBackup) evt.getNewValue();
                    if (hb == null) {
                        System.out.println("Client failed to connect to the HOST. (Lookup failed)");
                    } else {
                        System.out.println("setting host hb : " + hb);
                        ClientAdministration.getInstance().setHostbackup(hb);
                    }

                }

                if (evt.getOldValue().toString().equals("ReallyStartGame")) {
                    ClientAdministration.getInstance().getGame().waitingForOtherPlayers();
                }

                if (evt.getOldValue().toString().equals("RestartGame")) {
                    ClientAdministration.getInstance().getGame().doRestart();
                }

                if (evt.getOldValue().toString().equals("StopGame")) {
                    gameListener.stopListening();
                    gameListener = null;
                    System.out.println("Received StopGame");
                    ClientAdministration.getInstance().stopGame();
                }

                if (evt.getOldValue().toString().equals("Kick")) {
                    if (evt.getNewValue().toString().equals(ClientAdministration.getInstance().getAccount().getUsername())) {
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                lobbyScreen.btnLeaveClick(null);
                                JOptionPane.showMessageDialog(null, "You got kicked!", "Kick", 0);
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
                this.lobbyScreen.setIsHost(ClientAdministration.getInstance().getAccount().getUsername().equals(evt.getNewValue().toString()));
                break;
        }
    }

    @Override
    public void startListening() {
        try {
            String username = ClientAdministration.getInstance().getAccount().getUsername();

            ClientAdministration.getInstance().getCurrentLobby().addListener(username, this, "Administrative");
            ClientAdministration.getInstance().getCurrentLobby().addListener(username, this, "Chat");
            ClientAdministration.getInstance().getCurrentLobby().addListener(username, this, "Players");
            ClientAdministration.getInstance().getCurrentLobby().addListener(username, this, "Level");
            ClientAdministration.getInstance().getCurrentLobby().addListener(username, this, "Host");
        } catch (RemoteException ex) {
            System.out.println(ex);
            ClientAdministration.getInstance().connectionLost();
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
            System.out.println(ex);
            ClientAdministration.getInstance().connectionLost();
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
            System.out.println(ex);
            ClientAdministration.getInstance().connectionLost();
        }

        return names;
    }
}
