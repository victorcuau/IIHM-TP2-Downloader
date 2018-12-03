package downloader.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
				TextField champUrl = new TextField("http://iihm.imag.fr/index.html");
				panneau_add.setCenter(champUrl);
				
				// Création et paramètres - Bouton "add"
				Button ButtonPlus = new Button("add");
				panneau_add.setRight(ButtonPlus);
				ButtonPlus.setOnAction(new EventHandler<ActionEvent>() {
		      public void handle(ActionEvent event) {
		        DownloadTask cdownloader = new DownloadTask(champUrl.getText());
		        vbox.getChildren().add(cdownloader);
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
