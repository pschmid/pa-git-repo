/**
 * 
 */
package fiuba.pyp;

import java.io.Serializable;

/**
 * @author pyp
 *
 * Represents the Objects added to the Document
 *
 */
public abstract class DocumentObject implements Serializable{
	
	public abstract int getLength();
    public abstract String getObj();
	
}
