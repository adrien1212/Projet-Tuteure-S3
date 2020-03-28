package FXML;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import outils.OutilFichier;
import outils.Zip;

/**
 * Controller de l'interface graphique de l'application
 * @author groupe 8
 */
public class LancementFXMLControler extends Application {

    @FXML
    TextField fichierSourceTF;

    @FXML
    TextField fichierDestinationTF;

    @FXML
    Button validerBTN, genererBTN, annulerBTN, quitterBTN, ouvrirBTN;

    @FXML
    CheckBox cacherCheckBox, grasCheckBox, italiqueCheckBox, souligneCheckBox, sansBaliseCheckBox;

    @FXML
    Label messageAucuneBaliseLabel, erreurLabel;

    @FXML
    AnchorPane anchorPaneID;

    
    String fichierDestination;
    
    /**
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {  
        Parent root = FXMLLoader.load(getClass().getResource("Lancement.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param event
     * @throws IOException
     */
    public void cliquerValiderBTN(ActionEvent event) throws IOException {
        String fichierSource = fichierSourceTF.getText();
        fichierDestination = fichierDestinationTF.getText();
        String fichierSourceSansODP = null;

        int nbBaliseTrouvees = 0;
        OutilFichier.cacherPresent = false;

        if(fichierSource.trim().isEmpty() || fichierDestination.trim().isEmpty()) {
            erreurLabel.setText("Veuillez saisir les chemins");
        } else {
            erreurLabel.setText("");


            /* On vérifie que l'extension est .odp */
            if(isPathValid(fichierSource) && isPathValid(fichierDestination)) {
                /* dezipper l'ODP */
                try {
                    Zip.decompress(new File(fichierSource), false);
                } catch (IOException e) {
                    System.out.println("Probleme avec la decompression du fichier " + fichierSource);
                }

                fichierSourceSansODP = fichierSource.substring(0, fichierSource.lastIndexOf('.'));

                try {
                    if(OutilFichier.verifierFichier(fichierSourceSansODP + "/content.xml")) {
                        /* On indente le fichier, le fichier "xml.txt" est cree */
                        OutilFichier.indenteFichier(fichierSourceSansODP + "/content.xml", "xmlTemp.txt");

                        /* Lit le fichier et trouve les balises afin d'appliquer un style */
                        OutilFichier.lectureFichier("xmlTemp.txt");

                        griserElement(); 

                        if(OutilFichier.cacherPresent) {
                            cacherCheckBox.setDisable(false);
                            nbBaliseTrouvees++;
                        }
                        if(OutilFichier.grasPresent) {
                            grasCheckBox.setDisable(false);
                            nbBaliseTrouvees++;
                        }
                        if(OutilFichier.italiquePresent) {
                            italiqueCheckBox.setDisable(false);
                            nbBaliseTrouvees++;
                        }
                        //System.out.println(nbBaliseTrouvees);
                        if(OutilFichier.soulignePresent) {
                            souligneCheckBox.setDisable(false);
                            nbBaliseTrouvees++;
                        }


                        /* Affiche un message si aucune balise trouvée */
                        if(nbBaliseTrouvees == 0) {
                            messageAucuneBaliseLabel.setText("Aucune balise n'a été trouvé");
                            griserElement();
                        } else {
                            // on dégrise les autres éléments hors balises
                            messageAucuneBaliseLabel.setText("");
                            genererBTN.setDisable(false); 
                            annulerBTN.setDisable(false);
                            ouvrirBTN.setDisable(false);
                            sansBaliseCheckBox.setDisable(false);
                        }
                    } else {
                        erreurLabel.setText("Les balises ne sont pas correctement imbriquée");
                        griserElement();
                    }
                } catch (IOException e) {
                    erreurLabel.setText("Fichier source non trouvé");
                    griserElement();
                }

            } else {
                erreurLabel.setText("Extension du fichier incorrecte");
                griserElement();
            }
        }
    }

    private void griserElement() {
        genererBTN.setDisable(true); 
        annulerBTN.setDisable(true);

        sansBaliseCheckBox.setDisable(true);
        cacherCheckBox.setDisable(true);
        grasCheckBox.setDisable(true);
        italiqueCheckBox.setDisable(true);
        souligneCheckBox.setDisable(true);
    }
    
    private void decocherElement() {
        cacherCheckBox.setSelected(false);
        sansBaliseCheckBox.setSelected(false);
        grasCheckBox.setSelected(false);
        italiqueCheckBox.setSelected(false);
        souligneCheckBox.setSelected(false);
    }

