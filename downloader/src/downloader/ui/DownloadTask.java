package downloader.ui;

import downloader.fc.Downloader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class DownloadTask extends BorderPane {
	Downloader downloader;
	ProgressBar progressBar;
  Thread thread;
  Label nomUrl;
  
  DownloadTask(String URL){
  	downloader = new Downloader(URL);
  	progressBar = new ProgressBar();
  	progressBar.setMaxWidth(Double.MAX_VALUE);
  	thread = new Thread(downloader);
  	nomUrl = new Label(URL);

  	this.setTop(nomUrl);
  	this.setCenter(progressBar);
  	
  	// On abonne la ProgressBar aux mises à jour de la progression du téléchargement
  	progressBar.progressProperty().bind(downloader.progressProperty());
  	
    thread.start();
  }

	public void run() {
		downloader.run();
	}
}
