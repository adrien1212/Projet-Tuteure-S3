/*
 * FabriqueBalise.java                                                  7 mars 2020
 * IUT info 2019 - 2020
 */
package balise;

import java.util.HashMap;

import balise.Balise;
import balise.type.*;

/**
 * Design pattern factory pour la création des balises
 * @author groupe 8
 */
public class FabriqueBalise {

    /** Contient les balises */
    private HashMap<String, Balise> typeBalise = new HashMap<String, Balise>();
    
    /**
     * Constructeur par defaut, initialise la collection typeBalise
     */
    public FabriqueBalise() {
        typeBalise.put(Cacher.SYNT_OUVRANTE, new Cacher());
        typeBalise.put(Gras.SYNT_OUVRANTE, new Gras());
        typeBalise.put(Italique.SYNT_OUVRANTE, new Italique());
        typeBalise.put(Souligne.SYNT_OUVRANTE, new Souligne());
    }
    
    /**
     * Creer la balise correspondante avec son contenu
     * @param balise type de la balise a creer
     * @param contenu contenu a associer avec la balise
     * @return la balise creer
     */
    public Balise creerBalise(String balise, Contenu contenu) {
        return typeBalise.get(balise).creerBalise(contenu);
    }
}
