package s3proftaak.Client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Enumeration;
import s3proftaak.Shared.IServer;
import s3proftaak.util.XorSocketFactory.XorClientSocketFactory;

/**
 * @author S33D
 */
public class RMIClient {

    private final String bindingName = "S3Proftaak";
    private static RMIClient instance;
    private IServer serverAdministration = null;

    // Constructor
    public RMIClient() {
        System.out.println("Starting client.");
        
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
                        break;
                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println("Client failed to connect to the Server. \n" + ex);
            return;
        }
        
        System.setProperty("java.rmi.server.hostname","192.168.1.101");
        
        try {
            serverAdministration = (IServer) LocateRegistry.getRegistry("192.168.1.100", 1099, new XorClientSocketFactory()).lookup(bindingName);
        } catch (RemoteException | NotBoundException ex) {
            System.out.println("Client failed to connect to the Server. \n" + ex);
            return;
        }
        
        instance = (RMIClient) this;
        System.out.println("Client started.");
    }

    public static RMIClient getInstance() {
        return instance;
    }
    
    public static void clearInstance(){
        instance = null;
    }

    public IServer getServerAdministration() {
        return serverAdministration;
    }
}
