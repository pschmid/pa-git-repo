/**
 * 
 */
package fiuba.pyp;

import org.apache.log4j.Logger;
import rice.p2p.commonapi.Id;

/**
 *
 * Controls the Concurrency<br>
 * Implements Singleton
 * 
 * @author pyp
 *
 */
public class ConcurrencyControl {

	private static ConcurrencyControl INSTANCE = null;
    private HistoryBuffer buffer;

    // Private constructor suppresses 
    private ConcurrencyControl(){
    	initialize();
    }
 
    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    private synchronized static void createInstance() {
        if (INSTANCE == null) { 
            INSTANCE = new ConcurrencyControl();
        }
    }
 
    public static ConcurrencyControl getInstance() {
        createInstance();
        return INSTANCE;
    }
    
    private void initialize(){
    	this.algorithm = new GOTOAlgorithm();
        this.buffer = new HistoryBuffer();
    }
    
    private Document doc;
	private AlgorithmControl algorithm;
	
	/**
	 * @return the doc
	 */
	public Document getDoc() {
		return doc;
	}

	/**
	 * @param doc the doc to set
	 */
	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public synchronized Operation run(Operation operation){
		return algorithm.run(operation,this.buffer);

		//execute(transOp);
	}

    public synchronized Operation runOperation(Operation operation){
        Operation transOp = algorithm.run(operation,this.buffer);
        this.buffer.add(transOp);
        return transOp;
    }

    /*
    private synchronized void execute(Operation transOp){
        DocumentObject obj = transOp.getObj();
        int position = transOp.getPosition();
        Id id = transOp.getId();


        this.buffer.add(transOp);

        if (!transOp.isIdentity()){
            if(transOp.isInsert()){
                doc.addObject(obj, position);
            }
            else if (transOp.isDelete()){
                doc.removeObject(obj, position);
            }
        }
    }*/

    public void printHistoryBuffer(){
        Logger log = Logger.getLogger(App.class);
        for (Operation op : this.buffer.getBuffer()){
            log.info(op.getType()+ " " + op.getObj().getObj()+ " " + op.getPosition() + " time: "
            + op.getTimeStamp());

        }
    }

    public void clearHistoryBuffer(){
        this.buffer.getBuffer().clear();
    }

    public int getTimeStamp(){
        if (this.buffer.getBuffer().isEmpty()){
            return 1;
        }
        return this.buffer.getBuffer().size() + 1;
    }

    public void setOtherSitesOperations(Operation operation){
        for (Operation op : this.buffer.getBuffer()){
            if (op.getId() != operation.getId()){
                operation.setOtherSitesOperations(op);
            }
        }
    }

}
