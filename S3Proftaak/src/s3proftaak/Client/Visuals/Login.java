/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import s3proftaak.Client.RMIClient;
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
            if (!tfUsername.getText().isEmpty() && !tfPassword.getText().isEmpty()) {
                if (DBConnect.getInstance() != null) {
                    if (DBConnect.getInstance().doUserLogin(tfUsername.getText(), tfPassword.getText())) {
                        Account a = DBConnect.getInstance().getAccount(tfUsername.getText());
                        ClientAdministration.getInstance().setAccount(new Account(tfUsername.getText(), tfPassword.getText(), null));

                        SoundManager.getInstance().playMenuMusic();

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
        try {
            if (!tfUsername.getText().isEmpty() && !tfPassword.getText().isEmpty()) {
                if (!DBConnect.getInstance().hasAccount(tfUsername.getText())) {
                    Account a = new Account(tfUsername.getText(), tfPassword.getText(), null);
                    DBConnect.getInstance().insertAccount(a);
                    JOptionPane.showMessageDialog(null, "Account Registered!", "Registration", 1);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Enter a Username and Password", "Registration Error", 0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
