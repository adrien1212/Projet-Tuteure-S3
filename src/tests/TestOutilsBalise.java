package tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import outilsBaliseV2.OutilsBaliseV2;

public class TestOutilsBalise {

	public static final String FICHIER_TEST_GENERAL = "src/outilsBaliseV2/fichierTestGeneral";
	
	
	/** Tests unitaires de la méthode construireBalise*/
	public static void TestContruireBalise() {
		String[][] tabLigne = {
				{"<b1>"},
				{"<<b1>"},
				{"<b1>>"},
				{"texte<b1>"},
				{"<b1>texte"},
				{"texte<b1>texte"},
				{"<b1><b1>"},
				{"<b1<b2>"},
				{"<b1<b2>>"}
		};
		
		int[] positionChevron = {0,0,0,5,0,5,0,0,0};
		
		String[][] resultat = {
				{"<b1>"},
				{"<<b1>"},
				{"<b1>"},
				{"<b1>"},
				{"<b1>"},
				{"<b1>"},
				{"<b1>"},
				{"<b1<b2>"},
				{"<b1<b2>"}
		};
		
		int nbSucces = 0;
		
		System.out.println("\n *** Test construireBalise *** \n");
		for(int i = 0; i < tabLigne.length; i++) {
			if(OutilsBaliseV2.contruireBalise(tabLigne[i][0], positionChevron[i]).equals(resultat[i][0])) {
				nbSucces++;
			} else {
				System.out.println("Test " + i+1 + " pas passé");
				System.out.println("Résultat obtenue : " + OutilsBaliseV2.contruireBalise(tabLigne[i][0], positionChevron[i]));
			}
		}
		
		if(nbSucces == 9) {
			System.out.println("Tout les test ok");
		} else {
			System.out.println(9-nbSucces + "echec");
		}
		
	}
	
	
	/** 
	 * Test unitaites de la méthode recupererBalisePresente 
	 * Retourne la hauteur de la pile
	 */
	
	public static void TestRecupererBalisePresente() throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(FICHIER_TEST_GENERAL));
		String ligne;
		int hauteurRecupere = -2; // hauteur récupéré par la méthode
		int nbEchecs = 0;
		
		int[] hauteurTherorique = {0,0,0,0,0,0,1,1,-1,-1,-1,-1,4,1,4,-1,-1,-1, 2};
		
		/* Pour chaque ligne du fichier, on regarde la hauteur de la pile */
		System.out.println("\n *** Test recupererBalisePresente *** \n");
		for(int i = 0; (ligne = bf.readLine()) != null; i++) {
			hauteurRecupere = OutilsBaliseV2.recupererBalisePresente(ligne);
			if(hauteurTherorique[i] != hauteurRecupere) {
				nbEchecs++;
				System.out.println("Echec test : " + i);
				System.out.println("Hauteur theorique : " + hauteurTherorique[i] + "-> hauteur trouvé : " + hauteurRecupere);
			}
		}
		
		System.out.println("Nombre d'echecs : " + nbEchecs);
	}
	
	
	
	
	/** Tests unitaire de la méthode verifierBalise
	 * @throws IOException */
	public static void TestVerifierBalise() throws IOException {
		int nbEchec = 0;
		
		/* Génération de fichiers de texte via fichierTestGeneral */
		PrintWriter pw;
		BufferedReader bf = new BufferedReader(new FileReader(FICHIER_TEST_GENERAL));
		String ligne;
		
		/* Test avec balises correctes */
		System.out.println("\n *** Test verifierBalise avec balises correctes *** \n");
		for(int i = 0; (ligne = bf.readLine()) != null && i < 6; i++) {
			pw = new PrintWriter(new FileWriter("src/outilsBaliseV2/fichierGenere"));
			pw.println("ligne 1");
			pw.print(ligne);
			pw.close();
			
			/* On exécute les tests */
			if(!OutilsBaliseV2.verifierBalise("src/outilsBaliseV2/fichierGenere")) {
				nbEchec++;
				System.out.println("Echec test " + i);
			}
		}
		System.out.println("Nombre d'echec avec balise correcte : " + nbEchec);
		
		/* test avec balises incorrectes */
		System.out.println("\n *** Test verifierBalise avec balises incorrectes *** \n");
		for(int i = 6; (ligne = bf.readLine()) != null; i++) {
			pw = new PrintWriter(new FileWriter("src/outilsBaliseV2/fichierGenere"));
			pw.println("ligne 1");
			pw.print(ligne);
			pw.close();
			
			/* On exécute les tests */
			if(OutilsBaliseV2.verifierBalise("src/outilsBaliseV2/fichierGenere")) {
				nbEchec++;
				System.out.println("Echec test " + i);
			}
		}
		
		System.out.println("Nombre d'echec avec balise incorrecte : " + nbEchec);
		
		bf.close();
	}
	

	public static void main(String[] agrs) throws IOException {
		//TestContruireBalise();
		TestVerifierBalise();
		//TestRecupererBalisePresente();
	}
}
