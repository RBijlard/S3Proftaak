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
import s3proftaak.Client.ClientAdministration;
import static s3proftaak.Client.ClientAdministration.changeScreen;
import s3proftaak.Client.DBConnect;

/**
 *
 * @author Roel
 */
public class Register extends BasicScene {

    @FXML
    TextField tfUsername;
    @FXML
    TextField tfEmailAddres;
    @FXML
    PasswordField tfPassword;
    @FXML
    PasswordField tfPassword2;
    @FXML
    Button btnBack;
    @FXML
    Button btnRegister;

    public void btnRegisterClick(Event e) {
        try {
            if (!tfUsername.getText().isEmpty() && !tfPassword.getText().isEmpty() && !tfEmailAddres.getText().isEmpty()) {
                if (this.tfPassword.getText().equals(this.tfPassword2.getText())) {
                    if (!DBConnect.getInstance().hasAccount(tfUsername.getText())) {
                        Account a = new Account(tfUsername.getText(), tfPassword.getText(), null);
                        DBConnect.getInstance().insertAccount(a);
                        JOptionPane.showMessageDialog(null, "Account Registered!", "Registration", 1);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Passwords do not match!", "Registration Error", 0);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Enter a Username, EmailAddress and Password!", "Registration Error", 0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Account Register Failed!", "Registration Error", 0);
        }
    }
    
    public void btnBackClick(Event e) {
        changeScreen(ClientAdministration.Screens.Login);
    }
}
