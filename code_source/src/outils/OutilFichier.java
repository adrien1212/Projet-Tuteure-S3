/*
 * OutilFichier.java                                            7 mars 2020
 * IUT info 2019 - 2020
 */
package outils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import balise.Balise;
import balise.Contenu;
import balise.FabriqueBalise;
import balise.type.*;

/**
 * Methodes outils pour la verification, la lecture, et l'ecriture
 * du fichier content.xml
 * @author groupe 8
 */
public class OutilFichier {

    /** Coolection des balises trouvees pendant la lecture du fichier */
    public static ArrayList<Balise> baliseTrouve;

    /** Indique si une balise cacher est presente dans le texte */
    public static boolean cacherPresent = false;

    /** Indique si une balise cacher est presente dans le texte */
    public static boolean grasPresent = false;

    /** Indique si une balise cacher est presente dans le texte */
    public static boolean italiquePresent = false;

    public static boolean soulignePresent = false;

    /**
     * Remet en forme le fichier source
     * @param fichierSource fichier a remettre en forme
     * @param fichierDestination nom du fichier destination
     */
    public static void indenteFichier(String fichierSource, String fichierDestination) {
        String ligne; // ligne utile du fichier content
        StringBuilder aEcrire = new StringBuilder(); // a ecrire dans le fichier

        /* ouverture du fichier */
        try(BufferedReader fichier = new BufferedReader(new InputStreamReader( 
                new FileInputStream(fichierSource), "UTF-8"))) {

            PrintWriter ecris = new PrintWriter(fichierDestination, "UTF-8");

            ecris.println(fichier.readLine()); // La première ligne permettra la re-écriture
            ligne = fichier.readLine();

            /* 
             * Les caractère '<' et '>' on été remplacé durant la lecture de la ligne,
             * On remet donc les chevrons pour la suite de l'éxécution
             */
            ligne = ligne.replaceAll("&lt;", "<");
            ligne = ligne.replaceAll("&gt;", ">");


            for (int i = 0; i < ligne.length(); i++) {

                if (ligne.charAt(i) == '>') {
                    aEcrire.append(ligne.charAt(i));
                    aEcrire.append("\n");
                } else if (0 < i && ligne.charAt(i) == '<' && ligne.charAt(i-1) != '>') {
                    aEcrire.append("\n");
                    aEcrire.append(ligne.charAt(i));
                } else {
                    aEcrire.append(ligne.charAt(i));
                }
            }

            ecris.print(aEcrire.toString());
            ecris.close();

        } catch (IOException e) {
            System.out.println("Erreur à l'ouverture ou lecture du fichier");
        }
    }


    /*------------------------------------------------------------------------*/
    /* partie verification */
    /*------------------------------------------------------------------------*/

    /** Taille maximun d'une balise, utile dans le cas ou la balise ne ferme pas */
    private static final int TAILLE_MAX_BALISE = 10;

    /**
     * Verifie si chaque balise ouvrante à une balise fermante dans l'ordre de fermeture
     * exemple : <cacher><g>texte</g></cacher>
     * @param nomFichier nom du fichier a verifier
     * @return true si le fichier les balises contenues dans le fichiers sont correctement imbriquées
     * 		   false sinon
     */
    public static boolean verifierFichier(String nomFichier) throws IOException {

        String ligne;
        boolean Fichiercorrect = false;

        try(BufferedReader fichier = new BufferedReader(new InputStreamReader(new FileInputStream(nomFichier), "UTF-8"))) {

            /* Le fichier ne contient que 2 lignes, on saute la première ligne qui ne nous interesse pas */
            fichier.readLine();
            ligne = fichier.readLine(); //Ligne qui nous interesse

            ligne = ligne.replaceAll("&lt;", "<");
            ligne = ligne.replaceAll("&gt;", ">");

        } catch (IOException e) {
            System.out.println("Probleme avec l'ouverture ou la lecture du fichier - verification du fichier");
            throw e;
        }

        if(recupererBalisesPresentes(ligne) == 0) {
            Fichiercorrect = true;
        }

        return Fichiercorrect;
    }

