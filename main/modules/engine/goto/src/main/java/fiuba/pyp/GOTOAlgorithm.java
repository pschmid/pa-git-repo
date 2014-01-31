/**
 * 
 */
package fiuba.pyp;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        ArrayList<Operation> operationBuffer = historyBuffer.getBuffer();
		for (Operation opBuffer : operationBuffer){
            timestamp+=1;
			System.out.print(opBuffer.toString());
            if(opBuffer.isIndependent(operation)){
                for(int i=timestamp + 1;i<=operationBuffer.size();i++){
                    if(operationBuffer.get(i).isCausallyPreceding(operation)) {
                        //return the causally precedings with its position in the original historybuffer
                        Map<Integer,Operation> causallyPrecedingOperation = getCausallyPrecedingOperations(operationBuffer, i);
                        int j = 0;
                        for(Integer key:causallyPrecedingOperation.keySet()) {
                            j++;
                            lTranspose(operationBuffer, timestamp + j - 1,key );
                        }
                        int initialIdx = timestamp + causallyPrecedingOperation.size();
                        int finalIdx = operationBuffer.size();
                        ArrayList<Operation> middleOperations = getMiddleOperations(operationBuffer, initialIdx,finalIdx);
                        return it.transform(operation, middleOperations);

                    }else {
                        ArrayList<Operation> tailOperations = getTailOperations(operationBuffer, i);
                        return it.transform(operation, tailOperations);
                    }
                }
            }
            else
                return operation;

		}
        historyBuffer.add(operation);
		return operation;
	}

    private ArrayList<Operation> getMiddleOperations(ArrayList<Operation> operationBuffer, int initialIdx, int finalIdx) {
        return null;
    }

    private ArrayList<Operation> getLTransposeOperations(int i, int j) {
        return null;
    }

    private Map<Integer,Operation> getCausallyPrecedingOperations(ArrayList<Operation> operationBuffer, int i) {
        return null;
    }

    private ArrayList<Operation> getTailOperations(ArrayList<Operation> operationBuffer, int i) {
        return new ArrayList<Operation>();
    }


    public List<Operation> lTranspose(Operation op1, Operation op2) {
        return null;
    }


    public void lTranspose(List<Operation> operations, int initialIdx, int finalIdx) {

    }

}
