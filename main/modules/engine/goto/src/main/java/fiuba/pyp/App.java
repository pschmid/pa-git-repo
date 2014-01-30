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
        System.out.println( "Hello World! Pyp" );
        // Configure Log4J
        ConcurrencyControl concurrencyControl = ConcurrencyControl.getInstance();
        concurrencyControl.setDoc(new DocumentText());
        concurrencyControl.run(new Operation(new DocumentCharacter("a"),0,123,"INSERT"));

        log.info( "Hello World! Pyp" );
        log.info(concurrencyControl.getDoc().toString());
    }
}
