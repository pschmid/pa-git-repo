/**
 * 
 */
package fiuba.pyp;

/**
 * @author pyp
 * 
 * Represents the Document used for Collaborative Edition
 *
 */
public abstract class Document {


	/**
	 * Adds a Document Object into the position of the Document by id
	 */
	public abstract void addObject(DocumentObject obj, int position, int id);
	
	/**
	 * Removes a Document Object into the position of the Document by id
	 */
	public abstract void removeObject(DocumentObject obj, int position, int id);


    public abstract void setDoc(String doc);
}
