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
  Button buttonSuppr;
  Button buttonPause;
  Button buttonReprendre;
  HBox buttonBar;
  
  DownloadTask(String URL){
  	downloader = new Downloader(URL);
  	progressBar = new ProgressBar();
  	progressBar.setMaxWidth(Double.MAX_VALUE + 10);
  	thread = new Thread(downloader);
  	nomUrl = new Label(URL);
  	
  	Button buttonSuppr = new Button("X");
  	Button buttonPause = new Button("||");
  	Button buttonReprendre = new Button(">");
  	buttonBar = new HBox();
  	buttonBar.getChildren().addAll(buttonSuppr,buttonPause,buttonReprendre);
  	buttonBar.setHgrow(progressBar, Priority.ALWAYS);
  	buttonBar.setSpacing(1.5);
  	
  	this.setTop(nomUrl);
  	this.setCenter(progressBar);
  	this.setRight(buttonBar);
  	
  	// On abonne la ProgressBar aux mises à jour de la progression du téléchargement
  	downloader.progressProperty().addListener((obs, o, n) -> {
  		Platform.runLater(new Runnable() {
        public void run() {
        	progressBar.setProgress(n.doubleValue());
        }
      });
    });
  	
  	buttonSuppr.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
      	thread.resume();
      	
      }
    });
  	
    thread.start();
  }
  
  // Permet de reprendre un téléchargement
  public void play() {
    //unlock();
  }
  
  // Permet de suspendre un téléchargement
  public void pause() {
    //lock();
  }

	public void run() {
		downloader.run();
	}
}
