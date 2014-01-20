/**
 * 
 */
package fiuba.pyp;

import java.util.List;

/**
 * @author pyp
 *
 */
public class HistoryBuffer {
	
	private List<Operation> buffer;
	
	/**
	 * @param buffer
	 */
	public HistoryBuffer(List<Operation> buffer) {
		super();
		this.buffer = buffer;
	}

	/**
	 * @return the buffer
	 */
	public List<Operation> getBuffer() {
		return buffer;
	}

	/**
	 * @param buffer the buffer to set
	 */
	public void setBuffer(List<Operation> buffer) {
		this.buffer = buffer;
	}

	public synchronized void add(Operation op){
		buffer.add(op);
	}
	
	public synchronized void remove(Operation op){
		buffer.remove(op);
	}
	
}
