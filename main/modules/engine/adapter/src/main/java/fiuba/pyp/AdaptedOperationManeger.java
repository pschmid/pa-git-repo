package fiuba.pyp;

import rice.environment.Environment;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by pablo on 24/03/14.
 */
public class AdaptedOperationManeger {

    RemoteOperationHandler remoteOperationHandler;

    public AdaptedOperationManeger(int bindport, InetSocketAddress bootaddress,Environment env) {
        try {
            remoteOperationHandler = new RemoteOperationHandler(bindport,bootaddress,env);
            remoteOperationHandler.addRemoteOperationListener(new RemoteOperationListener() {
                @Override
                public void dispatchNewOperationArrive(Event event) {

                    Operation nextOp = remoteOperationHandler.getNextRemoteOperation();
                    if(nextOp!=null)
                        System.out.println("llego esta operacion" + nextOp);

                }
            });
        } catch (Exception e) {
            // remind user how to use
            System.out.println("Usage:");
            System.out
                    .println("java [-cp FreePastry-<version>.jar] rice.tutorial.scribe.ScribeTutorial localbindport bootIP bootPort numNodes");
            System.out
                    .println("example java rice.tutorial.scribe.ScribeTutorial 9001 pokey.cs.almamater.edu 9001 10");

        }
        start();

    }
    public void start(){


        Runnable remotePublisherHandler = new Runnable() {
            @Override
            public void run() {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String s = null;
                try {
                    s = br.readLine();
                    while(!s.equals("exit")){
                        s = br.readLine();
                        Operation newOp = new Operation(new DocumentCharacter(s), 0, "INSERT", 1, 1);
                        remoteOperationHandler.publishOperation(newOp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        Thread threadWriter = new Thread(remotePublisherHandler);
        threadWriter.start();

    }


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
