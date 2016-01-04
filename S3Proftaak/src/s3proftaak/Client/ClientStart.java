/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;

/**
 *
 * @author Berry-PC
 */
public class ClientStart {

    public static void main(String[] args) {
        System.out.println("Starting application.");

        // Dynamisch path van Slick2D instellen
        System.setProperty("java.library.path", RMIClient.class.getResource("/Resources/Slick2D").getPath().replace("%20", " ").substring(1));

        try {
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Path ingesteld
        
        System.out.println("Application started.");
        Application.launch(ClientAdministration.class);

    }

}
