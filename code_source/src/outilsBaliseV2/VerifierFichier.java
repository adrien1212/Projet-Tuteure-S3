package outilsBaliseV2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import balises.Cacher;

public class VerifierFichier {

	/** 
	 * Tableau o� sont stocker les balises utilis�es
	 * Chaque balise ouvrante et suivit de sa balise fermante
	 */
	public static final String[] balises = {Cacher.SYNTAXE_OUVERTURE, Cacher.SYNTAXE_FERMETURE, "<g>", "</g>"};
	
	/** Utile dans construire balise au cas o� il n'y est pas de chevron fermant*/
	private static final int TAILLE_MAX_BALISE = 20;
	
	/**
	 * V�rifie si chaque balise ouvrante � une balise fermente dans l'ordre de fermeture
	 * <cacher><g>texte</g></cacher>
	 * @param nomFichier
	 * @return
	 */
	public static boolean verifierBalise(String nomFichier) {
		boolean estFichierCorrect = false;
		
		String ligne = null;
		try(BufferedReader fichier = new BufferedReader(new FileReader(nomFichier))) {
			/* Le fichier ne contient que 2 lignes */
			fichier.readLine();
			ligne = fichier.readLine(); //Ligne qui nous interesse
		} catch (IOException e) {
			System.out.println("Probl�me avec l'ouverture ou la lecture du fichier");
		}
		
		if(recupererBalisePresente(ligne) == 0) {
			estFichierCorrect = true;
			System.out.println("Fichier correct");
		} else {
			System.out.println("Fichier incorrect, toute les balises ouvrantes ne sont pas ferm�es");
		}
		
		return estFichierCorrect;
	}
	
	
	/** 
	 * R�cup�re toutes les balises dans le fichier et les ajoutes dans une pile
	 * D�s qu'une balise est ouvrante on l'ajoute
	 * D�s qu'une balise est fermente on v�rifie que la balise pr�c�dente est sa balise ouvrante associ�
	 * Si ce n'est pas le cas alors la taille de la pile sera diff�rent de 0 � la fin
	 * @param ligne o� on cherche les balises
	 * @return la taille de la pile 
	 */
	public static int recupererBalisePresente(String ligne) {
		ArrayList<String> pile = new ArrayList<String>();
		String balisePotentielle;
		int indiceTabBalise = -1; // indice de la balise dans le tableau balises
		
		for(int i = 0; i < ligne.length(); i++) {
			if(ligne.charAt(i) == '<') {
				/* On construit une chaine de caract�re jusqu'au chevron fermant : '>' */
				balisePotentielle = contruireBalise(ligne, i);
				
				if((indiceTabBalise = estPresent(balises, balisePotentielle)) != -1) {
					// c'est donc une balise d�finie
					if(balisePotentielle.charAt(1) == '/') {
						// C'est une balise fermante
						try {
							pile.add(balisePotentielle); //ajout de la balise fermente
							appliquerModification(pile, indiceTabBalise);
						} catch (IndexOutOfBoundsException e) {
							// Il n'y a aucune balise avant cette balise fermente
							return -1;
						}
						
					} else {
						// C'est une balise ouvrante
						pile.add(balisePotentielle);
					}
				}
				
			}
			
		}
		return pile.size();
	}
	
	/**
	 * Construit une balise mais ne dit pas si cette balise est d�finie ou non
	 * @param ligne o� on veut r�cup�rer la balise
	 * @param positionChevron position du chevron ouvrant dans la ligne
	 * @return
	 */
	public static String contruireBalise(String ligne, int positionChevron) {
		StringBuilder balisePotentielle = new StringBuilder();
		int  i;
		int nbCaractere = positionChevron+TAILLE_MAX_BALISE;
		
		for(i = positionChevron; ligne.charAt(i) != '>' && i < nbCaractere ; i++) {
			balisePotentielle.append(ligne.charAt(i));
		}
		balisePotentielle.append(ligne.charAt(i));
		
		return balisePotentielle.toString(); 
	}
	
	
	/** 
	 * Permet de savoir si une chaine est pr�sente dans un tableau ou non
	 */
	public static int estPresent(String[] tab, String aRechercher) {
		int i;
		for(i = 0; i < tab.length; i++) {
			if(tab[i].equals(aRechercher)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Supprime la balise en haut de la pile (balise fermente) et ca balise associ�e (balise ouvrante)
	 * Si la suppression est impossible alors les balises ne sont pas dans le bon ordre
	 * => le fichier n'est pas bon
	 * @param pile o� sont les balises et dont le dernier �l�ment est une balise fermente
	 * @param indiceTabBalise indice le balise fermente dans le tableau
	 * @throw IndexOutOfBoundsException si la balise du sommet et une balise fermente
	 *        => fichier mal form�e
	 */
	private static void appliquerModification(ArrayList<String> pile, int indiceTabBalise) {
		String baliseOuvrantePrecedente;
		int hauteurPile = pile.size();
		
		try {
			baliseOuvrantePrecedente = pile.get(hauteurPile-2);
		} catch (IndexOutOfBoundsException e) {
			// alors la balise fermante est la premi�re de la liste
			throw e;
		}
		
		
		if(baliseOuvrantePrecedente.equals(balises[indiceTabBalise-1])) {
			// On supprimer la balise ouvrante et fermente de la pile
			pile.remove(hauteurPile-1);
			pile.remove(hauteurPile-2);
			
		}
	}
}
