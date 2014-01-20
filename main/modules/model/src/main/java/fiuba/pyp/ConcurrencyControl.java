/**
 * 
 */
package fiuba.pyp;

/**
 * @author pyp
 *
 */
public class ConcurrencyControl {

	private static ConcurrencyControl INSTANCE = null;
	 
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
    
    public void initialize(){
    	this.buffer = new HistoryBuffer();
    }

	private Document doc;
	private HistoryBuffer buffer;
	
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

	public synchronized void sendOperation(Operation op){
		op.execute(doc);
		buffer.add(op);
	}
	
}
