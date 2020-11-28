/*
 * Gras.java                                                  1 dec. 2019
 * IUT info2 2019-2020 groupe 8
 */
package balises.types;

import balises.Balise;

/**
 * Permet de mettre en gras le texte d'un diaporama
 * @author groupe 8
 */
public class Gras implements Balise {

    /** Syntaxe de la balise ouverte gras */
    public static final String SYNTAXE_OUVERTURE = "<gras>";

    /** Syntaxe de la balise fermante gras */
    public static final String SYNTAXE_FERMETURE = "</gras>";

    /** Style a applique au texte pour mettre en gras */
    public static final String SYNTAXE_STYLE = 
                      "<style:style style:name=\"GRAS\" style:family=\"text\">"
                    +    "<style:text-properties style:font-weight-complex=\"bold\" style:font-weight-asian=\"bold\" fo:font-weight=\"bold\"/>"
                    + "</style:style>";
    
    /** Texte contenu entre les balises */
    private String contenu;

    /**
     * Construit la balise gras avec le contenu en parametre
     * @param contenuBalise
     */
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
