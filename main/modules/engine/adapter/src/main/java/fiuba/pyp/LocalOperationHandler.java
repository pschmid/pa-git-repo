package fiuba.pyp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablo on 09/03/14.
 */
public class LocalOperationHandler {

    private List<AdaptedOperation> adaptedOperations;

    public List<Operation> getOperationEvents() {
        return operationEvents;
    }

    public void setOperationEvents(List<Operation> operationEvents) {
        this.operationEvents = operationEvents;
    }

    public void addOperationEvent(Operation operation){
        this.operationEvents.add(operation);
    }

    private List<Operation> operationEvents;

    public LocalOperationHandler() {
        this.adaptedOperations = new ArrayList<AdaptedOperation>();
        this.operationEvents = new ArrayList<Operation>();
    }

    public List<AdaptedOperation> getAdaptedOperations() {
        return adaptedOperations;
    }


    public synchronized Operation executeLocalOperation(AdaptedOperation adaptedOperation){
        return adaptedOperation.transformToPrimitiveOperation();
    }

    public synchronized AdaptedOperation getNextAdaptedOperation() {

        if (adaptedOperations.isEmpty()){
            return null;
        }
        else{
            return this.adaptedOperations.get(0);
        }

    }

    public synchronized AdaptedOperation transformToAdaptedOperation(Operation operation){
        return null;
    }

    public void run(AdaptedOperation adaptedOperation){

    }

}
