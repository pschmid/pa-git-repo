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

    public AdaptedOperationManeger(InetSocketAddress localIP, InetSocketAddress bootaddress) {

        this.localOperationHandler = new LocalOperationHandler();
        this.addressDomain = AddressDomain.getInstance();
        startRemoteHandler(localIP,bootaddress);
        ConcurrencyControl concurrencyControl = addressDomain.getConcurrencyControl();
        concurrencyControl.clearHistoryBuffer();
        concurrencyControl.setDoc(new DocumentText());

    }

    private void startRemoteHandler(InetSocketAddress localIp, InetSocketAddress bootaddress) {
        try {
            remoteOperationHandler = new RemoteOperationHandler(localIp,bootaddress);
        } catch (Exception e) {
            // remind user how to use
            System.out.println("Usage:");
            System.out
                    .println("localIp localbindport bootIP bootPort");
            System.out
                    .println("example 192.168.56.101 9001 192.168.56.1 9001");

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


                            AdaptedOperation localAdaptedOp = localOperationHandler.getNextAdaptedOperation();
                            Operation localOp = localOperationHandler.executeLocalOperation(localAdaptedOp);
                            localOp.setId(localId);
                            localOp.setLocalTimeStamp(cant);
                            localOp = addressDomain.getConcurrencyControl().runOperation(localOp);
                            AdaptedOperation adaptedOperation = localOperationHandler.transformToAdaptedOperation(localOp);

                            localOp.setStateVector(remoteOperationHandler.getStateVector());
                            //Se deberia enviar la operacion adaptada al otro lado
                            remoteOperationHandler.publishOperation(localOp);

                            if (adaptedOperation != null){
                                localOperationHandler.run(adaptedOperation);
                            }
                            */


                            Operation newOp = new Operation(new DocumentCharacter(s), 0, "INSERT", localId, addressDomain.getConcurrencyControl().getTimeStamp());
                            newOp.setLocalTimeStamp(cant);
                            cant++;
                            addressDomain.getConcurrencyControl().run(newOp);
                            newOp = addressDomain.getConcurrencyControl().getLastOperation();
                            newOp.setStateVector(remoteOperationHandler.getStateVector());

                            //Aca esta mal porque se envia a la red la operacion original sin transformar anteriormente
                            //aviso porque a veces pasa que no escribe en la posicion que deberia
                            remoteOperationHandler.publishOperation(newOp);

                        }


                        System.out.println("document contains " + addressDomain.getConcurrencyControl().getDoc().getDoc());

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

                            System.out.println("document contains " + addressDomain.getConcurrencyControl().getDoc().getDoc());
                        }
                        else{
                            sleep(1);
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
