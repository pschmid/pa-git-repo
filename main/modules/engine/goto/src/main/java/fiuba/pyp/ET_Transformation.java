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
        Operation opAux = op1.cloneOperation();
        if (!op2.isIdentity())
            opAux.setIdentity(op1.getIdentity());
		if (opAux.isInsert() && op2.isInsert()){
			return ET_InsertInsert(opAux, op2);
		}
		else if (opAux.isInsert() && op2.isDelete()){
			return ET_InsertDelete(opAux, op2);
		}
		else if (opAux.isDelete() && op2.isInsert()){
			return ET_DeleteInsert(opAux, op2);
		}
		else if (opAux.isDelete() && op2.isDelete()){
			return ET_DeleteDelete(opAux, op2);
		}
		return null;
	}

    @Override
    public Operation transform(Operation op1, List<Operation> operationList) {
        return null;
    }

    public Operation ET_InsertInsert(Operation op1, Operation op2) {
        Logger log = Logger.getLogger(App.class);
        if (op1.getPosition() < op2.getPosition())
            return op1;
        else if (op1.getPosition() == op2.getPosition() && op1.getId().compareTo(op2.getId()) < 0) {
            return op1;
        }
        else{
            op1.setPosition(op1.getPosition() - op2.getObj().getLength());
			return op1;
		}
	}
	
	public Operation ET_InsertDelete(Operation op1, Operation op2) {
		if (op1.getPosition() <= op2.getPosition())
			return op1;
		else{

			op1.setPosition(op1.getPosition() + op2.getObj().getLength());
			return op1;
		}

	}
	
	public Operation ET_DeleteInsert(Operation op1, Operation op2) {
        if (op1.getPosition() < op2.getPosition())
            return op1;
        else{
            op1.setPosition(op1.getPosition() - op2.getObj().getLength());
            return op1;
        }

    }
	
	public Operation ET_DeleteDelete(Operation op1, Operation op2) {
		if (op1.getPosition() + op1.getObj().getLength() <= op2.getPosition())
			return op1;
		else if (op1.getPosition() >= op2.getPosition()){
            op1.setPosition(op1.getPosition() + op2.getObj().getLength());
			return op1;
		}
		else{
            op1.setIdentity(true);
			return op1;
		}
	}

}
