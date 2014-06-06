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

    public DocumentText() {
        doc = new String();
    }


    @Override
    public String toString() {
        return doc;
    }

    @Override
	public void addObject(DocumentObject obj, int position) {

        String end = doc.substring(position);
        String result = doc.substring(0, position);
        result = result + obj.getObj() + end;
        this.doc = result;
    }

	@Override
	public void removeObject(DocumentObject obj, int position) {
		// TODO check if we need to check equalities between obj and obj in doc
        if (obj != null){
            String end = doc.substring(obj.getLength() + position);
            String result = doc.substring(0, position);
            result = result + end;
            this.doc = result;
        }
	}

    @Override
    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDoc() {
        return doc;
    }


}
