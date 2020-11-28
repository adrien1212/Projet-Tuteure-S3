/*
 * Souligne.java                                                  21 mars 2020
 * IUT info 2019 - 2020 
 */
package balise.type;

import balise.Balise;
import balise.Contenu;

/**
 * Souligne le texte d'un diaporama
 * @author groupe 8
 */
public class Souligne extends Balise {

    /** Syntaxe de la balise ouvrante */
    public static final String SYNT_OUVRANTE = "<souligne>";
    
    /** Syntaxe de la balise fermante */
    public static final String SYNT_FERMANTE = "</souligne>";
    
    /* Ajoute les syntaxes des balises dans la collection BALISE */
    static {
        BALISE.put(SYNT_OUVRANTE, SYNT_FERMANTE);
    }
    
    
    /**
     * Constructeur par defaut
     */
    public Souligne() {
        super();
    }
    
    /**
     * Construit une balise gras avec son contenu
     * @param contenu contenu a associer
     */
    public Souligne(Contenu contenu) {
        super(contenu);
    }

    @Override
    public String getSyntOuvrante() {
        return SYNT_OUVRANTE;
    }
    
    @Override
    public void appliquerModif() {
        contenu.setChaine("<text:span text:style-name=\"SOULIGNE\">" + contenu + "</text:span>");
    }
    
    @Override
    public Balise creerBalise(Contenu contenu) {
        return new Souligne(contenu);
    }
}
