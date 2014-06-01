package fiuba.pyp;

import java.io.Serializable;

/**
 * Created by pyp on 27/03/14.
 *
 * This Class will Handle the Transformation between
 * Application Operations and Engine Operations
 *
 */
public class AdaptedOperation implements Serializable{

    private Operation operation;

    private AddressDomain getAddressDomain() {
        return AddressDomain.getInstance();
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation transformToPrimitiveOperation(){
        return this.operation;
    }

}