    /**
     * Vérifie si le chemin saisie est valide c'est à dire que c'est un chemin correct
     * et qu'il finit par l'extension .odp
     * @param path chemin à vérifier
     * @return true si le chemain est correct
     *         false sinon
     */
    public static boolean isPathValid(String path) {
        String extension;
        boolean estValidePath;

        try {
            Paths.get(path);
            extension = path.substring(path.lastIndexOf('.'));
        } catch (Exception ex) {
            return false;
        }

        estValidePath = extension.equals(".odp") ? true : false;
        return estValidePath;
    }


    /**
     * TODO commenter le rôle de cette méthode
     * @param event
     * @throws IOException 
     */
    public void cliquerGenererBTN(ActionEvent event) throws IOException {
        ArrayList<String> baliseAAppliquerModification = new ArrayList<String>();

        String nomFichier = fichierSourceTF.getText(); // nom du fichier ODP a convertir

        String fichierSourceSansODP = nomFichier.substring(0, nomFichier.lastIndexOf('.'));

        /* Si rien n'est selectionné, on fait juste une duplication du fichier */
        if(!cacherCheckBox.isSelected() && !grasCheckBox.isSelected() && !italiqueCheckBox.isSelected() 
                && !souligneCheckBox.isSelected()	&& !sansBaliseCheckBox.isSelected()) {
            erreurLabel.setText("Aucune case cochée");
        } else {
            /* On récupère les balises où on va devoir appliquer une modification */
            if(cacherCheckBox.isSelected()) {
                baliseAAppliquerModification.add("<cacher>");
            } 
            if(grasCheckBox.isSelected()) {
                baliseAAppliquerModification.add("<gras>");
            }
            if(italiqueCheckBox.isSelected()) {
                baliseAAppliquerModification.add("<italique>");
            }
            if(souligneCheckBox.isSelected()) {
                baliseAAppliquerModification.add("<souligne>");
            }

            OutilFichier.lectureFichier("xmlTemp.txt");
            /* On génère le document destination */ 
            if(baliseAAppliquerModification.isEmpty()) {
                OutilFichier.ecrireFichierSansBalise("xmlTemp.txt", fichierSourceSansODP + "/content.xml");
            } else {
                OutilFichier.ecrireFichier("xmlTemp.txt", fichierSourceSansODP + "/content.xml", baliseAAppliquerModification);
            }


            /* recompression en ODP */
            try {
                Zip.compressFile(fichierSourceSansODP, fichierDestinationTF.getText());
            } catch (IOException e) {
                System.out.println("Probleme avec la compression du fichier essai");
            }

            /* On affiche la popup comme quoi la génération est réussite*/
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("messageGenerationOK.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage st2 = new Stage();
            st2.setScene(scene);
            st2.show();

            /* On enlève les sélections de l'utilisateur */
            decocherElement();
            griserElement();

            /* On supprimer les fichiers temporaires créés */
            OutilFichier.supprFichier(new File(fichierSourceSansODP));
        }
    }

    /**
     * @param event
     * @throws IOException
     */
    public void cliquerAnnulerBTN(ActionEvent event) throws IOException {
        decocherElement();
    }

    /**
     * Permet de choisir un fichier source
     */
    public void cliqueBrowseBTN(ActionEvent event) {
        final FileChooser fc = new FileChooser();

        Stage stage2 = (Stage) anchorPaneID.getScene().getWindow();
        File file = fc.showOpenDialog(stage2);

        if(file != null) {
            fichierSourceTF.setText(file.getAbsolutePath());
        }
    }

    /**
     * Permet de choisir un fichier destination
     * @param event
     */
    public void cliqueBrowseBTN2(ActionEvent event) {
        final DirectoryChooser dc = new DirectoryChooser();

        Stage stage2 = (Stage) anchorPaneID.getScene().getWindow();
        File file = dc.showDialog(stage2);

        if(file != null) {
            fichierDestinationTF.setText(file.getAbsolutePath());
        }
    }

    
    public void cliqueOuvrirBTN(ActionEvent event) throws IOException {
    	try {
    		if (Desktop.isDesktopSupported()) {
    			Desktop.getDesktop().open(new File(fichierDestination));
    		}
    	} catch (IOException ioe) {
    		ioe.printStackTrace();
    	}
    }
    
    

    public void cliqueAideBTN(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("aide.fxml"));

        Parent root = fxmlLoader.load();
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
    
    

    @FXML
    private void cliqueQuitterBTN() {
        try {
            String nomFichier = fichierSourceTF.getText(); // nom du fichier ODP a convertir
            String fichierSourceSansODP = nomFichier.substring(0, nomFichier.lastIndexOf('.'));
            OutilFichier.supprFichier(new File(fichierSourceSansODP));
        } catch (Exception e) {}
        
        Stage stage = (Stage) quitterBTN.getScene().getWindow();
        stage.close();
    }

    /**
     * Lancement de l'application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
