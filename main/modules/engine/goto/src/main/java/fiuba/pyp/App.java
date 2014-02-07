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
        System.out.println("Hello World! Pyp");
        // Configure Log4J
        ConcurrencyControl concurrencyControl = ConcurrencyControl.getInstance();
        concurrencyControl.setDoc(new DocumentText());

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 1, "INSERT", 2, 2);
        b.setOtherSitesOperations(a);
        Operation c = new Operation(new DocumentCharacter("c"), 0, "INSERT", 3, 1);

        Operation d = new Operation(new DocumentCharacter("d"), 2, "INSERT", 1, 2);
        Operation e = new Operation(new DocumentCharacter("e"), 0, "INSERT", 1, 3);

        Operation f = new Operation(new DocumentCharacter("f"), 3, "INSERT", 1, 4);


        concurrencyControl.run(a);
        concurrencyControl.run(b);
//        concurrencyControl.run(c);
        concurrencyControl.run(d);
        concurrencyControl.run(e);

        log.info( "Hello World! Pyp" );
        log.info(concurrencyControl.getDoc().toString());
        
    }
}
