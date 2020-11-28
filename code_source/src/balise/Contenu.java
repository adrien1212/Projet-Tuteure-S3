/*
 * Contenu.java                                                  7 mars 2020
 * IUT info 2019 - 2020
 */
package balise;

/**
 * Contenu d'une balise
 * @author groupe 8
 */
public class Contenu {

    /** chaine contenu */
    private String chaine;
    
    /** chaine */
    private String chaineInitiale;
    
    /**
     * Construit un contenu avec une chaine
     * @param chaine chaine a contenir
     */
    public Contenu(String chaine) {
        this.chaine = chaine;
    }

    /** @return valeur de chaine */
    public String getChaine() {
        return chaine;
    }
    
    /** @param chaine nouvelle valeur de chaine */
    public void setChaine(String chaine) {
        this.chaine = chaine;
        chaineInitiale = chaine;
    }
    
    /** @return valeur de chaineInitiale */
    public String getChaineInitiale() {
        return chaineInitiale;
    }

    @Override
    public String toString() {
        return chaine;
    }
}
