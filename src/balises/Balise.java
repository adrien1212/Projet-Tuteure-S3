/*
 * Balise.java                                                  24 nov. 2019
 * IUT info2 2019-2020 groupe 8
 */
package balises;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe parente de toutes les balises
 * @author groupe 8
 */
public interface Balise {
    
    /** Collection contenant toutes les balises */
    public static ArrayList<String> BALISE = initCollection();
    
    /** Syntaxe de la balise ouvrante */
    public static final String SYNTAXE_OUVERTURE = null;
    
    /** Syntaxe de la balise fermante */
    public static final String SYNTAXE_FERMETURE = null;
    
    /** Texte contenu entre les balises */
    public String contenu = null;
    
    
    /**
     * Applique les modifications de la balise sur le fichier XML content.xml
     */
    public void appliquerModif();

    /**
     * TODO commenter le rôle de cette méthode
     * @param contenuBalise
     * @return 
     */
    public abstract Balise creerBalise(String contenuBalise);
    
    /**
     * Initialise la collection avec les balises ouvrante et fermante 
     * @return aRetourne collection contenant les balises
     */
    public static ArrayList<String> initCollection() {
        ArrayList<String> aRetourne = new ArrayList<String>();
        
        String[] balise = {Cacher.SYNTAXE_OUVERTURE, Cacher.SYNTAXE_FERMETURE,
                           Taille.SYNTAXE_OUVERTURE, Taille.SYNTAXE_FERMETURE};
        
        aRetourne.addAll(Arrays.asList(balise));
        return aRetourne;
    }

    /** @return le contenu entre les balise */
    public default String getContenu() {
        return contenu;
    }
    
    /**
     * Verifie si la balise en parametre est valide
     * @param balise chaine a tester
     * @return l'index de la balise, -1 si invalide
     */
    public static int baliseValide(String balise) {
        return BALISE.indexOf(balise);
        
    }
}
