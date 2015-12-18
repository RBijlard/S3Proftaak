package s3proftaak.Server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Enumeration;

/**
 * @author S33D
 */
public class RMIServer {

    private final int portNumber = 1099;
    private final String bindingName = "S3Proftaak";
    
    private static String ip;

    // Constructor
    public RMIServer() {
        try {
            new ServerAdministration();
            LocateRegistry.createRegistry(portNumber).rebind(bindingName, ServerAdministration.getInstance());
            System.out.println("Server is offline at: '" + ip + "'.");

        } catch (RemoteException ex) {
            System.out.println("Server is offline. \n" + ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Starting server.");

        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                if (n.getDisplayName().contains("Wireless")) {
                    Enumeration ee = n.getInetAddresses();
                    while (ee.hasMoreElements()) {
                        InetAddress i = (InetAddress) ee.nextElement();
                        System.setProperty("java.rmi.server.hostname", ip = i.getHostAddress());
                        break;
                    }
                }
            }
        } catch (Exception ex) {
        }
        
        if (!ip.isEmpty()){
            new RMIServer();
        }else{
            System.out.println("Failed to start the Server. (IP lookup)");
        }
    }
}
