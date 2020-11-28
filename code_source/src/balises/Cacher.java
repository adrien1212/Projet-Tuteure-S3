package balises;

/**
 * TODO commenter les responsabilités de cette classe
 * @author groupe 8
 */
public class Cacher implements Balise {

    public static final String SYNTAXE_OUVERTURE = "<cacher>";

    public static final String SYNTAXE_FERMETURE = "</cacher>";

    private String contenu;

    
    /**
     * Constructeur par defaut
     */
    public Cacher() {
        this.contenu = null;
    }
    
    /**
     * Construit une balise cacher avec son contenu
     * @param contenu contenu de la balise
     */
    public Cacher(String contenu) {
        this.contenu = contenu;
    }
    
    /**
     * @param contenu contenu de la balise
     */
    public Balise creerBalise(String contenu) {
    	Cacher c = new Cacher(contenu);
    	return c;
    }
    
    /**
     * Application des spécifications de la balise :
     * Le contenu inclut entre <cacher> et </cacher> sera remplacé par des espaces
     */
    public void appliquerModif() {
        StringBuilder texteRemplacement = new StringBuilder();

        for(int i = 0; i < contenu.length(); i++) {
            texteRemplacement.append("a");
        }
        
        contenu = texteRemplacement.toString();
    }

    public String toString() {
        return contenu;
    }
}
