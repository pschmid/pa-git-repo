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

	public synchronized void run(Operation op){
		algorithm.run(op, this.doc);
	}
	
}