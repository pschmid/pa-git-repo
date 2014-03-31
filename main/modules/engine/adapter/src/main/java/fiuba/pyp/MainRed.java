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
        // Loads pastry configurations
        Environment env = new Environment();

        // disable the UPnP setting (in case you are testing this on a NATted LAN)
        env.getParameters().setString("nat_search_policy","never");


        // the port to use locally
        int bindport = Integer.parseInt(args[0]);

        // build the bootaddress from the command line args
        InetAddress bootaddr = InetAddress.getByName(args[1]);
        int bootport = Integer.parseInt(args[2]);
        InetSocketAddress bootaddress = new InetSocketAddress(bootaddr, bootport);

        // launch our node!

        AdaptedOperationManeger dt = new AdaptedOperationManeger(bindport, bootaddress, env);


    }

}
