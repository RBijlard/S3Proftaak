/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import s3proftaak.Client.Account;
import s3proftaak.Client.DBConnect;
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;
import s3proftaak.Client.SoundManager;

/**
 *
 * @author Stan
 */
public class Login extends BasicScene {

    @FXML
    TextField tfUsername;
    @FXML
    PasswordField tfPassword;
    @FXML
    Button btnLogin;
    @FXML
    Button btnRegister;

    public void btnLoginClick(Event e) {
        try {
            String username = this.tfUsername.getText();
            String password = this.tfPassword.getText();

            if (!username.isEmpty() && !password.isEmpty()) {
                if (DBConnect.getInstance() != null) {
                    if (DBConnect.getInstance().doUserLogin(username, password)) {
                        Account a = DBConnect.getInstance().getAccount(username);

                        s3proftaak.Client.Settings settings = DBConnect.getInstance().getSettings(username);

                        if (settings != null) {
                            a.setSettings(settings);
                            ClientAdministration.getInstance().setAccount(new Account(username, password, settings));
                            if (!settings.isSoundMute()) {
                                SoundManager.getInstance().playMenuMusic();
                            }
                            if (settings.isFullscreen()) {
                                //implement fullscreen
                            }
                        } else {
                            ClientAdministration.getInstance().setAccount(new Account(username, password, null));
                            SoundManager.getInstance().playMenuMusic();
                        }

                        changeScreen(ClientAdministration.Screens.Menu);
                    } else {
                        JOptionPane.showMessageDialog(null, "Username or Password invalid", "Login Error", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to connect with the database", "Database Error", 0);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Enter a Username and Password", "Registration Error", 0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void btnRegisterClick(Event e) {
        changeScreen(ClientAdministration.Screens.Register);
    }

    //sets the username if the user just registered an account
    public void enterUsername(String username) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfUsername.setText(username);
                tfPassword.requestFocus();
            }
        });
    }
}
