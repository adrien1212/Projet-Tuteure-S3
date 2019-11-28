package test;

import balises.*;
import outilsBaliseV2.*;

public class testGeneral {

	public static void main(String[] args) {
		/**
		 * On va tester le prendre un fichier avec une balise <cacher>Bonjour</cacher>
		 * et de remplacer le contenue
		 */
		FactoryBalise fB = new FactoryBalise();
		
		OutilsBaliseV2.verifierBalise("src/test/fichTest.txt");
		
		RecupererBalise.recupererFichier("src/test/fichTest.txt");
		
		System.out.println("bonjour");
	}
}
