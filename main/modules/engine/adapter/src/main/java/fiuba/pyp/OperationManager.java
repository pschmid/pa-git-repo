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

    PriorityQueue<Operation> queue;
    List<Operation> operationList;

    //Guarda el proximo numero de operacion que tiene que ejecutar para cada sitio en forma local
    Map<Id, Integer> stateVector;

    public OperationManager(Id localId) {
        this.queue = new PriorityQueue<Operation>(10,new OperationComparator(localId));
        this.operationList = new CopyOnWriteArrayList<Operation>();
        this.stateVector = new HashMap<Id, Integer>();
    }
    public void addOperation(@NotNull Operation op) {
        queue.add(op);
        operationList.add(op);
        if (! stateVector.containsKey(op.getId())){
            stateVector.put(op.getId(), 1);
        }
    }

    @Nullable
    public Operation getNextOperation() {
//        return queue.poll();
        Operation res = null;

        System.out.println(stateVector.toString());
        System.out.println(operationList.toString());

        for(Operation op: operationList){
            if (! stateVector.containsKey(op.getId())){
                stateVector.put(op.getId(), 1);
            }
            if (op.getLocalTimeStamp() == stateVector.get(op.getId())){
                res = op;
                operationList.remove(op);
                stateVector.put(op.getId(), stateVector.get(op.getId()) + 1);
                return res;
            }

        }

        return res;
    }

    public void printQueue() {
        for(Operation op: operationList){
            System.out.println("operacion: "+ op);
        }
    }

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


}
