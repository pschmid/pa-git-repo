/**
 * 
 */
package fiuba.pyp;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author army
 *
 */
public class ET_Transformation extends Transformation {

	/**
	 *  (see) Page 29 Direccionamiento de Datos & OT FAQ <br>
	 *
	 *	Conditions: op2 has already been executed, op1 has not 
	 *  and its parameters are from the state before op2 was executed.
	 *  op1 is executed as if op2 was not, so its excluded
	 *
	 */
	@Override
	public Operation transform(Operation op1, Operation op2) {
		// TODO Auto-generated method stub
		if (op1.isInsert() && op2.isInsert()){
			return ET_InsertInsert(op1, op2);
		}
		else if (op1.isInsert() && op2.isDelete()){
			return ET_InsertDelete(op1, op2);
		}
		else if (op1.isDelete() && op2.isInsert()){
			return ET_DeleteInsert(op1, op2);
		}
		else if (op1.isDelete() && op2.isDelete()){
			return ET_DeleteDelete(op1, op2);
		}
		return null;
	}

    @Override
    public Operation transform(Operation op1, List<Operation> operationList) {
        return null;
    }

    public Operation ET_InsertInsert(Operation op1, Operation op2) {
//        Logger log = Logger.getLogger(App.class);
//        log.info(op1.getId() + "-" + op1.getPosition()+ "-" + op1.getTimeStamp() );
//        log.info(op2.getId() + "-" + op2.getPosition()+  "-" +op2.getTimeStamp() );
        if (op1.getPosition() <= op2.getPosition())
			return op1;
		else{
			Operation opAux = op1;
			opAux.setPosition(op1.getPosition() - op2.getObj().getLength());
			return opAux;
		}
	}
	
	public Operation ET_InsertDelete(Operation op1, Operation op2) {
		if (op1.getPosition() <= op2.getPosition())
			return op1;
		else{
			Operation opAux = op1;
			opAux.setPosition(op1.getPosition() + op2.getObj().getLength());
			return opAux;
		}
	}
	
	public Operation ET_DeleteInsert(Operation op1, Operation op2) {
		if (op1.getPosition() + op1.getObj().getLength() <= op2.getPosition())
			return op1;
		else if (op1.getPosition() >= op2.getPosition() + op2.getObj().getLength()){
			Operation opAux = op1;
			opAux.setPosition(op1.getPosition() - op2.getObj().getLength());
			return opAux;
		}
		else{
			//Delete & Insert in the same Position -> Do Nothing
			//Check
			return null;
		}
		
	}
	
	public Operation ET_DeleteDelete(Operation op1, Operation op2) {
		if (op1.getPosition() + op1.getObj().getLength() <= op2.getPosition())
			return op1;
		else if (op1.getPosition() >= op2.getPosition()){
			Operation opAux = op1;
			opAux.setPosition(op1.getPosition() + op2.getObj().getLength());
			return opAux;
		}
		else{
			//Never reaches this point
			return null;
		}
	}

}
