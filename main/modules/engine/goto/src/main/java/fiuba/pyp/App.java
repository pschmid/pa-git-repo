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
        oneSiteReceiveOperations(concurrencyControl, log);
        concurrencyControl.clearHistoryBuffer();
        siteDeletesOperations(concurrencyControl, log);

    }

    public static void oneSiteReceiveOperations(ConcurrencyControl concurrencyControl, Logger log){
        concurrencyControl.setDoc(new DocumentText());

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation h = new Operation(new DocumentCharacter("h"), 0, "INSERT", 1, 2);
        Operation b = new Operation(new DocumentCharacter("b"), 0, "INSERT", 2, 1);
        Operation c = new Operation(new DocumentCharacter("c"), 0, "INSERT", 2, 2);
        Operation d = new Operation(new DocumentCharacter("d"), 2, "INSERT", 1, 5);
        Operation g = new Operation(new DocumentCharacter("g"), 1, "INSERT", 1, 6);
        Operation e = new Operation(new DocumentCharacter("c"), 0, "DELETE", 2, 3);
        Operation f = new Operation(new DocumentCharacter("b"), 0, "DELETE", 2, 4);

        concurrencyControl.run(a);
        concurrencyControl.run(h);
        concurrencyControl.run(b);
        concurrencyControl.run(c);
        concurrencyControl.run(d);
        concurrencyControl.run(g);
        concurrencyControl.run(e);
        concurrencyControl.run(f);

        log.info(concurrencyControl.getDoc().toString());
        concurrencyControl.printHistoryBuffer();
        log.info(concurrencyControl.getDoc().toString().equals("hgad"));

    }

    public static void siteDeletesOperations(ConcurrencyControl concurrencyControl, Logger log){
        concurrencyControl.setDoc(new DocumentText());

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 0, "INSERT", 2, 1);
        Operation c = new Operation(new DocumentCharacter("c"), 0, "INSERT", 2, 2);
        Operation d = new Operation(new DocumentCharacter("c"), 1, "DELETE", 1, 4);
        Operation g = new Operation(new DocumentCharacter("g"), 1, "INSERT", 1, 5);
        Operation e = new Operation(new DocumentCharacter("b"), 1, "DELETE", 2, 3);
        Operation f = new Operation(new DocumentCharacter("c"), 0, "DELETE", 2, 4);
        Operation j = new Operation(new DocumentCharacter("j"), 2, "INSERT", 1, 8);
        Operation k = new Operation(new DocumentCharacter("k"), 0, "INSERT", 2, 5);

        concurrencyControl.run(a);
        concurrencyControl.run(b);
        concurrencyControl.run(c);
        concurrencyControl.run(d);
        concurrencyControl.run(g);
        concurrencyControl.run(e);
        concurrencyControl.run(f);
        concurrencyControl.run(j);
        concurrencyControl.run(k);

        log.info(concurrencyControl.getDoc().toString());
        concurrencyControl.printHistoryBuffer();
        log.info(concurrencyControl.getDoc().toString().equals("agjk"));

    }

}
