package fiuba.pyp;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by pablo on 24/03/14.
 */
public class MainRed {

    public static void main( String[] args )
    {

        Runnable runnable = new RemoteOperationHandler(0);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String userName = null;

        //  read the username from the command-line; need to use try/catch with the
        //  readLine() method

        runnable.run();

    }
}
