/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import s3proftaak.Shared.IServer;

/**
 * @author S33D
 */
public class RMIClient {

    private final String bindingName = "S3Proftaak";
    private static IServer serverAdministration = null;

    // Constructor
    public RMIClient(String ipAddress, int portNumber) {
        try {
            Registry registry = LocateRegistry.getRegistry(ipAddress, portNumber);
            
            if (registry != null){
                serverAdministration = (IServer) registry.lookup(bindingName);
                if (serverAdministration != null){
                    System.out.println("Client started.");
                    Application.launch(ClientAdministration.class);
                }else{
                    System.out.println("Client failed to connect to the Server. (Lookup failed)");
                }
            }else{
                System.out.println("Client failed to connect to the Server. (Locate registry failed)");
            }
            
        } catch (RemoteException | NotBoundException ex) {
            System.out.println("Client failed to connect to the Server. \n" + ex);
        }
    }

    // Main method
    public static void main(String[] args) {
        System.out.println("Starting client.");
        
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

        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                if (n.getDisplayName().contains("Wireless")) {
                    Enumeration ee = n.getInetAddresses();
                    while (ee.hasMoreElements()) {
                        InetAddress i = (InetAddress) ee.nextElement();
                        System.setProperty("java.rmi.server.hostname", i.getHostAddress());
                        break;
                    }
                }
            }
        } catch (Exception ex) {
        }

        new RMIClient("localhost", 1099);
    }

    public static IServer getServerAdministration() {
        return serverAdministration;
    }
}
