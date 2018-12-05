package downloader.fc;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.locks.ReentrantLock;
import java.net.MalformedURLException;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.beans.property.ReadOnlyDoubleProperty;


public class Downloader extends Task {
	public static final int CHUNK_SIZE = 1024;

	URL url;
	int content_length;
	BufferedInputStream in;
	
	String filename;
	File temp;
	FileOutputStream out;

	int size = 0;
	int count = 0;
	
	private ReentrantLock playPauseLock = new ReentrantLock();
	
	public Downloader(String uri) {
		try {
			url = new URL(uri);
			
			URLConnection connection = url.openConnection();
			content_length = connection.getContentLength();
			
			in = new BufferedInputStream(connection.getInputStream());
			
			String[] path = url.getFile().split("/");
			filename = path[path.length-1];
			temp = File.createTempFile(filename, ".part");
			out = new FileOutputStream(temp);
		}
		catch(MalformedURLException e) { throw new RuntimeException(e); }
		catch(IOException e) { throw new RuntimeException(e); }
	}

	public String toString() {
		return url.toString();
	}
	
	protected String download() throws InterruptedException {
		byte buffer[] = new byte[CHUNK_SIZE];
		
		while(count >= 0) {
			try {
				out.write(buffer, 0, count);
			}
			catch(IOException e) { continue; }
			
			size += count;
			updateProgress(1.*size/content_length, 1.);
			Thread.sleep(1000);
			
			try {
				count = in.read(buffer, 0, CHUNK_SIZE);
			}
			catch(IOException e) { continue; }
		}
		
		if(size < content_length) {
			temp.delete();
			throw new InterruptedException();
		}
			
		temp.renameTo(new File(filename));
		return filename;
	}
	
	public void run() {
		try {
			call();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object call() throws Exception {
		playPauseLock.lock();
		byte buffer[] = new byte[CHUNK_SIZE];
		
		while(count >= 0) {
			try {
				out.write(buffer, 0, count);
			}
			catch(IOException e) { continue; }
			
			size += count;
			updateProgress(1.*size/content_length, 1.);
			playPauseLock.unlock();
			Thread.sleep(1000);			
			playPauseLock.lock();
			
			try {
				count = in.read(buffer, 0, CHUNK_SIZE);
			}
			catch(IOException e) { continue; }
		}
		
		if(size < content_length) {
			if(playPauseLock.isLocked()) {
				playPauseLock.unlock();
			}
			temp.delete();
			throw new InterruptedException();
		}
			
		temp.renameTo(new File(filename));
		return filename;
	}
	
	public void remove() {
		if(playPauseLock.isLocked()) {
			playPauseLock.unlock();
		}
		temp.delete();
		this.cancel();
	}
	
	public void playPause(Button button) {
		if (button.getText().equals("||")) {
			playPauseLock.lock();
			button.setText("▶");
		} else if (button.getText().equals("▶")) {
			playPauseLock.unlock();
			button.setText("||");
		}
	}
};
