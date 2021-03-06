/*
 * FactoryBalise.java                                           24 nov. 2019
 * IUT info2 2019-2020 groupe 8
 */
package balises;

import java.util.HashMap;

import balises.Balise;
import balises.types.Cacher;

/**
 * Design pattern factory permettant la cr�ation de differentes balise
 * @author groupe 8
 */
public class FactoryBalise {

    /** Contient les informations sur les balises*/
    HashMap<String, Balise> baliseTypes = new HashMap<String, Balise>();

    /**
     * Permet d'ajouter les balises qu'on va utiliser
     */
    public FactoryBalise() {
        baliseTypes.put("0", new Cacher());
    }

    /**
     * Creer une balise en fonction du parametre
     * @param balise balise a creer
     * @param contenuBalise 
     * @return la balise creer
     */
    public Balise creerBalise(String balise, String contenuBalise) {
        Balise b = baliseTypes.get(balise); // on r�cup�re un objet associ� au type
        return b.creerBalise(contenuBalise); // on applique la bonne m�thode (polymorphisme)
    }
}
