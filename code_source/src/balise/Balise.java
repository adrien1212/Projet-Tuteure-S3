/*
 * Balise.java                                                  7 mars 2020
 * IUT info 2019 - 2020
 */
package balise;

import java.util.HashMap;

import balise.type.*;

/**
 * Classe parente de toute les balises 
 * @author groupe 8
 */
public abstract class Balise {

    /* Partie globale a toute l'application */

    /** 
     * Contient les syntaxes des balises ouvrante et fermantes pour chaque balise 
     * Premier arg : balise ouvrante
     * Deuxième arg : balise fermante
     */
    public static HashMap<String, String> BALISE = new HashMap<String, String>();

    /* Rempli la collection BALISE en creant toutes les balises disponibles */
    static {
        new Cacher();
        new Gras();
        new Italique();
        new Souligne();
    }
    
    
    /* Partie reserve au balise */

    /** Syntaxe de la balise ouvrante */
    public static final String SYNT_OUVRANTE = null;

    /** Syntaxe de la balise fermante */
    public static final String SYNT_FERMANTE = null;

    /** Contenu de la balise */
    protected Contenu contenu;

    /** Balise imbrique dans la balise (this) */
    private Balise baliseImbrique;


    /**
     * Constructeur par defaut
     */
    public Balise() {}

    /**
     * Construit une balise avec son contenu
     * @param contenu contenu de la balise creer
     */
    public Balise(Contenu contenu) {
        this.contenu = contenu;
    }

    
    /**
     * Construit une balise avec son contenu (factory)
     * @param contenu contenu a associer a la balise creer
     * @return la balise creer
     */
    public abstract Balise creerBalise(Contenu contenu);
    
    /** Applique les modifications de la balise sur le fichier content.xml */
    public abstract void appliquerModif();

    
    
    @Override
    public String toString() {
        String nom = getClass().getName();

        return nom.substring(nom.lastIndexOf('.')+1, nom.length()) + " :" 
             + (contenu == null ? "" : " " + contenu) 
             + (baliseImbrique == null ? "" : " {" + baliseImbrique + "}");
    }


    /* Getter et Setter */

    /** @return la syntaxe ouvrante de la balise */
    public abstract String getSyntOuvrante();
    
    /** @return valeur de contenu */
    public Contenu getContenu() {
        return contenu;
    }

    /** @param contenu nouvelle valeur de contenu */
    public void setContenu(Contenu contenu) {
        this.contenu = contenu;
    }
    
    /** @param chaine nouvelle valeur de chaine dans contenu */
    public void setContenu(String chaine) {
        contenu.setChaine(chaine);
    }

    /** @return valeur de baliseImbrique */
    public Balise getBaliseImbrique() {
        return baliseImbrique;
    }
    
    /**
     * @param profondeur niveau de profondeur d'imbrication ou chercher la balise
     * @return <li> la balise imbrique </li>
     *         <li> null si profondeur est superieur au niveau d'imbrication </li>
     */
    public Balise getBaliseImbrique(int profondeur) {
        Balise aRetourner = this;
        
        for (int i = 0; i < profondeur; i++) {
            if (aRetourner != null) {
                aRetourner = aRetourner.getBaliseImbrique() == null ? 
                             null : aRetourner.getBaliseImbrique() ;
            }
        }
        
        return aRetourner;
    }

    /** @param baliseImbrique nouvelle valeur de baliseImbrique */
    public void setBaliseImbrique(Balise baliseImbrique) {
        this.baliseImbrique = baliseImbrique;
    }
    

    /* methode outil */

    /**
     * Verifie si la chaine est une balise valide, ouvrante ou fermante
     * @param chaine chaine a tester
     * @return <li> 0 si c'est une balise ouvrante </li>
     *         <li> 1 si c'est une balise fermante </li>
     *         <li> -1 si ce n'est pas une balise valide </li>
     */
    public static int estBalise(String chaine) {
        return BALISE.containsKey(chaine) ? 0 : BALISE.containsValue(chaine) ? 1 : -1 ;
    }
}
