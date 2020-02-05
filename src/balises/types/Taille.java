/*
 * Taille.java                                                  24 nov. 2019
 * IUT info2 2019-2020 groupe 8
 */
package balises.types;

import balises.Balise;

/**
 * Permet de changer la taille du texte d'un diaporama
 * @author groupe 8
 */
public class Taille implements Balise {
    
    /** Syntaxe de la balise fermante taille */
    public static final String SYNTAXE_OUVERTURE = "<taille>";
    
    /** Syntaxe de la balise fermante taille */
    public static final String SYNTAXE_FERMETURE = "</taille>";
    
    @Override
    public void appliquerModif() {
        // TODO Auto-generated method stub
    }

    @Override
    public Balise creerBalise(String contenuBalise) {
        // TODO Auto-generated method stub
        return null;
    }
}
