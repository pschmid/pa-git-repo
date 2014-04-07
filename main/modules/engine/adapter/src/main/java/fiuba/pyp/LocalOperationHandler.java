package fiuba.pyp;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablo on 09/03/14.
 */
public class LocalOperationHandler {

    private List<AdaptedOperation> adaptedOperations;

    public LocalOperationHandler() {
        this.adaptedOperations = new ArrayList<AdaptedOperation>();
    }

    public List<AdaptedOperation> getAdaptedOperations() {
        return adaptedOperations;
    }


    public synchronized Operation executeLocalOperation(AdaptedOperation adaptedOperation){
        return adaptedOperation.transformToPrimitiveOperation();
    }

    @Nullable
    public synchronized AdaptedOperation getNextAdaptedOperation() {

        if (adaptedOperations.isEmpty()){
            return null;
        }
        else{
            return this.adaptedOperations.get(0);
        }

    }

    @Nullable
    public synchronized AdaptedOperation transformToAdaptedOperation(Operation operation){
        return null;
    }

    public void run(AdaptedOperation adaptedOperation){

    }

}
