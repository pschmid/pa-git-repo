/**
 * 
 */
package fiuba.pyp;

/**
 * @author pyp
 *
 */
public class DocumentText extends Document{

	private String doc;
	
	/* 
	 * @see fiuba.pyp.Document#addObject(fiuba.pyp.DocumentObject, int, int)
	 */
	@Override
	public void addObject(DocumentObject obj, int position, int id) {
		// TODO Auto-generated method stub
		String end = doc.substring(position);
		String result = doc.substring(0, position);
		result.concat(obj.toString()).concat(end);
		this.doc = result;
	}

	/* 
	 * @see fiuba.pyp.Document#removeObject(fiuba.pyp.DocumentObject, int, int)
	 */
	@Override
	public void removeObject(DocumentObject obj, int position, int id) {
		// TODO Auto-generated method stub
		String end = doc.substring(obj.getLength() + position);
		String result = doc.substring(0, position);
		result.concat(end);
		this.doc = result;
	}

}
