/**
 * 
 */
package fiuba.pyp;


/**
 * @author pyp
 *
 */
public class GOTOAlgorithm extends AlgorithmControl{
	
	/**
	 * @ Constructor for GOTO
	 */
	public GOTOAlgorithm() {
		super();
		this.buffer = new HistoryBuffer();
	}

	private HistoryBuffer buffer;

	/* (non-Javadoc)
	 * @see fiuba.pyp.AlgorithmControl#run(fiuba.pyp.Operation, fiuba.pyp.Document)
	 */
	@Override
	public Operation run(Operation operation) {
		// TODO Auto-generated method stub
		for (Operation opBuffer : this.buffer.getBuffer()){
			System.out.print(opBuffer.toString()); 
		}
		this.buffer.add(operation);
		return operation;
	}

}
