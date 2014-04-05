package fiuba.pyp;

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
        return adaptedOperation.transformPrimitiveOperation();
    }

}
