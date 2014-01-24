/**
 * 
 */
package fiuba.pyp;

/**
 * @author pyp
 *
 */
public abstract class Transformation {
	
	/**
	 * Transforms 2 Operations into another Operation O'
	 * 
	 * @param op1
	 * @param op2
	 * @return O'
	 */
	public abstract Operation transform(Operation op1, Operation op2);

}
