package fiuba.pyp;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rice.environment.Environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by pablo on 24/03/14.
 */
public class MainRed {

    public static void main(String[] args) throws Exception {
        // the Ip and port to use locally
        InetAddress localIpAddr = InetAddress.getByName(args[0]);
        int bindport = Integer.parseInt(args[1]);
        InetSocketAddress localIP = new InetSocketAddress(localIpAddr, bindport);

        // build the bootaddress from the command line args
        InetAddress bootaddr = InetAddress.getByName(args[2]);
        int bootport = Integer.parseInt(args[3]);
        InetSocketAddress bootaddress = new InetSocketAddress(bootaddr, bootport);

        // launch our node!
        AdaptedOperationManeger dt = new AdaptedOperationManeger(localIP, bootaddress);

    }

}
