package s3proftaak.Client.Visuals;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;

/**
 *
 * @author Roel
 */
public class CreateLobby {
    @FXML Label lblLobbyName;
    @FXML TextField txtLobbyName;
    @FXML Button btnBack;
    @FXML Button btnCreateLobby;
    
    public CreateLobby(){
        
    }
    
    public void btnBackClick(Event e){
        changeScreen(ClientAdministration.Screens.Multiplayer);
    }
}
