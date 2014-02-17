/**
 * 
 */
package fiuba.pyp;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author pyp
 *
 */
public class IT_Transformation extends Transformation {

	/**
	 *  (see) Page 27 Direccionamiento de Datos & OT FAQ <br>
	 *
	 *	Conditions: op2 has already been executed, op1 has not 
	 *  and its parameters are from the state before op2 was executed
	 *  op1 is executed as if op2 was executed so its included
	 *  
	 */
	@Override
	public Operation transform(Operation op1, Operation op2) {
		// TODO Auto-generated method stub
        Operation opAux = new Operation(op1.getObj(), op1.getPosition(), op1.getType(), op1.getUserId(), op1.getTimeStamp());
        if (!op2.isIdentity())
            opAux.setIdentity(op1.getIdentity());
		if (opAux.isInsert() && op2.isInsert()){
			return IT_InsertInsert(opAux, op2);
		}
		else if (opAux.isInsert() && op2.isDelete()){
			return IT_InsertDelete(opAux, op2);
		}
		else if (opAux.isDelete() && op2.isInsert()){
			return IT_DeleteInsert(opAux, op2);
		}
		else if (opAux.isDelete() && op2.isDelete()){
			return IT_DeleteDelete(opAux, op2);
		}
		return null;
	}

    @Override
    public Operation transform(Operation op1, List<Operation> operationList) {
        if (operationList.isEmpty()){
            return op1;
        }
        else{
            Operation op = transform(transform(op1, operationList.get(0)), operationList.subList(1, operationList.size()));
            return op;
        }
    }

	public Operation IT_InsertInsert(Operation op1, Operation op2) {
        if (op1.getPosition() < op2.getPosition())
			return op1;
        else if (op1.getPosition() == op2.getPosition() && op1.getId() < op2.getId()) {
            return op1;
        }
		else{
			op1.setPosition(op1.getPosition() + op2.getObj().getLength());
			return op1;
		}
	}
	
	public Operation IT_InsertDelete(Operation op1, Operation op2) {
		if (op1.getPosition() <= op2.getPosition())
			return op1;
		else{
            op1.setPosition(op1.getPosition() - op2.getObj().getLength());
			return op1;
		}
		/* String-wise
		else if (op1.getPosition() > op2.getPosition() + op2.getObj().getLength()){
			return op1;
		}
		else{
			return op1;
		}*/
	}
	
	public Operation IT_DeleteInsert(Operation op1, Operation op2) {
		if (op1.getPosition() < op2.getPosition())
			return op1;
		else{
            op1.setPosition(op1.getPosition() + op2.getObj().getLength());
			return op1;
		}	
		/*	String-Wise
		 * 
		 * if (op2.getPosition() >= op1.getPosition() + op1.getObj().getLength())
			return op1;
		else if (op1.getPosition() >= op2.getPosition()){
			return op1;
		}
		else{
			return op1;
		}*/
	}
	
	public Operation IT_DeleteDelete(Operation op1, Operation op2) {
		if (op1.getPosition() < op2.getPosition())
			return op1;
		else if (op1.getPosition() > op2.getPosition()){
            op1.setPosition(op1.getPosition() - op2.getObj().getLength());
            return op1;
		}
		else{
            op1.setIdentity(true);
			return op1;
		}
		/*	String-Wise
		 * 
		if (op2.getPosition() >= op1.getPosition() + op1.getObj().getLength())
			return op1;
		else if (op1.getPosition() >= op2.getPosition() + op2.getObj().getLength()){
			//Delete (L(op1, P(op1) - L(op1))
			return op1;
		}
		else{
			if (op2.getPosition() <= op1.getPosition() && (op1.getPosition() + op1.getObj().getLength() <= op2.getPosition() + op2.getObj().getLength()))
				//Delete (0, L(op1))
				return null;
			else if (op2.getPosition() <= op1.getPosition() && (op1.getPosition() + op1.getObj().getLength() > op2.getPosition() + op2.getObj().getLength())){
				//Delete (P(op1) + L(op1) - (P(op2) + L(op2)), P(op2))
				return op1;
			}
			else if (op2.getPosition() > op1.getPosition() && (op1.getPosition() + op1.getObj().getLength() <= op2.getPosition() + op2.getObj().getLength())){
				//Delete (P(op2) - P(op1), P(op1))
				return op1;
			}
			else{
				//Delete (L(op1) - L(op2), P(op1))
				return op1;
			}
		}*/
	}
	
}
