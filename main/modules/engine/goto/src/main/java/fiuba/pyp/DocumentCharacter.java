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
	
	/**
	 * @return the character
	 */
	public String getCharacter() {
		return character;
	}

	/**
	 * @param character the character to set
	 */
	public void setCharacter(String character) {
		this.character = character;
	}

	/* (non-Javadoc)
	 * @see fiuba.pyp.DocumentObject#getLength()
	 */
	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return character.length();
	}
	
	

}
