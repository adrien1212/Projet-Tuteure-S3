package balises;

/**
 * TODO commenter les responsabilit�s de cette classe
 * @author groupe 8
 */
public class Cacher implements Balise {

    /** TODO commenter le r�le du champ (attribut, r�le associatif...) */
    public static final String SYNTAXE_OUVERTURE = "<cacher>";

    /** TODO commenter le r�le du champ (attribut, r�le associatif...) */
    public static final String SYNTAXE_FERMETURE = "</cacher>";

    /** TODO commenter le r�le du champ (attribut, r�le associatif...) */
    private String contenu;

    /**
     * TODO commenter l'�tat initial d�fini
     * @param contenu contenu de la balise
     */
    public Balise creerBalise(String contenu) {
    	Cacher c = new Cacher(contenu);
    	return c;
    }

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
     * Application des sp�cifications de la balise :
     * Le contenu inclut entre <cacher> et </cacher> sera remplac� par des espaces
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
