/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    PasswordField tfPasswordConf;
    @FXML
    Button btnBack;
    @FXML
    Button btnRegister;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public void btnRegisterClick(Event e) {
        try {
            String username = this.tfUsername.getText();
            String password = this.tfPassword.getText();
            String passwordconf = this.tfPasswordConf.getText();
            String email = this.tfEmailAddres.getText();

            if (!username.isEmpty() && !password.isEmpty() && !passwordconf.isEmpty() && !email.isEmpty()) {
                if (username.length() <= 13) {
                    if (validate(email)) {
                        if (password.equals(passwordconf)) {
                            if (!DBConnect.getInstance().hasAccount(username)) {

                                Account a = new Account(username, password, null);
                                DBConnect.getInstance().insertAccount(a);

                                //Clears all the textfields after Registration of the account
                                this.tfUsername.clear();
                                this.tfEmailAddres.clear();
                                this.tfPassword.clear();
                                this.tfPasswordConf.clear();

                                JOptionPane.showMessageDialog(null, "Account Registered!", "Registration Successful", 1);
                                
                                Login.EnterUsername(username);
                                changeScreen(ClientAdministration.Screens.Login);
                            } else {
                                JOptionPane.showMessageDialog(null, "Account already exists!", "Registration Error", 1);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Passwords do not match!", "Registration Error", 0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Not a valid email address!, Please enter a valid email address", "Registration Error", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Username is too long!, Please enter an username with max 13 characters", "Registration Error", 0);
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

    //Valedates the email address on the right characters
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
