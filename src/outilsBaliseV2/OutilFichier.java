/*
 * OutilFichier.java                                            27 nov. 2019
 * IUT info2 2019-2020 groupe 8 
 */
package outilsBaliseV2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import balises.Balise;
import balises.FactoryBalise;

/**
 * @author groupe 8
 */
public class OutilFichier {

	/** Lors que l'écriture des balises (cacher pour le moment) nous liront l'arrayList*/
	public static ArrayList<String> contenuBaliseModifie = new ArrayList<String>();
	
    /**
     * Remet en forme le fichier source
     * @param fichierSource fichier a remettre en forme
     */
    public static void corrigeFichier(String fichierSource) {
        String ligne; // ligne utile du fichier content
        StringBuilder aEcrire = new StringBuilder(); // a ecrire dans le fichier
        
        /* ouverture du fichier */
        try(BufferedReader fichier = new BufferedReader(new InputStreamReader( 
				new FileInputStream(fichierSource), "UTF-8"))) {
            
            PrintWriter ecris = new PrintWriter("ecris.txt", "UTF-8");
        	//PrintWriter ecris = new PrintWriter(new FileWriter("ecris.txt"));
            
            ecris.println(fichier.readLine()); // La première ligne permettra la re-écriture
            ligne = fichier.readLine();
            
            /* Les caractère '<' et '>' on été remplacé durant la lecture de la ligne,
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
    
    /**
     * Lis le fichier et lis les balises personnalisé
     * @param fichierSource fichier a lire
     */
    public static void lectureFichier(String fichierSource) {
    	
        String ligne; // ligne lu dans le fichier
        StringBuilder balise = new StringBuilder();
        
        int indiceBalise;  // indice de la balise rencontré dans la collection BALISE
        
        int nbBaliseImbric; // nombre de balise personnaliser rencontrer dans le contenu
        int idBaliseImbric; // indice de la balise rencontre dans le contenu
        
        try(BufferedReader fichier = new BufferedReader(new InputStreamReader( 
				new FileInputStream(fichierSource), "UTF-8"))) {

            while ((ligne = fichier.readLine()) != null) {
                if ((indiceBalise = Balise.baliseValide(ligne)) != -1 && indiceBalise % 2 == 0) {
                    
                    ligne = fichier.readLine(); // on saute la ligne de la balise valide
                    fichier.mark(0);            // on marque la position pour y revenir
                    
                    /* lecture du contenu entre les balises */
                    nbBaliseImbric = 0;
                    do {
                        idBaliseImbric = Balise.baliseValide(ligne);
                        
                        /* detection d'une balise ouvrante personnalise */
                        if (idBaliseImbric != -1 && idBaliseImbric % 2 == 0) {
                            nbBaliseImbric++; 
                        /* detection d'une balise fermante */
                        } else if (idBaliseImbric != -1 && idBaliseImbric % 2 == 1) {
                            nbBaliseImbric--;
                        }
                        
                        balise.append(ligne);
                        
                    } while (!(ligne = fichier.readLine()).equals(Balise.BALISE.get(indiceBalise+1)) || nbBaliseImbric != 0);
                    
                    /* On applique les modifications souhaitées à la balise */
                    FactoryBalise fb = new FactoryBalise();
                    Balise c = fb.creerBalise("cacher", balise.toString()); // TODO modif
                    c.appliquerModif();
                    
                    contenuBaliseModifie.add(c.toString());
                   
                    System.out.println(balise.toString());
                    
                    balise.delete(0, balise.length()); // vider le tampon
                    fichier.reset(); // retour a la marque
                }
            }
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * TODO commenter le rôle de cette méthode
     * @param collectionBalise
     */
    public static void ecrireFichier(ArrayList<Balise> collectionBalise) {
    }
    
    
    /**
     * TODO commenter le rôle de cette méthode
     * @param collectionBalise
     * @throws IOException 
     */
    public static void ecrireFichier(String fichierSource, String fichierDestination) throws IOException {
        
    	BufferedReader fSource =  new BufferedReader(new InputStreamReader( 
										new FileInputStream(fichierSource), "UTF-8"));
        		
    	PrintWriter fDestination = new PrintWriter("content2.xml", "UTF-8");
    	
    	String ligne;
    	int i = 0; // indice dans l'arrayList contenuBaliseModifie
    	
    	fDestination.println(fSource.readLine()); // on écrit la première ligne
    	
    	while((ligne = fSource.readLine()) != null) {
    		if(ligne.equals(Balise.BALISE.get(0))) {
    			/* Alors c'est la balise <cacher> */
    			fSource.readLine(); // on lit la ligne du contenu 
    			fDestination.print(contenuBaliseModifie.get(i));
    			
    			fSource.readLine(); // on saute pas la balise fermente
    		} else {
    			fDestination.print(ligne);
    		}
    		
    	}
    	
    	fSource.close();
    	fDestination.close();
    	
    }
    
    
    /**
     * Main de test
     * @param args inutilise
     */
    public static void main(String[] args) {
        //corrigeFichier("content.xml");
        lectureFichier("touchepasCaubel.txt");
        /*try {
			ecrireFichier("ecris.txt", "content2.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
    }
}
