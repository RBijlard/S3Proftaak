/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import s3proftaak.Client.RMIClient;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;
import s3proftaak.Shared.ILobby;

/**
 *
 * @author Stan
 */
public class Lobby extends BasicScene {
    
    @FXML Label lblLobbyName;
    @FXML ListView chatList;
    @FXML ListView playerList;
    @FXML TextField chatText;
    @FXML Button btnSend;
    @FXML Button btnKick;
    @FXML Button btnLeave;
    @FXML Button btnReady;
    
    public Lobby(){
        
    }
    
    public void btnSendClick(Event e) {
        
    }
    
    public void btnKickClick(Event e) {
        
    }
    
    public void btnReadyClick(Event e) {
        
    }
    
    public void btnLeaveClick(Event e) {
        changeScreen(ClientAdministration.Screens.Menu);
    }
}
