/**
 * 
 */
package fiuba.pyp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author pyp
 *
 */
public class HistoryBuffer implements Serializable {
	
	private ArrayList<Operation> buffer;

	/**
	 * Creates the History Buffer
	 */
	public HistoryBuffer() {
		super();
		this.buffer = new ArrayList<Operation>();
	}

	/**
	 * @param buffer
	 */
	public HistoryBuffer(ArrayList<Operation> buffer) {
		super();
		this.buffer = buffer;
	}

	/**
	 * @return the buffer
	 */
	public ArrayList<Operation> getBuffer() {
		return buffer;
	}

	/**
	 * @param buffer the buffer to set
	 */
	public void setBuffer(ArrayList<Operation> buffer) {
		this.buffer = buffer;
	}

	public synchronized void add(Operation op){
		buffer.add(op);
	}
	
	public synchronized void remove(Operation op){
		buffer.remove(op);
	}

    public int getBufferSize() {
        return buffer.size();
    }
}
