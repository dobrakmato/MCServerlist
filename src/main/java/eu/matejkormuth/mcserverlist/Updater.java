package eu.matejkormuth.mcserverlist;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Updater {
	private static final String DOWNLOAD_URL = "http://minecraftservery.eu/mcservery.jar";
	
	public Updater() {
		this.checkForUpdates();
	}
	
	public void checkForUpdates() {
		this.checkForMappingUpdate();
		this.checkForJarUpdate();
	}

	public void checkForMappingUpdate() {
		// TODO: Download new mappings.
	}

	public void checkForJarUpdate() {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(DOWNLOAD_URL);
			connection = (HttpURLConnection) url.openConnection();
			
			// TODO: Make updater.
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
		}
	}
}
