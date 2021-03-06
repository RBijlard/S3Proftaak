package s3proftaak.Client.Visuals;

import java.rmi.RemoteException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;
import s3proftaak.Client.RMIClient;
import s3proftaak.Shared.ILobby;
import s3proftaak.util.CustomException;

/**
 *
 * @author Roel
 */
public class CreateLobby extends BasicScene {

    @FXML
    Label lblLobbyName;
    @FXML
    TextField txtLobbyName;
    @FXML
    Button btnBack;
    @FXML
    Button btnCreateLobby;

    public void btnBackClick(Event e) {
        changeScreen(ClientAdministration.Screens.Multiplayer);
    }

    public void btnCreateLobbyClick(Event e) {
        if (txtLobbyName != null) {
            if (!txtLobbyName.getText().isEmpty()) {
                try {
                    ILobby tempLobby = RMIClient.getInstance().getServerAdministration().createLobby(txtLobbyName.getText());
                    tempLobby.addPlayer(ClientAdministration.getInstance().getAccount().getUsername(), ClientAdministration.getInstance().getAccount().getIp());
                    ClientAdministration.getInstance().setCurrentLobby(tempLobby);
                    changeScreen(ClientAdministration.Screens.Lobby);
                } catch (CustomException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Failed.", 1);
                } catch (RemoteException ex) {
                    System.out.println(ex);
                    ClientAdministration.getInstance().connectionLost();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Specify a lobby name.", "Failed.", 1);
            }
        }
    }
}
