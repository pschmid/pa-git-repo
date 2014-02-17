package fiuba.pyp;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulo on 17/02/14.
 */
/*@Test
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

    @Test
    public void basicOneSiteReceiveOperation() {
        ConcurrencyControl concurrencyControl = createDocument();

        Operation a = new Operation(new DocumentCharacter("a"), 0, "INSERT", 1, 1);
        Operation b = new Operation(new DocumentCharacter("b"), 0, "INSERT", 2, 1);
        Operation c = new Operation(new DocumentCharacter("c"), 0, "INSERT", 2, 2);
        Operation d = new Operation(new DocumentCharacter("d"), 2, "INSERT", 1, 4);
        Operation g = new Operation(new DocumentCharacter("g"), 1, "INSERT", 1, 5);
        Operation e = new Operation(new DocumentCharacter("c"), 0, "DELETE", 2, 3);
        Operation f = new Operation(new DocumentCharacter("b"), 0, "DELETE", 2, 4);

        List<Operation> ops = new ArrayList<Operation>();
        ops.add(a);
        ops.add(b);
        ops.add(c);
        ops.add(d);
        ops.add(e);
        ops.add(f);
        runOperations(concurrencyControl,ops);
        Assert.assertEquals(concurrencyControl.getDoc().toString(), "agd");
    }
}*/