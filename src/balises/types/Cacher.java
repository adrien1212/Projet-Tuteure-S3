/*
 * Cacher.java                                                  24 nov. 2019
 * IUT info2 2019-2020 groupe 8
 */
package balises.types;

import balises.Balise;

/**
 * Permet de remplacer le texte d'un diaporama en espace
 * @author groupe 8
 */
public class Cacher implements Balise {

    /** Syntaxe de la balise ouvrante cacher */
    public static final String SYNTAXE_OUVERTURE = "<cacher>";

    /** Syntaxe de la balise fermante cacher */
    public static final String SYNTAXE_FERMETURE = "</cacher>";

    /** Texte contenu entre les balises */
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
        contenu = "<text:s text:c=\"" + contenu.length() + "\"/>";
    }

    public String toString() {
        return contenu;
    }
}
