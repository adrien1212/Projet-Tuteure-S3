/*
 * Zip2.java                                                  23 nov. 2019
 * IUT info1 2018-2019 groupe 1, aucun droits : ni copyright ni copyleft 
 */
package outilsBaliseV2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Permet de zipper dezipper un fichier ODP
 * @author Pc
 */
public class Zip {

    /*
     * Partie compression (fichier -> ODP)
     */
    
    /**
     * Compresse les fichiers dans le ZipOutputStream, 
     * si c'est un repertoire on le traite de maniere recursive
     * @param aCompresser fichier ou repertoire a compresser
     * @param nomParent nom du fichier parent
     * @param archive archive contenant les fichiers compresses
     * @throws IOException si un probleme avec les fichiers est rencontre
     */
    private static void compressFile(File aCompresser, String nomParent, 
                                     ZipOutputStream archive) 
                                     throws IOException {
        
        /* nom du fichier a compresser */
        String nom = new StringBuilder(nomParent)
                        .append(aCompresser.getName())
                        .append(aCompresser.isDirectory() ? '/' : "")
                        .toString();
        
        /* Définition des attributs du fichier */
        ZipEntry entree = new ZipEntry(nom);
        entree.setSize(aCompresser.length());
        entree.setTime(aCompresser.lastModified());
        archive.putNextEntry(entree);
        
        /* Traitement récursif s'il s'agit d'un répertoire */
        if (aCompresser.isDirectory()) {
            for (File fichier : aCompresser.listFiles()) {
                compressFile(fichier, nom, archive);
            }
            return;
        }
        
        /* Ecriture du fichier dans le zip */
        InputStream input = new BufferedInputStream(
                            new FileInputStream(aCompresser));
        
        try {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = input.read(buffer))) {
                archive.write(buffer, 0, bytesRead);
            }
        } finally {
            input.close();
        }
    }
    
    /**
     * Enveloppe de compressFile, sort un fichier ODP
     * @param aZipper fichier a compresser
     * @throws IOException si un probleme avec les fichiers est rencontre
     */
    public static void compressFile(String aZipper) 
           throws IOException {
        
        String nomODP = aZipper + ".odp";
        
        /* fichier compresse */
        ZipOutputStream archive = new ZipOutputStream(
                                  new FileOutputStream(nomODP));
        archive.setMethod(ZipOutputStream.DEFLATED);
        archive.setLevel(Deflater.BEST_COMPRESSION);
        
        /* conversion de la chaine en fichier */
        File contenant = new File(aZipper);
        
        /* compression de tous les sous fichiers / repertoires */
        for (File aCompresser : contenant.listFiles()) {
            compressFile(aCompresser, "", archive);
        }
        
        /* fermeture de l'archive */
        archive.close();
    }
    
    
    /*
     * Partie décompression (ODP -> fichier)
     */
    
    /**
     * Décompresse un fichier zip à l'adresse indiquée par le dossier
     * @param aDecompresser fichier a decompresser
     * @param supprimer indique si on supprime l'archive
     * @throws IOException si un probleme avec les fichiers est rencontre
     */
    public static void decompress(File aDecompresser, boolean supprimer) 
           throws IOException {
        
        String nom = aDecompresser.toString();
        nom = nom.substring(0, nom.lastIndexOf('.'));
        
        File nomDecompress = new File(nom);
        
        /*  */
        ZipInputStream zis = new ZipInputStream(
                             new BufferedInputStream(
                             new FileInputStream(
                                     aDecompresser.getCanonicalFile()))
                             );
        
        ZipEntry entree;
        try {    
            // Parcourt tous les fichiers
            while (null != (entree = zis.getNextEntry())) {
                File fichier = new File(nomDecompress.getCanonicalPath(),
                                        entree.getName());
                
                /* si le fichier existe deja */
                if (fichier.exists()) {
                    fichier.delete();
                }
                    
                /* Création des dossiers */
                if (entree.isDirectory()) {
                    fichier.mkdirs();
                    continue;
                }
                
                fichier.getParentFile().mkdirs();
                OutputStream fos = new BufferedOutputStream(
                                   new FileOutputStream(fichier));

                /* ecriture des fichiers */
                try {
                    try {
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while (-1 != (bytesRead = zis.read(buffer)))
                            fos.write(buffer, 0, bytesRead);
                    } finally {
                        fos.close();
                    }
                } catch (IOException ioe) {
                    fichier.delete();
                    throw ioe;
                }
            }
        } finally {
            /* fermeture */
            zis.close();
        }

        /* si vrai on supprime l'archive */
        if (supprimer) {
            aDecompresser.delete();
        }
    }
    
    
    /**
     * Main de test
     * @param args
     */
    public static void main(String[] args) {
        try {
            //decompress(new File("essai.odp"), true);
            compressFile("essai");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
