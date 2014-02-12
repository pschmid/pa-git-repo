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
        // Configure Log4J
        ConcurrencyControl concurrencyControl = ConcurrencyControl.getInstance();
        concurrencyControl.setDoc(new DocumentText());

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 0, "INSERT", 2, 1);
        Operation c = new Operation(new DocumentCharacter("c"), 0, "INSERT", 2, 2);
        Operation d = new Operation(new DocumentCharacter("d"), 2, "INSERT", 1, 2);
        Operation e = new Operation(new DocumentCharacter("c"), 0, "DELETE", 2, 3);
        Operation f = new Operation(new DocumentCharacter("b"), 0, "DELETE", 2, 4);

        concurrencyControl.run(a);
        concurrencyControl.run(b);
        concurrencyControl.run(c);
        concurrencyControl.run(d);
        concurrencyControl.run(e);
//        concurrencyControl.printHistoryBuffer();
//        concurrencyControl.run(f);

        log.info(concurrencyControl.getDoc().toString());
        concurrencyControl.printHistoryBuffer();

    }
}
