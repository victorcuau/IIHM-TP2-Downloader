package downloader.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	public void start(Stage stage) {
		// Création et paramètres - Fenêtre principale
		BorderPane fenetre_principale = new BorderPane();
		fenetre_principale.setPrefSize(600, 300);
		
		// Création et paramètres - Liste des téléchargements
		VBox vbox = new VBox();
		ScrollPane panneau_download = new ScrollPane(vbox);
		fenetre_principale.setCenter(panneau_download);
		
		// Création et paramètres - Bandeau d'ajout de téléchargement
		BorderPane panneau_add = new BorderPane();
		fenetre_principale.setBottom(panneau_add);
		panneau_download.setFitToWidth(true); // Taille de la Progress Bar adaptée à la taille de la fenêtre
		
				// Création et paramètres - Zone de texte URL
				TextField champUrl = new TextField("http://iihm.imag.fr/index.html"); // URL de test déjà remplie
				champUrl.setPromptText("Input an URL ..."); // Texte si champ vide
				panneau_add.setCenter(champUrl);
				
				// Création et paramètres - Bouton "add"
				Button ButtonPlus = new Button("Ajouter");
				panneau_add.setRight(ButtonPlus);
				
				// Actions lors du clic sur le bouton AJOUTER
				ButtonPlus.setOnAction(new EventHandler<ActionEvent>() {
		      public void handle(ActionEvent event) {
		        DownloadTask cdownloader = new DownloadTask(champUrl.getText());
		        vbox.getChildren().add(cdownloader);
		        
		        cdownloader.progressBar.setStyle("-fx-accent: blue;"); // Couleur de la ProgressBar
		        
		        Button buttonSuppr = new Button("✖");
		      	Button buttonPausePlay = new Button("||");
		      	HBox buttonBar = new HBox();
		      	buttonBar.getChildren().addAll(buttonSuppr,buttonPausePlay);
		      	buttonBar.setSpacing(1.5);
		      	cdownloader.setRight(buttonBar);
		      	
		      	// Actions lors du clic sur le bouton SUPPRIMER
			    	buttonSuppr.setOnAction(new EventHandler<ActionEvent>() {
				      public void handle(ActionEvent event) {
				      	cdownloader.downloader.remove();
				      	vbox.getChildren().remove(cdownloader);
				      }
			    	});
			    	
			    	// Actions lors du clic sur le bouton PLAY/PAUSE
			    	buttonPausePlay.setOnAction(new EventHandler<ActionEvent>() {
				      public void handle(ActionEvent event) {
				      	cdownloader.downloader.playPause(buttonPausePlay);
				      	
				      	// ProgressBar grisée si téléchargement en pause
				      	if (buttonPausePlay.getText().equals("▶")) {
				      		cdownloader.progressBar.setStyle("-fx-accent: grey;");
				      	}
				      	
				      	// ProgressBar bleue si reprise du téléchargement
				      	if (buttonPausePlay.getText().equals("||")) {
				      		cdownloader.progressBar.setStyle("-fx-accent: blue;");
				      	}
				      	
				      }
			    	});
			    	
		      }
		    });
		
		stage.setTitle("Downloader by Leya & Victor");
		stage.setScene(new Scene(fenetre_principale));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
