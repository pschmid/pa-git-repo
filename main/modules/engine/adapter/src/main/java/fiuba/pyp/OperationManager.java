package fiuba.pyp;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import rice.p2p.commonapi.Id;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by pablo on 02/04/14.
 */
public class OperationManager {

//    PriorityQueue<Operation> queue;
    List<Operation> operationList;
    //Guarda el proximo numero de operacion que tiene que ejecutar para cada sitio en forma local
    Map<Id, Integer> stateVector;

    public OperationManager(Id localId) {
//        this.queue = new PriorityQueue<Operation>(10,new OperationComparator(localId));
        //Esta lista es thread-safe
        this.operationList = new CopyOnWriteArrayList<Operation>();
        this.stateVector = new HashMap<Id, Integer>();
    }
    public void addOperation(@NotNull Operation op) {
//        queue.add(op);
        operationList.add(op);
        if (! stateVector.containsKey(op.getId())){
            stateVector.put(op.getId(), 1);
        }
    }

    public HashMap<Id, Integer> cloneHashMap(){
        HashMap<Id, Integer> auxMap = new HashMap<Id, Integer>();
        for(Id key:stateVector.keySet()){
            auxMap.put(key, stateVector.get(key));
        }
        return auxMap;
    }

    @Nullable
    public Operation getNextOperation() {
//        return queue.poll();
        Operation res = null;
//        System.out.println(stateVector.toString());
//        System.out.println(operationList.toString());
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

    private boolean compareStateVectors(Operation operationCompare){
        Id localId = operationCompare.getId();
        for(Id key:operationCompare.getStateVector().keySet()){
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
            System.out.println("operation: "+ op);
        }
    }


    /*
    private class OperationComparator implements Comparator<Operation>
    {
        private final Id localId;


        public OperationComparator(Id localId) {
            this.localId = localId;
        }

        @Override
        public int compare(Operation o1, Operation o2) {

            if(o1.getId() == localId  && o2.getId() == localId){
                if(o1.getTimeStamp() < o2.getTimeStamp()) {
                    return -1;
                }else
                    return 1;
            }

            if(o1.getId() == localId && o2.getId() != localId) {
                return -1;
            }

            if(o1.getId() != localId && o2.getId() == localId) {
                return 1;
            }


            if(o1.getId() != localId && o1.getId() == o2.getId()  &&  o1.getTimeStamp()>o2.getTimeStamp())
                return 1;
            if(o1.getId() != localId && o1.getId() == o2.getId()  &&  o1.getTimeStamp()<o2.getTimeStamp())
                return -1;
            if(o1.getId() != localId && o2.getId()!= localId  & o1.getId() != o2.getId()) {
                Iterator othersiteOperations = o2.getOtherSitesOperations();
                while ( othersiteOperations.hasNext()) {
                    Operation otherSiteOperation = (Operation) othersiteOperations.next();
                    if(otherSiteOperation.getId() == o1.getId() && otherSiteOperation.getTimeStamp() == o1.getTimeStamp()){
                        o2.removeOtherSiteOperation(o1);
                        return -1;
                    }
                }
                return 1;
            }

            return 1;

        }
    }
    */


}
