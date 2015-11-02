/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Visuals;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import s3proftaak.Account;
import s3proftaak.DBConnect;
import s3proftaak.Main;
import static s3proftaak.Main.changeScreen;

/**
 *
 * @author Stan
 */
public class Login extends BasicScene {

    @FXML TextField tfUsername;
    @FXML PasswordField tfPassword;
    @FXML Button btnLogin;
    @FXML  Button btnRegister;

    public void btnLoginClick(Event e) {
        try {
            if (!tfUsername.getText().isEmpty() && !tfPassword.getText().isEmpty()) {
                if (DBConnect.getInstance().doUserLogin(tfUsername.getText(), tfPassword.getText())) {
                    Account a = DBConnect.getInstance().getAccount(tfUsername.getText());
                    Main.setAccount(a);
                    changeScreen(Main.Screens.Menu.load());
                } else {
                    JOptionPane.showMessageDialog(null, "Username or Password invalid", "Login Error", 0);
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
