/**
 * 
 */
package fiuba.pyp;

import fiuba.pyp.Document;
import org.apache.log4j.Logger;
import rice.pastry.Id;

import java.io.Serializable;
import java.util.Iterator;

/**
 * @author pyp
 *
 */



public class Operation implements Serializable{
	
	private DocumentObject obj;
	private int position;
	private String type;
    private rice.p2p.commonapi.Id userId;
    private int timeStamp;
    private HistoryBuffer otherSiteOperations;
    private boolean identity;
    private int localTimeStamp=1;

    public Operation(DocumentObject object, int pos, String type, rice.p2p.commonapi.Id userId, int timeStamp) {
        this.obj = object;
        this.position = pos;
        this.type = type;
        this.userId = userId;
        this.timeStamp = timeStamp;
        this.otherSiteOperations = new HistoryBuffer();
        this.identity = false;
    }

    public String toString() {
        return "Operation "+type+ obj.getObj()+"  in position "+position + " in time: " +timeStamp;
    }

    public Iterator<Operation> getOtherSitesOperations(){
        return otherSiteOperations.getBuffer().iterator();
    }

    public void removeOtherSiteOperation(Operation op) {
        otherSiteOperations.getBuffer().remove(op);
    }
    public void setOtherSitesOperations(Operation op){
        otherSiteOperations.add(op);
    }
	public boolean isInsert(){
		return this.type.equals("INSERT");
	}

	public boolean isDelete(){
		return this.type.equals("DELETE");
	}

    public boolean isIdentity(){
        return this.identity==true;
    }

    public void setIdentity(boolean identity){ this.identity = identity;}

    public int getLocalTimeStamp() {
        return localTimeStamp;
    }

    public void setLocalTimeStamp(int localTimeStamp) {
        this.localTimeStamp = localTimeStamp;
    }

    public boolean getIdentity(){ return  identity; }

	/**
	 * @return the obj
	 */
	public DocumentObject getObj() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(DocumentObject obj) {
		this.obj = obj;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the id
	 */
	public rice.p2p.commonapi.Id getId() {
		return userId;
	}

	/**
     * @param id the id to set
     */
	public void setId(rice.p2p.commonapi.Id id) {
		this.userId = id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }


    public void print(String label){
        Logger log = Logger.getLogger(App.class);
        if (this.getObj() == null)
            log.info(label + this.getType() + " " + "IDENTITY");
        else
            log.info(label + this.getType() + " " + this.getObj().getObj() + " " + this.getPosition());
    }

    /*
    *two operations are dependent if
     */
    public boolean isIndependent(Operation operation) {
        if(operation.getId() == this.userId  )
            return false;
        else
        if(executionHappendsBeforeGeneration(operation)){
            return false;
        }else
            return true;

    }

    /*
    *if the arrived operation has this operation in its context then they are dependent
     */
    private boolean executionHappendsBeforeGeneration(Operation operation1) {
        Iterator<Operation> it = operation1.getOtherSitesOperations();
        Operation otherSiteOp;
        while(it.hasNext()){
            otherSiteOp = it.next();
            if(this.userId == otherSiteOp.userId  && this.timeStamp == otherSiteOp.getTimeStamp()){
                return true;
            }
        }
        return false;
    }

    public boolean isCausallyPreceding(Operation operation) {
        if(isIndependent(operation)) {
            return false;
        }
        return true;

    }

    @Override
    public boolean equals(Object obj) {
        Operation operation = (Operation) obj;
        if (this.getId() == operation.getId()  &&
                this.getTimeStamp() == operation.getTimeStamp())
            return true;
        else
            return false;
    }
}
