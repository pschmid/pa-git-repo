package fiuba.pyp;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * Created by pablo on 24/03/14.
 */
public class RemoteConnection implements Runnable {

    private Socket  connection;
    private int id;

        public RemoteConnection(Socket connection, int i) {
            this.connection = connection;
            this.id = i;
        }


    @Override
    public void run() {

        //while(true)
        try{

            BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
            InputStreamReader isr = new InputStreamReader(is);
            int character;
            StringBuffer process = new StringBuffer();
            while((character = isr.read()) != 13) {
                process.append((char)character);
            }
            System.out.println(process);
            //need to wait 10 seconds to pretend that we're processing something

            String TimeStamp = new Date().toString();
            String returnCode = "MultipleSocketServer repsonded at "+ TimeStamp + (char) 13;
            BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
            OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
            osw.write(returnCode);
            osw.flush();
            connection.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
}
