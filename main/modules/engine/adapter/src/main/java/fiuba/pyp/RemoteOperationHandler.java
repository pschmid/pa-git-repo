package fiuba.pyp;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablo on 09/03/14.
 */
public class RemoteOperationHandler implements Runnable{

    private List<Operation> fromApplicationOperations;

    private List<Socket> connections;
    private String TimeStamp;
    private int ID;
    private static int PORT = 19999;
    private int count;

    public void start() {

        //add a listener to de fromapplicationlist and send the package every time a new operations
        //is addded to the list.

        //add a listener to the  fromremoteOperationList and every time a new operation is add, manage the logic way to
        //send it to AOM.



        System.out.println("MultipleSocketServer Initialized");
        Runnable connectionHandler =  new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket socket1 = new ServerSocket(PORT);
                    Socket connection;
                    while (true) {


                        connection = socket1.accept();
                        connections.add(connection);
                        Runnable runnable = new RemoteConnectionListener(connection, ++count);
                        Thread thread = new Thread(runnable);
                        thread.start();
                        //runnable = new RemoteConnectionWriter(connection,count);
                        //thread = new Thread(runnable);
                        //thread.start();
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
        };
        Thread thread = new Thread(connectionHandler);
        thread.start();
    }

    RemoteOperationHandler(int i) {
        this.fromApplicationOperations = new ArrayList<Operation>();
        this.ID = i;
        count = 0;
        connections = new ArrayList<Socket>();
        start();
    }




    public List<Operation> getFromApplicationOperations() {
        return fromApplicationOperations;
    }

    public void setFromApplicationOperations(List<Operation> fromApplicationOperations) {
        this.fromApplicationOperations = fromApplicationOperations;
    }


    @Override
    public void run() {

        /** Define a host server */
        String host = "localhost";
        /** Define a port */
        int port = 19999;

        StringBuffer instr = new StringBuffer();
        String TimeStamp;
        System.out.println("SocketClient initialized");
        try {
            /** Obtain an address object of the server */
            InetAddress address = InetAddress.getByName(host);
            /** Establish a socket connetion */
            Socket connection = new Socket(address, port);
            /** Instantiate a BufferedOutputStream object */

            //BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());

            /** Instantiate an OutputStreamWriter object with the optional character
             * encoding.
             */
            //OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");


            OutputStream os = connection.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            Operation op = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
            NetworkObject networkObject = new NetworkObject(op, 1);
            oos.writeObject(networkObject);
            oos.flush();



            TimeStamp = new java.util.Date().toString();
            String process = "Calling the Socket Server on "+ host + " port " + port +
                    " at " + TimeStamp +  (char) 13;

            /** Write across the socket connection and flush the buffer */
            //osw.write(process);
            //osw.flush();
            /** Instantiate a BufferedInputStream object for reading
             /** Instantiate a BufferedInputStream object for reading
             * incoming socket streams.
             */

            //BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            /**Instantiate an InputStreamReader with the optional
             * character encoding.
             */

            //InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");

            InputStream is = connection.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            networkObject = (NetworkObject)ois.readObject();
            if ( networkObject.getAck() == 1){
                System.out.println("recibi el ack");
            }


            /**Read the socket's InputStream and append to a StringBuffer */
            //int c;
            //while ( (c = isr.read()) != 13)
              //  instr.append( (char) c);

            /** Close the socket connection. */
            connection.close();
            //System.out.println(instr);
        }
        catch (IOException f) {
            System.out.println("IOException: " + f);
        }
        catch (Exception g) {
            System.out.println("Exception: " + g);
        }
    }

}
