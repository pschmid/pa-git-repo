package fiuba.pyp;

import rice.p2p.commonapi.Id;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by pablo on 02/04/14.
 */
public class OperationManager {

    List<Operation> operationList;
    //Guarda el proximo numero de operacion que tiene que ejecutar para cada sitio en forma local
    Map<Id, Integer> stateVector;
    Id localId;

    public OperationManager(Id localId) {
        //Esta lista es thread-safe
        this.operationList = new CopyOnWriteArrayList<Operation>();
        this.stateVector = new HashMap<Id, Integer>();
        this.localId = localId;
    }
    public void addOperation(Operation op) {
        operationList.add(op);
        if (! stateVector.containsKey(op.getId())){
            stateVector.put(op.getId(), 1);
        }
    }

    //Devuelve copia del HashMap
    public HashMap<Id, Integer> cloneHashMap(){
        HashMap<Id, Integer> auxMap = new HashMap<Id, Integer>();
        for(Id key:stateVector.keySet()){
            auxMap.put(key, stateVector.get(key));
        }
        return auxMap;
    }

    //Devuelve la proxima Operacion Remota que se Encuentre Causally Ready (es decir que pueda ejecutarse)
    public Operation getNextOperation() {
        Operation res = null;
        for(Operation op: operationList){
            if (! stateVector.containsKey(op.getId())){
                stateVector.put(op.getId(), 1);
            }
            //Primera condicion para que este causally ready
            if (op.getLocalTimeStamp() == stateVector.get(op.getId())){
                //Segunda condicion para que este causally ready
                if (compareStateVectors(op)){
                    res = op;
                    operationList.remove(op);
                    stateVector.put(op.getId(), stateVector.get(op.getId()) + 1);
                    return res;
                }
            }
        }
        return res;
    }

    //Busca que no falte ninguna otra operacion remota de otro sitio para que no haya conflictos
    private boolean compareStateVectors(Operation operationCompare){
        for(Id key:operationCompare.getStateVector().keySet()){
            //Primera condicion  ya verifica que sea la siguiente
            if (key != localId){
                if (!stateVector.containsKey(key)){
                    return false;
                }
                else if (stateVector.get(key) < operationCompare.getStateVector().get(key)){
                    return false;
                }
            }
        }
        return true;
    }

    public void printQueue() {
        for(Operation op: operationList){
            //System.out.println("operation: "+ op);
        }
    }


}
