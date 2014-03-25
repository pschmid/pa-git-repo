package fiuba.pyp;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * Created by pablo on 24/03/14.
 */
public class RemoteConnectionListener implements Runnable {

    private Socket  connection;
    private int id;

        public RemoteConnectionListener(Socket connection, int i) {
            this.connection = connection;
            this.id = i;
        }


    @Override
    public void run() {

        //while(true)
        try{


            InputStream is = connection.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            NetworkObject obj = (NetworkObject)ois.readObject();
            int messageId = obj.getId();

            System.out.println(" objeto arribado = " + obj.getOperation().getType() + "timeStamp " + obj.getId() );
            //BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
            //InputStreamReader isr = new InputStreamReader(is);
            //int character;
            //StringBuffer process = new StringBuffer();
            //while((character = isr.read()) != 13) {
                //process.append((char)character);
            //}
            //System.out.println(process);
            //need to wait 10 seconds to pretend that we're processing something

            //String TimeStamp = new Date().toString();
            //String returnCode = "MultipleSocketServer repsonded at "+ TimeStamp + (char) 13;
            //BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
            //OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
            //osw.write(returnCode);
            //osw.flush();
            //connection.close();

            // send ack that the
            OutputStream os = connection.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            obj = new NetworkObject(messageId);
            oos.writeObject(obj);
            oos.flush();
            connection.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
}
