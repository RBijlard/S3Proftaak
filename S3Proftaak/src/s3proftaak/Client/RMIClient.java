package s3proftaak.Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import s3proftaak.Shared.IServer;
import s3proftaak.util.RMIBase;
import s3proftaak.util.XorSocketFactory.XorClientSocketFactory;

/**
 * @author S33D
 */
public class RMIClient extends RMIBase {

    private static RMIClient instance;

    public static RMIClient getInstance() {
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }
    
    private final String hostAddress = "localhost";
    private final String bindingName = "S3Proftaak";
    private IServer serverAdministration = null;

    // Constructor
    public RMIClient() {
        System.out.println("Starting client.");

        String ip = this.getWirelessIpAddress();

        if (ip == null) {
            System.out.println("Failed to start the Client. (IP lookup)");
            return;
        }

        ClientAdministration.getInstance().getAccount().setIp(ip);

        try {
            serverAdministration = (IServer) LocateRegistry.getRegistry(hostAddress, 1099, new XorClientSocketFactory()).lookup(bindingName);
        } catch (RemoteException | NotBoundException ex) {
            System.out.println("Client failed to connect to the Server. \n" + ex);
            return;
        }

        instance = (RMIClient) this;
        System.out.println("Client started.");
    }

    public IServer getServerAdministration() {
        return serverAdministration;
    }

}
