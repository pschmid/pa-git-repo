package fiuba.pyp;

import java.io.Serializable;

/**
 * Created by pyp on 27/03/14.
 */
public class AdaptedOperation implements Serializable{

    private Operation operation;
    private AddressDomain addressDomain;

    private AddressDomain getAddressDomain() {
        return AddressDomain.getInstance();
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation transformPrimitiveOperation(){
        return getAddressDomain().getPrimitiveOperation(this.operation);
    }

}
