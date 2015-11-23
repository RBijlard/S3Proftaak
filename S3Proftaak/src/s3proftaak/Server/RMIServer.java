/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
    private Registry registry = null;
    private ServerAdministration serverAdministration = null;

    // Constructor
    public RMIServer() {

        // Print port number for registry
        System.out.println("Server: Port number " + portNumber);

        // Create MockEffectenbeurs
        try {
            serverAdministration = new ServerAdministration();
            System.out.println("Server: MockEffectenbeurs created");
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create MockEffectenbeurs");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            serverAdministration = null;
        }
        
        if (serverAdministration != null){
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
                registry.rebind(bindingName, serverAdministration);
            } catch (RemoteException ex) {
                System.out.println("Server: Cannot bind MockEffectenbeurs");
                System.out.println("Server: RemoteException: " + ex.getMessage());
            }
        }else {
            System.out.println("Server: MockEffectenbeurs not bound");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Welcome message
        System.out.println("SERVER USING REGISTRY");
        
        // Create server
        new RMIServer();
    }
}