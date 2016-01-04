package s3proftaak.Client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import s3proftaak.Shared.IServer;

/**
 * @author S33D
 */
public class RMIClient {

    private final String bindingName = "S3Proftaak";
    private static RMIClient instance;
    private IServer serverAdministration = null;

    // Constructor
    public RMIClient() {
        instance = (RMIClient) this;
        
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                if (n.getDisplayName().contains("Wireless")) {
                    Enumeration ee = n.getInetAddresses();
                    while (ee.hasMoreElements()) {
                        InetAddress i = (InetAddress) ee.nextElement();
                        System.setProperty("java.rmi.server.hostname", i.getHostAddress());
                        ClientAdministration.getInstance().getAccount().setIp(i.getHostAddress());
                        System.out.println("JOUW IP ; " + ClientAdministration.getInstance().getAccount().getIp());
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        
        try {
            Registry registry = LocateRegistry.getRegistry("145.93.40.144", 1099);

            if (registry != null) {
                serverAdministration = (IServer) registry.lookup(bindingName);
                if (serverAdministration == null) {
                    System.out.println("Client failed to connect to the Server. (Lookup failed)");
                }
            } else {
                System.out.println("Client failed to connect to the Server. (Locate registry failed)");
            }

        } catch (RemoteException | NotBoundException ex) {
            System.out.println("Client failed to connect to the Server. \n" + ex);
        }
    }

    public static RMIClient getInstance() {
        return instance;
    }

    public IServer getServerAdministration() {
        return serverAdministration;
    }
}
