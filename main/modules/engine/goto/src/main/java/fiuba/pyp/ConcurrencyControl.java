/**
 * 
 */
package fiuba.pyp;

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
        //fdokdsfkjodsfkjfds

        if(transOp.isInsert()){
            doc.addObject(obj, position, id);
        }
        else if (transOp.isDelete()){
            doc.removeObject(obj, position, id);
        }

    }
	
}
