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
		if(type == "INSERT"){
			doc.addObject(obj, position, id);
		}
		else if (type == "DELETE"){
			doc.removeObject(obj, position, id);
		}
		
	}
	
}
