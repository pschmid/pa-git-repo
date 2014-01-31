/**
 * 
 */
package fiuba.pyp;

import java.util.List;

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

    public abstract Operation transform(Operation op1, List<Operation> operationList);

}
