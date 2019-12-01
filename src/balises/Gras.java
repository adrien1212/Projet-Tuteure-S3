package balises;

public class Gras implements Balise {

	public static final String SYNTAXE_OUVERTURE = "<gras>";

	public static final String SYNTAXE_FERMETURE = "</gras>";

	private String contenu;
	
	private String SYNTAXE_STYLE = 
			"<style:style style:name=\"GRAS\" style:family=\"text\">"
			+    "<style:text-properties style:font-weight-complex=\"bold\" style:font-weight-asian=\"bold\" fo:font-weight=\"bold\"/>"
			+ "</style:style>";
	
	public Gras() {
	}
	
	public Gras(String contenuBalise) {
		this.contenu = contenuBalise;
	}

	@Override
	public void appliquerModif() {
		
	}

	@Override
	public Balise creerBalise(String contenuBalise) {
		Balise b = new Gras(contenuBalise);
		return b;
	}

    public String toString() {
        return contenu;
    }
	
}
