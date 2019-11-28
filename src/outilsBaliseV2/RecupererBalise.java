package outilsBaliseV2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import balises.Balise;
import balises.Cacher;
import balises.FactoryBalise;

public class RecupererBalise {

    /**
     * Chaque balise ouvrante à l'indice i à pour indice i+1 sa balise fermante
     * ex : <cacher> à l'indice 0 à </cacher> à l'indice 1
     */ 
    public static final String[] BALISE_SYNTAXE = {Cacher.SYNTAXE_OUVERTURE, Cacher.SYNTAXE_FERMETURE, "<g>", "</g>"};


    /**
     * Récupère les données du fichier content.xml
     * @param nomFichier content.xml
     */
    public static void recupererFichier(String  nomFichier) {
        String ligne;

        try(BufferedReader fichier = new BufferedReader(new FileReader(nomFichier))) {
            ligne = fichier.readLine();
            ligne = fichier.readLine(); // le fichier content.xml contient 2 lignes

            recupererBalise(ligne);

        } catch (IOException e) {
            System.out.println("Erreur à l'ouverture ou lecture du fichier");
        }
    }


    /**
     * Récupère la balise ouvrante et sa balise fermante 
     * @param ligne ou sont les balises
     */
    public static void recupererBalise(String ligne) {
        String balisePotentielle;
        int indiceTabBalise = -1; // indice de la balise dans le tableau balises

        for(int i = 0; i < ligne.length(); i++) {
            if(ligne.charAt(i) == '<') {
                /* On construit une chaine de caractère jusqu'au chevron fermant : '>' 
                 * ex : <à construire>
                 */
                balisePotentielle = OutilsBaliseV2.contruireBalise(ligne, i);

                if((indiceTabBalise = OutilsBaliseV2.estPresent(BALISE_SYNTAXE, balisePotentielle)) != -1) {
                    // la balise est correcte
                    if(balisePotentielle.charAt(1) != '/') {
                        // alors la balise est ouvrante
                        System.out.println(recupererContenueBalise(ligne, i, indiceTabBalise));
                        FactoryBalise fB = new FactoryBalise();
                        Balise c = fB.creerBalise("cacher", recupererContenueBalise(ligne, i, indiceTabBalise));
                        c.appliquerModif();
                        System.out.println(c.toString());
                    }
                }
            }
        }
    }

    /**
     * @param ligne ou est la balise
     * @param indiceChevron indice du chevron ouvrant ('<') de la balise ouvrante
     * @param indiceTabBalise permet de savoir quel est le type de la balise
     * @return
     */
    public static String recupererContenueBalise(String ligne, int indiceChevron, int indiceTabBalise) {
        int indiceFinBaliseOuvrante;
        int indiceDebutBaliseFermante;

        StringBuilder contenuBalise = new StringBuilder();

        /* On récupère l'indice du chevron fermant de la balise */
        indiceFinBaliseOuvrante = indiceChevron + BALISE_SYNTAXE[indiceTabBalise].length()-1;

        /* On récupère l'indice du chevron ouvrant de la balise fermante*/
        indiceDebutBaliseFermante = ligne.indexOf(BALISE_SYNTAXE[indiceTabBalise+1]);


        for(int i = indiceFinBaliseOuvrante+1; i < indiceDebutBaliseFermante; i++) {
            contenuBalise.append(ligne.charAt(i));
        }

        return contenuBalise.toString();
    }

}
