/*
 * Balise.java                                                  24 nov. 2019
 * IUT info2 2019-2020 groupe 8
 */
package balises;

import java.util.ArrayList;
import java.util.Arrays;

import balises.types.*;

/**
 * Classe parente de toutes les balises
 * @author groupe 8
 */
public interface Balise {
    
    /** Collection contenant toutes les balises */
    public static ArrayList<String> BALISE = initCollection();
    
    /** Collection contenant toutes les balises ouvrantes */
    public static ArrayList<String> BALISE_OUVRANTE = initCollectionBaliseOuvrante();
    
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
     * Construit une balise avec son contenu
     * @param contenuBalise écrit dans entre la balise
     * @return la balise
     */
    public abstract Balise creerBalise(String contenuBalise);
    
    /**
     * Initialise la collection avec les balises ouvrante et fermante 
     * @return aRetourne collection contenant les balises
     */
    public static ArrayList<String> initCollection() {
        ArrayList<String> aRetourne = new ArrayList<String>();
        
        /* Ensemble des balises */
        String[] balise = {Cacher.SYNTAXE_OUVERTURE, Cacher.SYNTAXE_FERMETURE,
                           Taille.SYNTAXE_OUVERTURE, Taille.SYNTAXE_FERMETURE,
                           Gras.SYNTAXE_OUVERTURE, Gras.SYNTAXE_FERMETURE
                           };
        
        aRetourne.addAll(Arrays.asList(balise));
        return aRetourne;
    }
    
    /**
     * Initialise la collection avec les balises ouvrante seulement
     * @return aRetourne collection contenant les balises ouvrantes
     */
    public static ArrayList<String> initCollectionBaliseOuvrante() {
    	ArrayList<String> aRetourne = new ArrayList<String>();
        
        /* Ensemble des balises */
        String[] balise = {Cacher.SYNTAXE_OUVERTURE,
                           Taille.SYNTAXE_OUVERTURE, 
                           Gras.SYNTAXE_OUVERTURE, 
                           };
        
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
