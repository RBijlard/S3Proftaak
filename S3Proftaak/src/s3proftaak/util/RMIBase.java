/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 *
 * @author Stan
 */
public class RMIBase {

    public String getWirelessIpAddress() {
        String ip = null;

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
        } catch (SocketException ex) {
        }
        
        System.setProperty("java.rmi.server.hostname", ip = "169.254.164.158");

        return ip;
    }
}
