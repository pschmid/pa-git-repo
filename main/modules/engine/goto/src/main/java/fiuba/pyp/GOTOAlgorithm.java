/**
 * 
 */
package fiuba.pyp;


import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author pyp
 *
 */
public class GOTOAlgorithm extends AlgorithmControl{

    private final ET_Transformation et;
    private final IT_Transformation it;

    /**
	 * @ Constructor for GOTO
	 */
	public GOTOAlgorithm() {
        this.et = new ET_Transformation();
        this.it = new IT_Transformation();
    }


	/* (non-Javadoc)
	 * @see fiuba.pyp.AlgorithmControl#run(fiuba.pyp.Operation, fiuba.pyp.Document)
	 */
	@Override
	public Operation run(Operation operation, HistoryBuffer historyBuffer) {

        int timestamp = 0;
        Logger log = Logger.getLogger(App.class);
        ArrayList<Operation> operationBuffer = historyBuffer.getBuffer();
		for (Operation opBuffer : operationBuffer){
            timestamp+=1;

            if(opBuffer.isIndependent(operation)){

                for(int i=timestamp +1;i<=operationBuffer.size();i++){
                    if(operationBuffer.get(i-1).isCausallyPreceding(operation)) {
                        log.info(operationBuffer.get(i-1).getObj().getObj() + "from "+ operationBuffer.get(i-1).getId() + " is causally preceding "+
                                operation.getObj().getObj() + "from "+ operation.getId());
                        //returns the causally precedings with its position in the original historybuffer
                        Map<Integer,Operation> causallyPrecedingOperations = getCausallyPrecedingOperations(operationBuffer, i-1,operation);
                        int j = 0;

                        ArrayList<Operation> opBufferTransposed = new ArrayList<Operation>();
                        opBufferTransposed.addAll(operationBuffer);

                        for(Integer key:causallyPrecedingOperations.keySet()) {
                            j++;
                            lTranspose(getTransposeBuffer(opBufferTransposed, timestamp + j - 2, key), opBufferTransposed, key);
                        }
                        int initialIdx = timestamp + causallyPrecedingOperations.size() -1;
                        int finalIdx = opBufferTransposed.size()-1;
                        ArrayList<Operation> middleOperations = getMiddleOperations(opBufferTransposed, initialIdx,finalIdx);
                        return it.transform(operation, middleOperations);

                    }/*else {
                        log.info(operationBuffer.get(i-1).getObj().getObj() + "from "+ operationBuffer.get(i-1).getId() + " is not causally preceding "+
                                operation.getObj().getObj() + "from "+ opBuffer.getId());
                        ArrayList<Operation> tailOperations = getTailOperations(operationBuffer, i-1);
                        return it.transform(operation, tailOperations);
                    }*/
                }
                log.info("independent operation. Adding " + operation.getObj().getObj() );
                ArrayList<Operation> tailOperations = getTailOperations(operationBuffer, timestamp-1);
                return it.transform(operation, tailOperations);

            }
            else{
                log.info(opBuffer.getObj().getObj() + "from " + opBuffer.getId() + " is dependent " +
                        operation.getObj().getObj() + "from " + opBuffer.getId());
                return operation;
            }

		}
        if(operationBuffer.isEmpty())
            log.info("not operation in history buffer. Adding " + operation.getObj().getObj() );
        //historyBuffer.add(operation);
		return operation;
	}

    private ArrayList<Operation> getTransposeBuffer(ArrayList<Operation> operationBuffer, int i, Integer key) {

        return getMiddleOperations(operationBuffer, i, key);

    }

    private ArrayList<Operation> getMiddleOperations(ArrayList<Operation> operationBuffer, int initialIdx, int finalIdx) {
        ArrayList<Operation> middleOperations = new ArrayList<Operation>();
        for(int i= initialIdx ; i<= finalIdx ; i++ ) {
            middleOperations.add(operationBuffer.get(i));
        }
        return middleOperations;
    }

    private ArrayList<Operation> getLTransposeOperations(int i, int j) {
        return null;
    }

    /*
    *return the causally precedings with its position in the original historybuffer
     */
    private Map<Integer,Operation> getCausallyPrecedingOperations(ArrayList<Operation> operationBuffer, int i, Operation operation) {

        Map<Integer, Operation> causallyPrecedingOperations = new HashMap<Integer, Operation>();
        List<Operation> lastOperations = new ArrayList<Operation>();

        int j = i;
        for(int z = i ; z<operationBuffer.size(); z++) {
            lastOperations.add(operationBuffer.get(z));
        }
        for (Operation op : lastOperations) {
            if (op.isCausallyPreceding(operation))
                causallyPrecedingOperations.put(j, op);
            j++;
        }
        return causallyPrecedingOperations;
    }

    private ArrayList<Operation> getTailOperations(ArrayList<Operation> operationBuffer, int i) {
        ArrayList<Operation> tailOperations = new ArrayList<Operation>();
        if (i <= operationBuffer.size()){
            tailOperations.addAll(i, operationBuffer);}
        return tailOperations;
    }


    public List<Operation> lTranspose(Operation op1, Operation op2) {
        List<Operation> tranformedOperations = new ArrayList<Operation>();
        op1.print("before transpose1 ");
        op2.print("before transpose2 ");
        Operation op2Prime = et.transform(op2, op1);
        Operation op1Prime = it.transform(op1, op2Prime);
        op1Prime.print("after transpose1 ");
        op2Prime.print("after transpose2 ");
        tranformedOperations.add(op2Prime);
        tranformedOperations.add(op1Prime);
        return tranformedOperations;
    }


    public void lTranspose(List<Operation> operations, List<Operation> operationsTrans, int posj) {

        List<Operation> twoOperations;
        for (int i = operations.size() -1; i > 0; i--) {
            twoOperations = lTranspose(operations.get(i - 1), operations.get(i));
            operations.set(i - 1, twoOperations.get(0));
            operations.set(i, twoOperations.get(1));
            operationsTrans.set(posj-1, twoOperations.get(0));
            operationsTrans.set(posj, twoOperations.get(1));
            posj--;
        }


    }

}
