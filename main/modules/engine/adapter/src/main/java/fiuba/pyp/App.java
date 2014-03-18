package fiuba.pyp;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Hello world!
 *
 */
public class App
{


    public static void main( String[] args )
    {
        PropertyConfigurator.configure(App.class.getClassLoader().getResource(
                "log4j.properties"));
        Logger log = Logger.getLogger(App.class);
        log.info("Hello World! Pyp");

    }
}