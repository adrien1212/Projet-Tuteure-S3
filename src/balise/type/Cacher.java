/*
 * Cacher.java                                                  7 mars 2020
 * IUT info 2019-2020
 */
package balise.type;

import balise.Balise;
import balise.Contenu;

/**
 * Remplace le texte d'un diaporama par des espaces
 * @author groupe 8
 */
public class Cacher extends Balise {

    /** Syntaxe de la balise ouvrante */
    public static final String SYNT_OUVRANTE = "<cacher>";

    /** Syntaxe de la balise fermante */
    public static final String SYNT_FERMANTE = "</cacher>";
    
    /* Ajoute les syntaxes des balises dans la collection BALISE */
    static {
        BALISE.put(SYNT_OUVRANTE, SYNT_FERMANTE);
    }


    /**
     * Constructeur par defaut
     */
    public Cacher() {
        super();
    }

    /**
     * Construit une balise cacher avec son contenu
     * @param contenu contenu a associer
     */
    public Cacher(Contenu contenu) {
        super(contenu);
    }

    @Override
    public String getSyntOuvrante() {
        return SYNT_OUVRANTE;
    }
    
    @Override
    public void appliquerModif() {
        contenu.setChaine("<text:s text:c=\"" + contenu.getChaine().length() + "\"/>");
    }

    @Override
    public Balise creerBalise(Contenu contenu) {
        return new Cacher(contenu);
    }
}