    /**
     * Récupère les balises présentent dans le fichier.
     * @param ligne à vérifier
     * @return 0 si les balises sont correctement imbriquées
     * 		   N sinon
     */
    private static int recupererBalisesPresentes(String ligne) {

        /* Dès qu'une balise est ouvrante on l'ajoute à la liste
         * et dès qu'on trouve la balise fermante associée, on supprime la balise
         * ouvrante et fermante de la pile
         * Si les balises sont correctes, alors la pile doit valoir 0
         */
        ArrayList<String> pile = new ArrayList<String>();

        String balisePotencielle;

        /* 0 si ouvrante, 1 si fermante, -1 sinon */
        int typeBalise = -1; 

        for(int i = 0; i < ligne.length(); i++) {
            if(ligne.charAt(i) == '<') {
                balisePotencielle = construireBalise(ligne, i);

                if((typeBalise = Balise.estBalise(balisePotencielle)) != -1) {
                    if(typeBalise == 0) {
                        /* c'est une balise ouvrante */
                        pile.add(balisePotencielle);
                    } else {
                        /* c'est une balise fermante */
                        try {
                            pile.add(balisePotencielle);
                            modifierPile(pile);
                        } catch(IndexOutOfBoundsException e) {
                            /* Alors la première balise est fermente */
                            return -1;
                        }
                    }
                }
            }
        }

        return pile.size();
    }

    /**
     * Construit la balise potentielle
     * @param ligne où est la balise
     * @param positionChevron du chevron ouvrant de la balise
     * @return la chaine trouvé à partir de '<' et jusqu'à '>' si le caractère est présent
     */
    private static String construireBalise(String ligne, int positionChevron) {
        StringBuilder balise = new StringBuilder();
        int i;
        for(i = positionChevron; ligne.charAt(i) != '>' && i < positionChevron+TAILLE_MAX_BALISE && i < ligne.length()-1; i++) {
            balise.append(ligne.charAt(i));
        }
        balise.append(ligne.charAt(i));
        return balise.toString();
    }

    /**
     * Modifie la taille de la pile en fonction des balises
     * Si une balise ouvrante est suivie de SA balise fermante, alors on dépile les deux balises
     * @param pile où sont les balise
     */
    private static void modifierPile(ArrayList<String> pile) {
        /* On récupère les 2 dernières balises ajoutées */
        String derniereBalise = pile.get(pile.size()-1);
        String avantDerniereBalise = pile.get(pile.size()-2);

        int hauteurPile = pile.size();

        /* On ajoute un '/' à l'avant dernière balise car elle est supposé ouvrante */
        avantDerniereBalise = avantDerniereBalise.substring(0, 1) + "/" + avantDerniereBalise.substring(1);

        /* Si maintenant, la dernièreBalise (qui est supposé fermante) et équivalente à avantDernièreBalise
         * Alors, la pile est correcte
         */
        if(avantDerniereBalise.equals(derniereBalise)) {
            /* Alors on peut supprimer les balises de la pile */
            pile.remove(hauteurPile-1);
            pile.remove(hauteurPile-2);
        }
    }


    /*------------------------------------------------------------------------*/
    /* partie lecture */
    /*------------------------------------------------------------------------*/

    /**
     * Lis le fichier et lis les balises personnalisées
     * @param fichierSource fichier a lire
     */
    public static void lectureFichier(String fichierSource) {
    	/* Init ici pour l'IHM*/
    	cacherPresent = false;
    	grasPresent = false;
    	italiquePresent = false;
    	soulignePresent = false;
    	
        baliseTrouve = new ArrayList<Balise>();

        String ligne;     // ligne lu dans le fichier

        int typeBalise;   // permet de savoir si c'est une balise valise ouvrante ou fermante
        int nbImb; // nombre de balise ouvert (imbrique)

        FabriqueBalise factory = new FabriqueBalise();
        Balise tmp;      // variable temporaire

        try(BufferedReader fichier = new BufferedReader(new InputStreamReader( 
                new FileInputStream(fichierSource), "UTF-8"))) {

            nbImb = 0;

            /* tant que des lignes sont presentes */
            while ((ligne = fichier.readLine()) != null) {
                typeBalise = Balise.estBalise(ligne);

                /* si une balise ouvrante est trouvee */
                if (typeBalise == 0) {

                    /* on creer la balise correspondante */
                    tmp = factory.creerBalise(ligne, null);
                    /* on ajoute a la collection */
                    baliseTrouve.add(tmp);

                    /* si la balise est imbriquee */
                    if (nbImb != 0) {
                        imbrique(nbImb);
                    }

                    nbImb++; // incremente l'imbrication

                    /* changement des variables de presence */
                    switch (ligne) {
                    case Cacher.SYNT_OUVRANTE:
                        cacherPresent = true;
                        break;
                    case Gras.SYNT_OUVRANTE:
                        grasPresent = true;
                        break;
                    case Italique.SYNT_OUVRANTE:
                        italiquePresent = true;
                        break;
                    case Souligne.SYNT_OUVRANTE:
                    	soulignePresent = true;
                    }
                    
                /* si une balise fermante est trouvee */
                } else if (typeBalise == 1) {
                    nbImb--;

                /* sinon contenu */
                } else if (nbImb != 0) {
                    assignerContenu(nbImb, new Contenu(ligne));
                }
            }

            /* on retire les balises sans contenu */
            viderBaliseMarquante();

        } catch (IOException e) {
            System.out.println("LECTURE : Probleme avec le fichier : " + fichierSource);
        }
    }

