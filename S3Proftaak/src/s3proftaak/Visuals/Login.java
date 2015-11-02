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
    @FXML Button btnRegister;
    
    public void btnLoginClick(Event e){
        try {
            if (DBConnect.getInstance().doUserLogin(tfUsername.getText(), tfPassword.getText())){
                Account a = DBConnect.getInstance().getAccount(tfUsername.getText());
                    Main.setAccount(a);
                changeScreen(Main.Screens.Menu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void btnRegisterClick(Event e){
        try {
            if (!DBConnect.getInstance().hasAccount(tfUsername.getText())){
                if (!tfUsername.getText().isEmpty() && !tfPassword.getText().isEmpty()){
                    Account a = new Account(tfUsername.getText(), tfPassword.getText(), null);
                    DBConnect.getInstance().insertAccount(a);
                }else{
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, "Enter an username & password.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
