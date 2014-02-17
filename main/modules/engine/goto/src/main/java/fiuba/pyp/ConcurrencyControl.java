/**
 * 
 */
package fiuba.pyp;

import org.apache.log4j.Logger;

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

	public synchronized void run(Operation operation){
		Operation transOp = algorithm.run(operation,this.buffer);
		execute(transOp);
	}

    public synchronized void execute(Operation transOp){
        DocumentObject obj = transOp.getObj();
        int position = transOp.getPosition();
        int id = transOp.getId();


        this.buffer.add(transOp);

        if (!transOp.isIdentity()){
            if(transOp.isInsert()){
                doc.addObject(obj, position, id);
            }
            else if (transOp.isDelete()){
                transOp.print("A VER ");
                doc.removeObject(obj, position, id);
            }
        }
    }

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
	
}