    /**
     * Imbrique les balises de la collection baliseTrouve
     * l'imbrication aura une forme de pile mais aura le contenu vide
     * elle sert de marquage
     * @param baliseOuvert nombre d'imbrication
     */
    private static void imbrique(int baliseOuvert) { 
        Balise tmp; // variable de stockage
        int taille; // taille de la collection baliseTrouve

        taille = baliseTrouve.size()-1;
        /* puis on remonte le nombre d'imbrication */
        for (int i = 0; i < baliseOuvert; i++) {
            /* on recupere la derniere balise de la collection */
            tmp = baliseTrouve.get(taille);

            /* on recupere le type de la balise precedente puis on en creer une nouvelle */
            baliseTrouve.set(taille, baliseTrouve.get(taille-1)
                        .getBaliseImbrique(baliseOuvert-i-1)
                        .creerBalise(null));

            /* puis on imbrique dans cette nouvelle balise tmp */
            baliseTrouve.get(taille).setBaliseImbrique(tmp);
        }
    }

    /**
     * Assigne le contenu au balise correspondante
     * @param baliseOuvert nombre de balise imbrique
     * @param contenu contenu a assigner
     */
    private static void assignerContenu(int baliseOuvert, Contenu contenu) {
        Balise aAjouter, // pile de balise dupliquer a ajouter a la collection
               tmpAjout, // variable temporaire pour construire la nouvelle pile
               tmp;      // variable temporaire pour dupliquer la pile de balise

        /* on recupere le type de la derniere balise de la collection */
        tmp = baliseTrouve.get(baliseTrouve.size()-1);
        aAjouter = tmp.creerBalise(null); // on creer une nouvelle balise
        tmpAjout = aAjouter;              // on part de cette balise

        /* dupliquer la pile de balise imbrique */
        for (int i = 1; i < baliseOuvert; i++) {
            tmp = tmp.getBaliseImbrique();
            tmpAjout.setBaliseImbrique(tmp.creerBalise(null));
            tmpAjout = tmpAjout.getBaliseImbrique();
        }

        /* ajout du contenu, si ce n'est pas une balise imbrique alors on ajoute directement  */
        if (baliseOuvert == 1) {
            aAjouter.setContenu(contenu);
        } else {
            /* pour chaque balise imbrique on assigne le contenu */
            for (int i = 1; i <= baliseOuvert; i++) {
                aAjouter.getBaliseImbrique(baliseOuvert-i).setContenu(contenu);
            }
        }
        
        /* ajouter a la collection */
        baliseTrouve.add(aAjouter);
    }

    /**
     * Retire les balises marquantes (contenu vide) de la collection baliseTrouve
     */
    private static void viderBaliseMarquante() {
        Balise tmp; // variable temporaire
        
        /* liste des balises a supprimer */
        ArrayList<Balise> aSupprimer = new ArrayList<Balise>();
        
        /* balayage de toute la collection */
        for (Balise balise : baliseTrouve) {
            
            tmp = balise;
            /* on atteint la derniere balise imbrique */
            while (tmp.getBaliseImbrique() != null) {
                tmp = tmp.getBaliseImbrique();
            }
            
            /* si le contenu est vide alors on supprime */
            if (tmp.getContenu() == null) {
                aSupprimer.add(balise);
            }
        }
        
        /* suppression des balises vides */
        for (Balise balise : aSupprimer) {
            baliseTrouve.remove(balise);
        }
    }
    

    /*------------------------------------------------------------------------*/
    /* partie ecriture */
    /*------------------------------------------------------------------------*/

