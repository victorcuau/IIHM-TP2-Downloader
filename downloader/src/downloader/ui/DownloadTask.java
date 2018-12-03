package downloader.ui;

import downloader.fc.Downloader;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

public class DownloadTask extends ProgressBar {
	Downloader downloader;
  Thread thread;
  
  DownloadTask(String URL){
  	downloader = new Downloader(URL);
  	this.setMaxWidth(Double.MAX_VALUE);
  	thread = new Thread(downloader);
  	
  	// On abonne la ProgressBar aux mises à jour de la progression du téléchargement
  	downloader.progressProperty().addListener((obs, o, n) -> {
  		Platform.runLater(new Runnable() {
        public void run() {
          setProgress(n.doubleValue());
        }
      });
    });
  	
    thread.start();
  }

	public void run() {
		downloader.run();
	}
}
