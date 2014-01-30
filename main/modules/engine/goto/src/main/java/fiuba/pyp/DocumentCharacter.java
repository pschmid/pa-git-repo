/**
 * 
 */
package fiuba.pyp;

/**
 * @author pyp
 *
 */
public class DocumentCharacter extends DocumentObject{

	private String character;


    public DocumentCharacter(String character) {
        this.character = character;
    }


    @Override
	public String getObj() {
		return character;
	}

	@Override
	public int getLength() {
		return character.length();
	}
	
	

}
