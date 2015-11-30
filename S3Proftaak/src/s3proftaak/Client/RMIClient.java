/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import s3proftaak.Shared.IServer;

/**
 * Example of RMI using Registry
 *
 * @author Nico Kuijpers
 */
public class RMIClient {

    // Set binding name for MockEffectenbeurs
    private static final String bindingName = "S3Proftaak";

    // References to registry and MockEffectenbeurs
    private static Registry registry = null;
    private static IServer serverAdministration = null;

    // Constructor
    public RMIClient(String ipAddress, int portNumber) {

        // Print IP address and port number for registry
        System.out.println("Client: IP Address: " + ipAddress);
        System.out.println("Client: Port number " + portNumber);

        // Locate registry at IP address and port number
        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Print result locating registry
        if (registry != null) {
            System.out.println("Client: Registry located");
        } else {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: Registry is null pointer");
        }

        // Bind MockEffectenbeurs using registry
        if (registry != null) {
            try {
                serverAdministration = (IServer) registry.lookup(bindingName);
            } catch (RemoteException ex) {
                System.out.println("Client: Cannot bind MockEffectenbeurs");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                serverAdministration = null;
            } catch (NotBoundException ex) {
                System.out.println("Client: Cannot bind MockEffectenbeurs");
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                serverAdministration = null;
            }
        }

        // Print result binding MockEffectenbeurs
        if (serverAdministration != null) {
            System.out.println("Client: MockEffectenbeurs bound");
        } else {
            System.out.println("Client: MockEffectenbeurs is null pointer");
        }

        // Test RMI connection
        if (serverAdministration != null) {
            testConnectionEffectenbeurs();
        }
    }

    // Test RMI connection
    private void testConnectionEffectenbeurs() {
        try {
            Application.launch(ClientAdministration.class);
        }
        catch(Exception e)
        {
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    // Main method
    public static void main(String[] args) {

        // Welcome message
        System.out.println("CLIENT USING REGISTRY");

        // Get ip address of server
        Scanner input = new Scanner(System.in);
        System.out.print("Client: Enter IP address of server: ");
        String ipAddress = "localhost";//input.nextLine();

        // Get port number
        System.out.print("Client: Enter port number: ");
        int portNumber = 1099;//input.nextInt();

        // Create client
        new RMIClient(ipAddress, portNumber);
    }
    
    public static void bind(){
        try {
            registry.rebind(ClientAdministration.getInstance().getAccount().getUsername(), ClientAdministration.getInstance().getClientData());
        } catch (RemoteException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void unbind(){
        try {
            registry.unbind(ClientAdministration.getInstance().getAccount().getUsername());
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static IServer getServerAdministration(){
        return serverAdministration;
    }
}
