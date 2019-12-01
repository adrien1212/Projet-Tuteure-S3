package test;

import java.io.IOException;

import outilsBaliseV2.OutilFichier;
import outilsBaliseV2.VerifierFichier;

public class LancementCacher {

	public static void main(String[] args) throws IOException {
		
		System.out.println("Ex�cution des balises cach�s");
		
		/* On v�rifie que le fichier est correct */
		if(VerifierFichier.verifierBalise("content.xml")) {
			/* On indente le fichier, le fichier "ecris.txt" est cr�� */
			OutilFichier.corrigeFichier("content.xml");
			
			/* Lit le fichier et trouve les balises afin d'appliquer un style 
			 */
			OutilFichier.lectureFichier("ecris.txt");
			
			OutilFichier.ecrireFichier("ecris.txt", "content2.xml");
		}
		
		
	}

}
