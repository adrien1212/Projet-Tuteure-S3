package outilsBalise;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import balises.Balise;
import balises.Cacher;

public class OutilsBalise {
	
	public static final String[] balises = {"<cacher>", "</cacher>", "<g>", "</g>"};
	
	public static final String[] B_OUVRANTES = {"<cacher>"};
	public static final String[] B_FERMENTES = {"</cacher>"};
	
	public static ArrayList<Balise> baliseTrouvee;
	
	public static ArrayList<Balise> trouverBalise(String nomFichier) {
		baliseTrouvee = new ArrayList<Balise>();
		String ligne;
		
		try(BufferedReader fichier = new BufferedReader(new FileReader(nomFichier))) {
			while( (ligne=fichier.readLine()) != null) {
				if(ligne.contains("e")) {
					System.out.println("Balise cacher trouvée");
					baliseTrouvee.add(new Cacher(ligne));    //recupererContenuLigne(ligne, "<cacher>", "</cacher>")));
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("fichier " + nomFichier + " non trouvé");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return baliseTrouvee;
	}
	
	public static String recupererContenuLigne(String ligne, String baliseOuvrante, String BaliseFermente) {
		return "";
	}
	
	/**
	 * Vérifier qu'il y est le bon nombre de balise c'est à dire autant de balises ouvrantes
	 * que de balises associées fermentantes
	 * @param nomFichier
	 * @return true s'il y a le bon nombre de balise
	 *         false sinon
	 */
	public static boolean compterBalise(String nomFichier) {
		HashMap<String, Integer> nbBalise = new HashMap<String, Integer>();
		String ligne;
		boolean nbBaliseCorrect = true;
		
		try(BufferedReader fichier = new BufferedReader(new FileReader(nomFichier))) {
			while ( (ligne = fichier.readLine()) != null) {
				/* Associe à chaque balise le nombre de fois où elle apparait*/
				for(int i = 0; i < balises.length; i++) {
					nbBalise.put(balises[i], nombreApparation(ligne, balises[i]));
				}
			}
		} catch (IOException e) {
			System.out.println("Problème avec l'ouverture ou la lecture du fichier");
		}
		
		/** 
		 * On regarde si le nombre de balises ouvrantes est le même
		 * que nombre de balises fermentes (de même type) 
		 */
		for(int i = 0; i < balises.length && nbBaliseCorrect; i+=2) {
			if(nbBalise.get(balises[i]) != nbBalise.get(balises[i+1])) {
				System.out.println("pas bon nombre balise");
				nbBaliseCorrect = false;
			}
		}
		return nbBaliseCorrect;
		
	}
	
	/**
	 * Compte de nombre de fois ou un texte apparait dans une chaine
	 * @param texte
	 * @return
	 */
	private static int nombreApparation(String chaine, String aRechercher) {
		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1) {

		    lastIndex = chaine.indexOf(aRechercher, lastIndex);

		    if (lastIndex != -1) {
		        count++;
		        lastIndex += aRechercher.length();
		    }
		}
		
		return count;
	}
	
	/**
	 * 1) Dès qu'une balise ouvrante et trouvé, l'ajouter à la pile
	 * 2) Dès qu'une balise précédente et trouvé verifier que la balise-1 et sa balise ouvrante
	 * @param nomFichier
	 */
	public static void verifierFichier(String nomFichier) {
		ArrayList<String> pile = new ArrayList<String>();
		String ligne = null;
		String balisePotentielle = null;
		int hauteurPile = 0;
		boolean baliseEstPresente = false;
		int indiceTabBalise = 0;
		
		try(BufferedReader fichier = new BufferedReader(new FileReader(nomFichier))) {
			/* Le fichier ne contient que 2 lignes */
			fichier.readLine();
			ligne = fichier.readLine(); //Ligne qui nous interesse
		} catch (IOException e) {
			System.out.println("Problème avec l'ouverture ou la lecture du fichier");
		}
		
		/* On récupère les balises présentent dans le texte*/
		for(int i = 0; i < ligne.length(); i++) {
			if(ligne.charAt(i) == '<') {

				balisePotentielle = contruireBalise(ligne, i);
				/* On vérifie que la balise est présente dans la liste des balises */
				if((indiceTabBalise = estPresent(balises, balisePotentielle)) != -1) {
					baliseEstPresente = true;
				}

				
				/* On vérifie si la balise est fermante : la 2ème caractère est '/'*/
				if(baliseEstPresente && balisePotentielle.charAt(1) == '/') {
					// la balise est fermante
					pile.add(balisePotentielle);
					hauteurPile++;
					System.out.println(pile.toString());
					/* Vérifier que la balise précédente est sa balise associée */
					if((pile.get(hauteurPile-2)).equals(balises[indiceTabBalise-1])) {

						pile.remove(hauteurPile-1);
						pile.remove(hauteurPile-2);
						hauteurPile-=2;
					} else {
						System.out.println("Problème balises non rangées");
					}
					
				} else if(baliseEstPresente) {
					pile.add(balisePotentielle);
					hauteurPile++;
					System.out.println(pile.toString());
				}
			} 
		}	
		System.out.println(pile.toString());
	}
	
	
	private static String contruireBalise(String ligne, int positionChevron) {
		StringBuilder balisePotencielle = new StringBuilder();
		int  i;
		
		for(i = positionChevron; ligne.charAt(i) != '>'; i++) {
			balisePotencielle.append(ligne.charAt(i));
		}
		balisePotencielle.append(ligne.charAt(i));
		
		return balisePotencielle.toString();
	}
	
	/**
	 * @param tab
	 * @param aRechercher
	 * @return l'indice de la balise dans le tableau
	 */
	private static int estPresent(String[] tab, String aRechercher) {
		int i;
		for(i = 0; i < tab.length; i++) {
			if(tab[i].equals(aRechercher)) {
				return i;
			}
		}
		return -1;
	}
	
	
	public static void main(String[] args) {
//		trouverBalise("content.xml");
//		System.out.println(baliseTrouvee.get(1).toString());
//		baliseTrouvee.get(0).appliquerModif();
//		System.out.println(baliseTrouvee.get(0).toString());
		
		System.out.println(compterBalise("test.txt"));
		verifierFichier("test.txt");
	}
}
