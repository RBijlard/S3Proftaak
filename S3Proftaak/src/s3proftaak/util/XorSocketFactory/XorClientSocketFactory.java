/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.util.XorSocketFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import s3proftaak.util.Constants;

public class XorClientSocketFactory
    implements RMIClientSocketFactory, Serializable {

    private final byte pattern;

    public XorClientSocketFactory() {
        this.pattern = Constants.Pattern;
    }
    
    @Override
    public Socket createSocket(String host, int port)
        throws IOException
    {
        Socket socket = new XorSocket(pattern);
        socket.setSoTimeout(5000);
        socket.connect(new InetSocketAddress(host, port), 5000);
        return socket;
    }
    
    @Override
    public int hashCode() {
        return (int) pattern;
    }

    @Override
    public boolean equals(Object obj) {
        return (getClass() == obj.getClass() &&
                pattern == ((XorClientSocketFactory) obj).pattern);
    }
}