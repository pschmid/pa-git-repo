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
	private int id;
	private String type;
	
	public synchronized void execute(Document doc){
		if(this.isInsert()){
			doc.addObject(obj, position, id);
		}
		else if (this.isDelete()){
			doc.removeObject(obj, position, id);
		}
		
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
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	
}
