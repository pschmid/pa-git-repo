package fiuba.pyp;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import rice.p2p.commonapi.Id;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Created by pablo on 02/04/14.
 */
public class OperationManager {

    PriorityQueue<Operation> queue;

    public OperationManager(Id localId) {
        this.queue = new PriorityQueue<Operation>(10,new OperationComparator(localId));

    }
    public void addOperation(@NotNull Operation op) {
        queue.add(op);
    }

    @Nullable
    public Operation getNextOperation() {
        return queue.poll();
    }

    public void printQueue() {
        for(Operation op: queue){
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
