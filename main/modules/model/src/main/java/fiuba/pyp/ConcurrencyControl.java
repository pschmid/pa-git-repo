/**
 * 
 */
package fiuba.pyp;

/**
 * @author pyp
 *
 */
public class ConcurrencyControl {

	/**
	 * @param doc
	 */
	public ConcurrencyControl(Document doc) {
		super();
		this.doc = doc;
	}

	private Document doc;
	private HistoryBuffer buffer;
	
	public synchronized void sendOperation(Operation op){
		op.execute(doc);
		buffer.add(op);
	}
	
}
