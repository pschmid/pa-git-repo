package fiuba.pyp;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablo on 11/02/14.
 */
@Test
public class HelloWorldTest {
    private ConcurrencyControl createDocument(){
        ConcurrencyControl concurrencyControl = ConcurrencyControl.getInstance();
        concurrencyControl.setDoc(new DocumentText());
        return concurrencyControl;
    }
    private void runOperations(ConcurrencyControl concurrencyControl, List<Operation> opList){
        for (Operation op : opList){
            concurrencyControl.run(op);
        }
    }

    @Test()
    public void basicOneSiteInsertion() {

        ConcurrencyControl concurrencyControl = createDocument();

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 0, "INSERT", 1, 2);
        Operation c = new Operation(new DocumentCharacter("c"), 0, "INSERT", 1, 3);
        Operation d = new Operation(new DocumentCharacter("d"), 0, "INSERT", 1, 4);
        Operation e = new Operation(new DocumentCharacter("e"), 0, "INSERT", 1, 5);
        Operation f = new Operation(new DocumentCharacter("f"), 0, "INSERT", 1, 6);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);
        ops.add(c);
        ops.add(d);
        ops.add(e);
        ops.add(f);
        runOperations(concurrencyControl,ops);
        Assert.assertEquals(concurrencyControl.getDoc().toString(), "fedcba");
    }
    @Test
    public void basicOneSiteInsertion2(){
        ConcurrencyControl concurrencyControl = createDocument();

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 1, "INSERT", 1, 2);
        Operation c = new Operation(new DocumentCharacter("c"), 2, "INSERT", 1, 3);
        Operation d = new Operation(new DocumentCharacter("d"), 0, "INSERT", 1, 4);
        Operation e = new Operation(new DocumentCharacter("e"), 1, "INSERT", 1, 5);
        Operation f = new Operation(new DocumentCharacter("f"), 2, "INSERT", 1, 6);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);
        ops.add(c);
        ops.add(d);
        ops.add(e);
        ops.add(f);
        runOperations(concurrencyControl, ops);

        Assert.assertEquals(concurrencyControl.getDoc().toString(), "defabc");
    }
    @Test
    public void basicOneSiteInsertion3(){
        ConcurrencyControl concurrencyControl = createDocument();

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 1, "INSERT", 1, 2);
        Operation c = new Operation(new DocumentCharacter("c"), 2, "INSERT", 1, 3);
        Operation d = new Operation(new DocumentCharacter("d"), 2, "INSERT", 1, 4);
        Operation e = new Operation(new DocumentCharacter("e"), 1, "INSERT", 1, 5);
        Operation f = new Operation(new DocumentCharacter("f"), 0, "INSERT", 1, 6);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);
        ops.add(c);
        ops.add(d);
        ops.add(e);
        ops.add(f);
        runOperations(concurrencyControl,ops);

        Assert.assertEquals(concurrencyControl.getDoc().toString(), "faebdc");
    }
    @Test
    public void basicOneSiteInsertion4(){
        ConcurrencyControl concurrencyControl = createDocument();

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 1, "INSERT", 1, 2);
        Operation c = new Operation(new DocumentCharacter("c"), 2, "INSERT", 1, 3);
        Operation d = new Operation(new DocumentCharacter("d"), 3, "INSERT", 1, 4);
        Operation e = new Operation(new DocumentCharacter("e"), 3, "INSERT", 1, 5);
        Operation f = new Operation(new DocumentCharacter("f"), 3, "INSERT", 1, 6);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);
        ops.add(c);
        ops.add(d);
        ops.add(e);
        ops.add(f);
        runOperations(concurrencyControl,ops);

        Assert.assertEquals(concurrencyControl.getDoc().toString(), "abcfed");
    }
    @Test
    public void basicOneSiteremove(){
        ConcurrencyControl concurrencyControl = createDocument();

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 1, "INSERT", 1, 2);
        Operation c = new Operation(new DocumentCharacter("c"), 2, "INSERT", 1, 3);
        Operation d = new Operation(new DocumentCharacter("c"), 2, "DELETE", 1, 4);
        Operation e = new Operation(new DocumentCharacter("b"), 1, "DELETE", 1, 5);
        Operation f = new Operation(new DocumentCharacter("a"), 0, "DELETE", 1, 6);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);
        ops.add(c);
        ops.add(d);
        ops.add(e);
        ops.add(f);
        runOperations(concurrencyControl,ops);

        Assert.assertEquals(concurrencyControl.getDoc().toString(), "");
    }

    @Test
    public void basicOneSiteRemove1(){
        ConcurrencyControl concurrencyControl = createDocument();

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 1, "INSERT", 1, 2);
        Operation c = new Operation(new DocumentCharacter("c"), 2, "INSERT", 1, 3);
        Operation d = new Operation(new DocumentCharacter("b"), 1, "DELETE", 1, 4);
        Operation e = new Operation(new DocumentCharacter("c"), 1, "DELETE", 1, 5);
        Operation f = new Operation(new DocumentCharacter("a"), 0, "DELETE", 1, 6);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);
        ops.add(c);
        ops.add(d);
        ops.add(e);
        ops.add(f);
        runOperations(concurrencyControl,ops);

        Assert.assertEquals(concurrencyControl.getDoc().toString(), "");
    }
    @Test
    public void basicOneSiteRemove2(){
        ConcurrencyControl concurrencyControl = createDocument();

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 1, "INSERT", 1, 2);
        Operation c = new Operation(new DocumentCharacter("c"), 2, "INSERT", 1, 3);
        Operation d = new Operation(new DocumentCharacter("a"), 0, "DELETE", 1, 4);
        Operation e = new Operation(new DocumentCharacter("b"), 0, "DELETE", 1, 5);
        Operation f = new Operation(new DocumentCharacter("c"), 0, "DELETE", 1, 6);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);
        ops.add(c);
        ops.add(d);
        ops.add(e);
        ops.add(f);
        runOperations(concurrencyControl,ops);

        Assert.assertEquals(concurrencyControl.getDoc().toString(), "");
    }

    /*
    @Test
    public void basicOneSiteReceiveOperation() {
        ConcurrencyControl concurrencyControl = createDocument();

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 0, "INSERT", 2, 1);
        Operation c = new Operation(new DocumentCharacter("c"), 0, "INSERT", 2, 2);
        Operation d = new Operation(new DocumentCharacter("d"), 2, "INSERT", 1, 2);
        Operation e = new Operation(new DocumentCharacter("g"), 1, "INSERT", 1, 3);
        Operation f = new Operation(new DocumentCharacter("c"), 0, "DELETE", 2, 3);
        Operation g = new Operation(new DocumentCharacter("b"), 0, "DELETE", 2, 4);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);
        ops.add(c);
        ops.add(d);
        ops.add(e);
        ops.add(f);
        ops.add(g);
        runOperations(concurrencyControl,ops);
        Assert.assertEquals(concurrencyControl.getDoc().toString(), "agd");
    }*/
}