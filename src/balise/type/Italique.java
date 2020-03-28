/*
 * Italique.java                                                  7 mars 2020
 * IUT info 2019-2020
 */
package balise.type;

import balise.Balise;
import balise.Contenu;

/**
 * Met en italique le texte d'un diaporama
 * @author groupe 8
 */
public class Italique extends Balise {

    /** Syntaxe de la balise ouvrante */
    public static final String SYNT_OUVRANTE = "<italique>";

    /** Syntaxe de la balise fermante */
    public static final String SYNT_FERMANTE = "</italique>";

    /* Ajoute les syntaxes des balises dans la collection BALISE */
    static {
        BALISE.put(SYNT_OUVRANTE, SYNT_FERMANTE);
    }


    /**
     * Constructeur par defaut
     */
    public Italique() {
        super();
    }

    /**
     * Construit une balise cacher avec son contenu
     * @param contenu contenu a associer
     */
    public Italique(Contenu contenu) {
        super(contenu);
    }
    
    @Override
    public String getSyntOuvrante() {
        return SYNT_OUVRANTE;
    }

    @Override
    public void appliquerModif() {
        contenu.setChaine("<text:span text:style-name=\"ITALIQUE\">" + contenu + "</text:span>");
    }
    
    @Override
    public Balise creerBalise(Contenu contenu) {
        return new Italique(contenu);
    }
}
