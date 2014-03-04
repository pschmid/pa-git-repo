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
    public DocumentText() {
        doc = new String();
    }


    @Override
    public String toString() {
        return doc;
    }

    @Override
	public void addObject(DocumentObject obj, int position, int id) {
        // TODO ver el tema cuando es causality preservation que te cae en un 3er sitio una operacion para
        // la siguiente posicion y todavia no te llego la primera.
        //hay que agregar campos vacios al documento plano

        if (position > doc.length()) {
            //ver de usar un vector virtual y volcarlo al string cuando este completo
        }

        String end = doc.substring(position);
        String result = doc.substring(0, position);
        result = result + obj.getObj() + end;
        this.doc = result;
    }

	/* 
	 * @see fiuba.pyp.Document#removeObject(fiuba.pyp.DocumentObject, int, int)
	 */
	@Override
	public void removeObject(DocumentObject obj, int position, int id) {
		// TODO check if we need to check equalities between obj and obj in doc
        if (obj != null){
            System.out.println("DOCUMENT ---- " + doc);
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


}