    /**
     * Ecrit le fichier content.xml avec les balises selectionnees
     * @param fSource content.xml indente issue du diaporama source
     * @param fDestination content.xml reecris le fichier content.xml avec les balises
     * @param balisesCochees liste des balises selectionnees par l'utitilisateur
     */
    public static void ecrireFichier(String fSource, String fDestination, 
                                     ArrayList<String> balisesCochees) {

        String ligne; // variable temporaire pour lire le fSource
        
        Balise tmp; // variable temporaire pour recuperer les balises imbriquees
        int indice; // indice pour balayer la collection baliseTrouve
        int nbImbr; // donne le niveau d'imbrication
        
        boolean styleARajouter; // indique si on doit rajouter le style

        try {
            
            /* fichier ou prendre le contenu */
            BufferedReader in =  new BufferedReader(
                                 new InputStreamReader(
                                 new FileInputStream(fSource), "UTF-8"));
            
            /* fichier a ecrire */
            PrintWriter out = new PrintWriter(fDestination, "UTF-8");
            
            styleARajouter = true;
            indice = nbImbr = 0;
            
            /* parcours des lignes du fichier source */
            while((ligne = in.readLine()) != null) {
                
                if (Balise.estBalise(ligne) == 0) {
                    /* balise ouvrante */
                    nbImbr++;
                } else if (Balise.estBalise(ligne) == 1) {
                    /* balise fermante */
                    nbImbr--;
                } else if (nbImbr != 0) { 
                    /* contenu de la balise */
                    
                    /* recuperation de la balise courante */
                    tmp = baliseTrouve.get(indice);
                    if (balisesCochees.contains(tmp.getSyntOuvrante())) {
                        tmp.appliquerModif();
                    }
                    
                    /* parcours des balises imbrique si il y a */
                    while (tmp.getBaliseImbrique() != null) {
                        tmp = tmp.getBaliseImbrique();
                        if (balisesCochees.contains(tmp.getSyntOuvrante())) {
                            tmp.appliquerModif();
                        }
                    }
                    
                    indice++; // on passe a la balise suivante
                    
                    /* on recopie le contenu dans le fichier */
                    out.print(tmp.getContenu().getChaine());
                    
                } else {
                    /* contenu de libreOffice */
                    out.print(ligne);
                    
                    /* On rajoute les style pour certaines balises (gras, italique ...) */
                    if(styleARajouter && ligne.equals("<office:automatic-styles>")) {
                        /* On écrit les styles */
                        // GRAS
                        out.print("<style:style style:name=\"GRAS\" style:family=\"text\"><style:text-properties style:font-weight-complex=\"bold\" style:font-weight-asian=\"bold\" fo:font-weight=\"bold\"/></style:style>");
                        // ITALIQUE
                        out.print("<style:style style:name=\"ITALIQUE\" style:family=\"text\"><style:text-properties style:font-style-complex=\"italic\" style:font-style-asian=\"italic\" fo:font-style=\"italic\"/></style:style>");
                        // SOULIGNE
                        out.print("<style:style style:name=\"SOULIGNE\" style:family=\"text\"><style:text-properties style:text-underline-color=\"font-color\" style:text-underline-width=\"auto\" style:text-underline-style=\"solid\"/></style:style>");
                        
                        styleARajouter = false;
                    }
                }
            }
            
            /* fermeture */
            in.close();
            out.close();
            
        } catch (IOException e) {
            System.out.println("ECRITURE : Fichier source ou destination introuvable ");
        }
    }
    
    /**
     * TODO commenter le rôle de cette méthode
     * @param fichierSource
     * @param fichierDestination
     */
    public static void ecrireFichierSansBalise(String fichierSource, String fichierDestination) {
        String ligne;
        try {
            BufferedReader fSource =  new BufferedReader(new InputStreamReader(new FileInputStream(fichierSource), "UTF-8"));		
            PrintWriter fDestination = new PrintWriter(fichierDestination, "UTF-8");

            fDestination.println(fSource.readLine()); // on écrit la première ligne

            while((ligne = fSource.readLine()) != null) {
                /* Si la ligne est differente d'une balise */
                if (Balise.estBalise(ligne) == -1) {
                    fDestination.print(ligne);
                }
            }

            fSource.close();
            fDestination.close();
        } catch (IOException fichierIntrouvable) {
            System.out.println("Fichier source ou destination introuvable ");
        }
    }
    
    /*------------------------------------------------------------------------*/
    /* partie methode outil */
    /*------------------------------------------------------------------------*/

    /**
     * Supprime le fichier / repertoire parametre
     * @param aSupprimer fichier ou repertoire a supprimer
     */
    public static void supprFichier(File aSupprimer) {
        if (aSupprimer.isDirectory()) {
            File[] entries = aSupprimer.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    supprFichier(entry);
                }
            }
        }

        aSupprimer.delete();
    }
}
