package fiuba.pyp;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import rice.environment.Environment;
import rice.p2p.commonapi.Id;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Created by pablo on 24/03/14.
 */
public class AdaptedOperationManeger {

    LocalOperationHandler localOperationHandler;
    RemoteOperationHandler remoteOperationHandler;
    AddressDomain addressDomain;

    int bindport;


    public AdaptedOperationManeger(int bindport, InetSocketAddress bootaddress,Environment env) {

        this.bindport = bindport;
        this.localOperationHandler = new LocalOperationHandler();
        this.addressDomain = AddressDomain.getInstance();
        startRemoteHandler(bootaddress, env);
        ConcurrencyControl concurrencyControl = addressDomain.getConcurrencyControl();
        concurrencyControl.clearHistoryBuffer();
        concurrencyControl.setDoc(new DocumentText());

    }

    private void startRemoteHandler(InetSocketAddress bootaddress, Environment env) {
        try {
            remoteOperationHandler = new RemoteOperationHandler(bindport,bootaddress,env);
            remoteOperationHandler.addRemoteOperationListener(new RemoteOperationListener() {
                @Override
                public void dispatchNewOperationArrive(Event event) {

                    //Operation nextOp = remoteOperationHandler.getNextRemoteOperation();
                    //if(nextOp!=null){
                        //System.out.println("llego esta operacion" + nextOp);

                        //concurrencyControl.run(nextOp);
                        //System.out.println("documen contains " + concurrencyControl.getDoc().getDoc());
                    //}

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


        Runnable localPublisherHandler = new Runnable() {
            @Override
            public void run() {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String s = "";
                try {
//                    s = br.readLine();
                    int cant = 1;
                    while(!s.equals("exit")){
                        s = br.readLine();
                        Id localId = remoteOperationHandler.getId();

                        if(s.equals("1")){
                            Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", localId, 1);
                            remoteOperationHandler.publishOperation(a);
                        }else
                        if(s.equals("2")) {
                            Operation op = remoteOperationHandler.getNextRemoteOperation();
                            Id otherId = op.getId();
                            Operation b = new Operation(new DocumentCharacter("b"), 1, "INSERT", localId, 2);
                            Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", otherId, 1);
                            b.setOtherSitesOperations(a);
                            remoteOperationHandler.publishOperation(b);

                        }else
                        if(s.equals("3")) {
                            Operation b = new Operation(new DocumentCharacter("h"), 1, "INSERT", localId, 1);
                            remoteOperationHandler.publishOperation(b);

                        }else{

                            /*
                            Aca en realidad deberia llamarse al localOperationHandler pidiendo la proxima operacion
                            asi se le tagea el Id y se corre en el goto antes de enviarla por la red

                            Operation localOp = localOperationHandler.getNextOperation();
                            localOp.setId(localId);
                            localOp.setLocalTimeStamp(cant);
                            localOp = addressDomain.getConcurrencyControl().runOperation(localOp);
                            remoteOperationHandler.publishOperation(localOp);
                            AdaptedOperation adaptedOperation = localOperationHandler.transformToAdaptedOperation(localOp);
                            if (adaptedOperation != null){
                                localOperationHandler.run(adaptedOperation);
                            }
                            */

                            Operation newOp = new Operation(new DocumentCharacter(s), 0, "INSERT", localId, addressDomain.getConcurrencyControl().getTimeStamp());
                            newOp.setLocalTimeStamp(cant);
                            cant++;
                            remoteOperationHandler.publishOperation(newOp);
                            addressDomain.getConcurrencyControl().run(newOp);

                        }


                        System.out.println("documen contains " + addressDomain.getConcurrencyControl().getDoc().getDoc());

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };


        Runnable remotePublisherHandler = new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){

                        Operation op = remoteOperationHandler.getNextRemoteOperation();

                        if (op != null){
                            addressDomain.getConcurrencyControl().run(op);
//                            op = remoteOperationHandler.getNextRemoteOperation();

                            System.out.println("documen contains " + addressDomain.getConcurrencyControl().getDoc().getDoc());
                        }
                        else{
                            sleep(3600);
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        };

        Thread threadWriter = new Thread(localPublisherHandler);
        threadWriter.start();

        Thread threadWriter2 = new Thread(remotePublisherHandler);
        threadWriter2.start();

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
