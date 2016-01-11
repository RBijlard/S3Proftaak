/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import java.sql.SQLException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;
import s3proftaak.Client.DBConnect;
import s3proftaak.Client.SoundManager;

/**
 *
 * @author Stan
 */
public class Settings extends BasicScene {

    @FXML
    ToggleButton btnSound;
    @FXML
    ToggleButton btnFullscreen;
    @FXML
    Button btnBack;
    @FXML
    ComboBox cbSkin;

    private ClientAdministration instance;
    private SoundManager Soundinstance;
    private s3proftaak.Client.Settings settings;

    public Settings() {
        this.instance = ClientAdministration.getInstance();
        this.Soundinstance = SoundManager.getInstance();
        this.settings = ClientAdministration.getInstance().getAccount().getSettings();
        

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (instance.getAccount().getSettings().isSoundMute()) {
                    btnSound.setText("UnMute");
                } else {
                    btnSound.setText("Mute");
                }
                if(instance.getAccount().getSettings().isFullscreen()){
                    btnFullscreen.setText("Deactivate");
                }
                else{
                    btnFullscreen.setText("Activate");
                }
            }
        });
    }

    public void btnSoundClick(Event e) {
        try {
            if (this.settings.isSoundMute()) {
                this.settings.setSoundMute(false);
                this.Soundinstance.playMenuMusic();
                this.btnSound.setText("Mute");
            } else {
                this.settings.setSoundMute(true);
                this.Soundinstance.stopMusic();
                this.btnSound.setText("UnMute");
            }

            DBConnect.getInstance().updateSettings(this.instance.getAccount().getUsername(), this.settings);
        } catch (SQLException se) {
            System.err.println(se.fillInStackTrace());
        }
    }

    public void btnFullscreenClick(Event e){
        setFullScreen();
    }

    public void btnBackClick(Event e) {
        changeScreen(ClientAdministration.Screens.Menu);
    }
    
    public void setFullScreen(){
        try{
            this.settings.setFullscreen(!this.settings.isFullscreen());
            if(this.settings.isFullscreen()){
                btnFullscreen.setText("Deactivate");
            }
            else{
                btnFullscreen.setText("Activate");
            }
            DBConnect.getInstance().updateSettings(this.instance.getAccount().getUsername(), this.settings);
            }
        catch(SQLException se){
            System.err.println(se.fillInStackTrace());
        }
    }
}
