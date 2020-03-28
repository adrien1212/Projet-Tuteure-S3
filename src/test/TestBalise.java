/*
 * TestBalise.java                                                  7 mars 2020
 * IUT info 2019 - 2020
 */
package test;

import java.util.Map.Entry;

import balise.Balise;
import balise.type.*;
import outils.OutilFichier;

/**
 * Test sur les balises
 * @author groupe 8
 */
public class TestBalise {

    /*
     * Test visuel de la collection BALISE
     */
    public static void testBALISE() {     
        for(Entry<String, String> entry : Balise.BALISE.entrySet()) {
            System.out.println(entry.getKey() + " ; " + entry.getValue());
        }
    }
    
    /*
     * Test lecture des balises
     */
    public static void testLectureFichier(String fichSource) {
        OutilFichier.lectureFichier(fichSource);
        System.out.println(OutilFichier.baliseTrouve);
        System.out.println("presence cacher : " + OutilFichier.cacherPresent);
        System.out.println("presence gras : " + OutilFichier.grasPresent);
        System.out.println("presence italique : " + OutilFichier.italiquePresent);
    }
    
    /*
     * Test des balises imbriquees
     */
    public static void testBaliseImbr() {
        Gras g = new Gras();
        Italique i = new Italique();
        Cacher c = new Cacher();
        
        g.setBaliseImbrique(i);
        i.setBaliseImbrique(c);
        
        System.out.println(g.getBaliseImbrique(3));
        System.out.println(g.getContenu());
    }
    
    /*
     * Test ecriture
     */
    public static void testEcriture() {
        //OutilFichier.ecrireFichier("test/test5.txt", "test/resultat.txt", null);
        OutilFichier.lectureFichier("xmlTemp.txt"); // initialisation collection
        OutilFichier.ecrireFichier("xmlTemp.txt", "test/resultat.txt", null);
    }
    
    public static void main(String[] args) {
        //testBALISE();
        //testLectureFichier("test/test4.txt");
        testEcriture();
        //testBaliseImbr();
    }
}
