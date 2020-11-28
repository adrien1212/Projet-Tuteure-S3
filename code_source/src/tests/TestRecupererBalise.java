package tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import outilsBaliseV2.RecupererBalise;

public class TestRecupererBalise {

	public static final String FICHIER_TEST_GENERAL = "src/outilsBaliseV2/fichierRecupererBalise";
	
	
	public static void testRecupererContenueBalise() throws IOException {
		int nbEchec = 0;
		String ligne;
		String contenue;
		
		BufferedReader bf = new BufferedReader(new FileReader(FICHIER_TEST_GENERAL));
		
		for(int i = 0; (ligne = bf.readLine()) != null; i++) {
			contenue = RecupererBalise.recupererContenueBalise(ligne, 0, 0);
			System.out.println(contenue);
		}
	}
	
	public static void main(String[] args) throws IOException {
		testRecupererContenueBalise();
	}
}
