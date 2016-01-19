package s3proftaak.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import s3proftaak.util.RMIBase;
import s3proftaak.util.XorSocketFactory.XorClientSocketFactory;
import s3proftaak.util.XorSocketFactory.XorServerSocketFactory;

/**
 * @author S33D
 */
public class RMIServer extends RMIBase {

    public static void main(String[] args) {
        System.out.println("Starting server.");
        new RMIServer();
    }

    private final int portNumber = 1099;
    private final String bindingName = "S3Proftaak";

    public RMIServer() {
        String ip = this.getWirelessIpAddress();

        if (ip != null) {
            try {
                new ServerAdministration();
                LocateRegistry.createRegistry(portNumber, new XorClientSocketFactory(), new XorServerSocketFactory()).rebind(bindingName, ServerAdministration.getInstance());
                System.out.println("Server is online at: '" + ip + "'.");
            } catch (RemoteException ex) {
                System.out.println("Server is offline. \n" + ex);
            }
        } else {
            System.out.println("Failed to start the Server. (IP lookup)");
        }
    }
}
