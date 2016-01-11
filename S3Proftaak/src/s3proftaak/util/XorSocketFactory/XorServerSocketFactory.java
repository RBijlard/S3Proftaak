package s3proftaak.util.XorSocketFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;
import s3proftaak.util.Constants;

public class XorServerSocketFactory
    implements RMIServerSocketFactory {

    private final byte pattern;

    public XorServerSocketFactory() {
        this.pattern = Constants.Pattern;
    }
    
    @Override
    public ServerSocket createServerSocket(int port)
        throws IOException
    {
        ServerSocket socket = new XorServerSocket(port, pattern);
        socket.setSoTimeout(5000);
        return socket;
    }
    
    @Override
    public int hashCode() {
        return (int) pattern;
    }

    @Override
    public boolean equals(Object obj) {
        return (getClass() == obj.getClass() &&
                pattern == ((XorServerSocketFactory) obj).pattern);
    }
}