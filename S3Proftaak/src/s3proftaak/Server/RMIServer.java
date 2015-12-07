/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Server;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Example of RMI using Registry
 *
 * @author Nico Kuijpers
 */
public class RMIServer {

    // Set port number

    private static final int portNumber = 1099;

    // Set binding name for MockEffectenbeurs
    private static final String bindingName = "S3Proftaak";

    // References to registry and MockEffectenbeurs
    private static Registry registry = null;

    // Constructor
    public RMIServer() {
        // Print port number for registry
        System.out.println("Server: Port number " + portNumber);

        // Create MockEffectenbeurs
        try {
            new ServerAdministration();
            System.out.println("Server: MockEffectenbeurs created");
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create MockEffectenbeurs");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }

        if (ServerAdministration.getInstance() != null) {
            // Create registry at port number
            try {
                registry = LocateRegistry.createRegistry(portNumber);
                System.out.println("Server: Registry created on port number " + portNumber);
            } catch (RemoteException ex) {
                System.out.println("Server: Cannot create registry");
                System.out.println("Server: RemoteException: " + ex.getMessage());
                registry = null;
            }

            // Bind MockEffectenbeurs using registry
            try {
                registry.rebind(bindingName, ServerAdministration.getInstance());
            } catch (RemoteException ex) {
                System.out.println("Server: Cannot bind MockEffectenbeurs");
                System.out.println("Server: RemoteException: " + ex.getMessage());
            }
        } else {
            System.out.println("Server: MockEffectenbeurs not bound");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Welcome message
        System.out.println("SERVER USING REGISTRY");

        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    if (i.getHostAddress().startsWith("145")){
                        //System.setProperty("java.rmi.server.hostname", i.getHostAddress());
                        break;
                    }
                }
            }

        } catch (SocketException ex) {
            Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Create server
        System.setProperty("java.rmi.server.hostname", "192.168.1.135");
        new RMIServer();
    }
}
