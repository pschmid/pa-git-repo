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
		if (op1.isInsert() && op2.isInsert()){
			return IT_InsertInsert(op1, op2);
		}
		else if (op1.isInsert() && op2.isDelete()){
			return IT_InsertDelete(op1, op2);
		}
		else if (op1.isDelete() && op2.isInsert()){
			return IT_DeleteInsert(op1, op2);
		}
		else if (op1.isDelete() && op2.isDelete()){
			return IT_DeleteDelete(op1, op2);
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
//        Logger log = Logger.getLogger(App.class);
//        log.info(op1.getId() + "-" + op1.getPosition()+ "-" + op1.getTimeStamp() );
//        log.info(op2.getId() + "-" + op2.getPosition()+  "-" +op2.getTimeStamp() );
		if (op1.getPosition() < op2.getPosition())
			return op1;
        else if (op1.getPosition() == op2.getPosition() && op1.getId() < op2.getId()) {
            return op1;
        }
		else{
			Operation opAux = op1;
			opAux.setPosition(op1.getPosition() + op2.getObj().getLength());
			return opAux;
		}
	}
	
	public Operation IT_InsertDelete(Operation op1, Operation op2) {
		if (op1.getPosition() <= op2.getPosition())
			return op1;
		else{
			Operation opAux = op1;
			opAux.setPosition(op1.getPosition() - op2.getObj().getLength());
			return opAux;
		}
		/* String-wise
		else if (op1.getPosition() > op2.getPosition() + op2.getObj().getLength()){
			Operation opAux = op1;
			opAux.setPosition(op1.getPosition() - op2.getObj().getLength());
			return opAux;
		}
		else{
			Operation opAux = op1;
			opAux.setPosition(op2.getPosition());
			return opAux;
		}*/
	}
	
	public Operation IT_DeleteInsert(Operation op1, Operation op2) {
		if (op1.getPosition() < op2.getPosition())
			return op1;
		else{
			Operation opAux = op1;
			opAux.setPosition(op1.getPosition() + op2.getObj().getLength());
			return opAux;
		}	
		/*	String-Wise
		 * 
		 * if (op2.getPosition() >= op1.getPosition() + op1.getObj().getLength())
			return op1;
		else if (op1.getPosition() >= op2.getPosition()){
			Operation opAux = op1;
			opAux.setPosition(op1.getPosition() + op2.getObj().getLength());
			return opAux;
		}
		else{
			return op1;
		}*/
	}
	
	public Operation IT_DeleteDelete(Operation op1, Operation op2) {
		if (op1.getPosition() < op2.getPosition())
			return op1;
		else if (op1.getPosition() > op2.getPosition()){
			Operation opAux = op1;
			opAux.setPosition(op1.getPosition() - op2.getObj().getLength());
			return opAux;
		}
		else{
			return null;
		}
		/*	String-Wise
		 * 
		if (op2.getPosition() >= op1.getPosition() + op1.getObj().getLength())
			return op1;
		else if (op1.getPosition() >= op2.getPosition() + op2.getObj().getLength()){
			//Delete (L(op1, P(op1) - L(op1))
			Operation opAux = op1;
			opAux.setPosition(op1.getPosition() - op2.getObj().getLength());
			return opAux;
		}
		else{
			if (op2.getPosition() <= op1.getPosition() && (op1.getPosition() + op1.getObj().getLength() <= op2.getPosition() + op2.getObj().getLength()))
				//Delete (0, L(op1))
				return null;
			else if (op2.getPosition() <= op1.getPosition() && (op1.getPosition() + op1.getObj().getLength() > op2.getPosition() + op2.getObj().getLength())){
				//Delete (P(op1) + L(op1) - (P(op2) + L(op2)), P(op2))
				Operation opAux = op1;
				opAux.setPosition(op2.getPosition());
				return opAux;
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
