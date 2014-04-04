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
public class GOTOTest {
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
/*
    @Test()
    public void basicOneSiteInsertion() {

        ConcurrencyControl concurrencyControl = createDocument();
        concurrencyControl.clearHistoryBuffer();
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
        concurrencyControl.clearHistoryBuffer();
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
        concurrencyControl.clearHistoryBuffer();
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
        concurrencyControl.clearHistoryBuffer();
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
        concurrencyControl.clearHistoryBuffer();
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
        concurrencyControl.clearHistoryBuffer();
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
        concurrencyControl.clearHistoryBuffer();
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

    @Test
    public void basicOneSiteReceiveOperation() {
        ConcurrencyControl concurrencyControl = createDocument();
        concurrencyControl.clearHistoryBuffer();
        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 0, "INSERT", 2, 1);
        Operation c = new Operation(new DocumentCharacter("c"), 0, "INSERT", 2, 2);
        Operation d = new Operation(new DocumentCharacter("c"), 1, "DELETE", 1, 4);
        Operation g = new Operation(new DocumentCharacter("g"), 1, "INSERT", 1, 5);
        Operation e = new Operation(new DocumentCharacter("b"), 1, "DELETE", 2, 3);
        Operation f = new Operation(new DocumentCharacter("c"), 0, "DELETE", 2, 4);
        Operation j = new Operation(new DocumentCharacter("j"), 2, "INSERT", 1, 8);
        Operation k = new Operation(new DocumentCharacter("k"), 0, "INSERT", 2, 5);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);
        ops.add(c);
        ops.add(d);
        ops.add(g);
        ops.add(e);
        ops.add(f);
        ops.add(j);
        ops.add(k);
        runOperations(concurrencyControl,ops);
        Assert.assertEquals(concurrencyControl.getDoc().toString(),"agjk");
    }

    @Test
    public void basicOneSiteReceiveOperation2() {
        ConcurrencyControl concurrencyControl = createDocument();
        concurrencyControl.clearHistoryBuffer();
        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 0, "INSERT", 2, 1);
        Operation c = new Operation(new DocumentCharacter("c"), 0, "INSERT", 2, 2);
        Operation g = new Operation(new DocumentCharacter("g"), 1, "INSERT", 1, 4);
        Operation e = new Operation(new DocumentCharacter("b"), 1, "DELETE", 2, 3);
        Operation f = new Operation(new DocumentCharacter("c"), 0, "DELETE", 2, 4);
        Operation j = new Operation(new DocumentCharacter("j"), 2, "INSERT", 1, 7);
        Operation k = new Operation(new DocumentCharacter("k"), 0, "INSERT", 2, 5);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);
        ops.add(c);
        ops.add(g);
        ops.add(e);
        ops.add(f);
        ops.add(j);
        ops.add(k);
        runOperations(concurrencyControl,ops);
        Assert.assertEquals(concurrencyControl.getDoc().toString(),"agjk");
    }

    @Test
    public void basicOneSiteDependentInsertion(){
        ConcurrencyControl concurrencyControl = createDocument();
        concurrencyControl.clearHistoryBuffer();
        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 0, "INSERT", 2, 2);
        b.setOtherSitesOperations(a);
        Operation c = new Operation(new DocumentCharacter("c"), 0, "INSERT", 2, 3);
        c.setOtherSitesOperations(a);
        Operation d = new Operation(new DocumentCharacter("d"), 0, "INSERT", 1, 4);
        Operation e = new Operation(new DocumentCharacter("e"), 0, "INSERT", 2, 5);
        e.setOtherSitesOperations(a);
        e.setOtherSitesOperations(d);
        Operation f = new Operation(new DocumentCharacter("f"), 0, "INSERT", 2, 6);

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
    public void basicOneSiteDependentDeletion(){
        ConcurrencyControl concurrencyControl = createDocument();
        concurrencyControl.clearHistoryBuffer();
        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 0, "INSERT", 2, 2);
        b.setOtherSitesOperations(a);
        Operation c = new Operation(new DocumentCharacter("a"), 1, "DELETE", 2, 3);
        c.setOtherSitesOperations(a);
        Operation d = new Operation(new DocumentCharacter("d"), 0, "INSERT", 1, 4);
        Operation e = new Operation(new DocumentCharacter("b"), 1, "DELETE", 1, 5);
        Operation f = new Operation(new DocumentCharacter("d"), 0, "DELETE", 2, 6);
        f.setOtherSitesOperations(a);
        f.setOtherSitesOperations(d);
        f.setOtherSitesOperations(e);

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
        Este Test No Cumple con los requisitos de la capa Superior a GOTO (Sistema Distribuido)
    @Test
    public void basicOneSiteCausalityPreservation(){
        ConcurrencyControl concurrencyControl = createDocument();
        concurrencyControl.clearHistoryBuffer();


        concurrencyControl.getDoc().setDoc("12");

        //i'm site 3
        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 1, "INSERT", 2, 2);
        b.setOtherSitesOperations(a);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(b);
        ops.add(a);
        runOperations(concurrencyControl,ops);

        Assert.assertEquals(concurrencyControl.getDoc().toString(), "ab12");
    }*/
/*
    @Test
    public void basicOneSiteConvergence(){
        ConcurrencyControl concurrencyControl = createDocument();
        concurrencyControl.clearHistoryBuffer();

        concurrencyControl.getDoc().setDoc("12");
        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 2, "INSERT", 2, 1);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);
        runOperations(concurrencyControl,ops);

        Assert.assertEquals(concurrencyControl.getDoc().toString(), "a12b");


        concurrencyControl.clearHistoryBuffer();

        concurrencyControl.getDoc().setDoc("12");
        List<Operation> ops2 = new ArrayList<Operation>();
        ops2.add(b);
        ops2.add(a);
        runOperations(concurrencyControl,ops2);
        Assert.assertEquals(concurrencyControl.getDoc().toString(), "a12b");

    }

    @Test
    public void basicOneSiteIntentionPreservation(){
        ConcurrencyControl concurrencyControl = createDocument();
        concurrencyControl.clearHistoryBuffer();


        concurrencyControl.getDoc().setDoc("ABCDE");
        Operation a = new Operation(new DocumentCharacter("1"), 1, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("B"), 1, "DELETE", 2, 1);


        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);


        runOperations(concurrencyControl,ops);

        Assert.assertEquals(concurrencyControl.getDoc().toString(), "A1CDE");

        concurrencyControl.clearHistoryBuffer();

        concurrencyControl.getDoc().setDoc("ABCDE");
        List<Operation> ops2 = new ArrayList<Operation>();
        ops2.add(b);
        ops2.add(a);
        runOperations(concurrencyControl,ops2);
        Assert.assertEquals(concurrencyControl.getDoc().toString(), "A1CDE");
    }
    */


}
