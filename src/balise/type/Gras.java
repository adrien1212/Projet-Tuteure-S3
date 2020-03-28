/*
 * Gras.java                                                  7 mars 2020
 * IUT info 2019 - 2020
 */
package balise.type;

import balise.Balise;
import balise.Contenu;

/**
 * Met en gras le texte d'un diaporama
 * @author groupe 8
 */
public class Gras extends Balise {

    /** Syntaxe de la balise ouvrante */
    public static final String SYNT_OUVRANTE = "<gras>";
    
    /** Syntaxe de la balise fermante */
    public static final String SYNT_FERMANTE = "</gras>";
    
    /* Ajoute les syntaxes des balises dans la collection BALISE */
    static {
        BALISE.put(SYNT_OUVRANTE, SYNT_FERMANTE);
    }
    
    
    /**
     * Constructeur par defaut
     */
    public Gras() {
        super();
    }
    
    /**
     * Construit une balise gras avec son contenu
     * @param contenu contenu a associer
     */
    public Gras(Contenu contenu) {
        super(contenu);
    }

    @Override
    public String getSyntOuvrante() {
        return SYNT_OUVRANTE;
    }
    
    @Override
    public void appliquerModif() {
        contenu.setChaine("<text:span text:style-name=\"GRAS\">" + contenu + "</text:span>");
    }
    
    @Override
    public Balise creerBalise(Contenu contenu) {
        return new Gras(contenu);
    }
}
