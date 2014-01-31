/**
 * 
 */
package fiuba.pyp;

import fiuba.pyp.Document;

/**
 * @author pyp
 *
 */
public class Operation {
	
	private DocumentObject obj;
	private int position;
	private String type;
    private int userId;
    private int timeStamp;

    public Operation(DocumentObject object, int pos, String type,int userId, int timeStamp) {
        this.obj = object;
        this.position = pos;
        this.type = type;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }

	public boolean isInsert(){
		return this.type.equals("INSERT");
	}
	
	public boolean isDelete(){
		return this.type.equals("DELETE");
	}

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
	public int getId() {
		return userId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
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

    public boolean isIndependent(Operation operation) {

        return false;
    }

    public boolean isCausallyPreceding(Operation operation) {
        return false;

    }
}
