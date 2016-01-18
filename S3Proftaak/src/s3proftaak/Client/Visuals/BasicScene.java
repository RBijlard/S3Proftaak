/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client.Visuals;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import s3proftaak.Client.ClientAdministration;
import s3proftaak.Client.Visuals.Listeners.BasicListener;

/**
 *
 * @author Stan
 */
public class BasicScene {
    private Scene scene;
    private BasicListener listener;
    
    public BasicScene(){
        ClientAdministration.getInstance().setCurrentScreen(this);
    }
    
    public BasicScene load(String s) {
        try {
            this.setScene(new Scene(FXMLLoader.load(new URL(BasicScene.class.getResource(s).toExternalForm()))));
        } catch (IOException ex) {
            Logger.getLogger(BasicScene.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
    
    public Scene getScene(){
        return this.scene;
    }
    
    public void setScene(Scene s){
        this.scene = s;
    }

    public BasicListener getListener() {
        return this.listener;
    }
    
    public void setListener(BasicListener listener){
        this.listener = listener;
    }
}
