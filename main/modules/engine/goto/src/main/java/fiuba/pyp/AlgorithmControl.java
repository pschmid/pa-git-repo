/**
 * 
 */
package fiuba.pyp;

/**
 * 
 * Abstract Class for The Algorithm Control
 * 
 * @author pyp
 * 
 */
public abstract class AlgorithmControl {

	/**
	 * 
	 * Given an Operation returns the Execution form
	 * of the Operation EO, such that DC(EO) = EC(O) 
	 * 
	 * @param op
	 * @return Transformed Operation
	 */
	public abstract Operation run(Operation operation, HistoryBuffer historyBuffer);
	
}
