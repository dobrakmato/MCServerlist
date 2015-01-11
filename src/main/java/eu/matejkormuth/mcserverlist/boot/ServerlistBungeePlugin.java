package eu.matejkormuth.mcserverlist.boot;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import com.google.common.io.Files;

import eu.matejkormuth.mcserverlist.Comunicator;
import eu.matejkormuth.mcserverlist.gatherers.BukkitServerInformationGatherer;
import eu.matejkormuth.mcserverlist.gatherers.BungeeCordServerInfomationGatherer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class ServerlistBungeePlugin extends Plugin {
	private final static String API_ROOT = "http://minecraftservery.eu/api/";

	private String token = null;
	private Comunicator comunicator;

	@Override
	public void onEnable() {
		this.getLogger().info("Initializing Serverlist plugin...");
		
		// Initialize comunicator.
		this.comunicator = new Comunicator(API_ROOT,
				new BungeeCordServerInfomationGatherer(true));

		// Read from config.
		this.loadToken();

		// Register if can't find token.
		if (this.token == null) {
			this.getLogger().info("Registering on serverlist...");
			this.token = this.comunicator.registerNew().getString("token", null);
			
			if(this.token == null) {
				this.getLogger().severe("Can't obtain indentification token from serverlist. Giving up!");
				return;
			}
			
			this.getLogger().info("This server's identification token will be printed! Do not give this token to anyone, because It allows others to preted as Your server!");
			this.getLogger().info("Token will be stored in plugin configuration.");
			this.getLogger().info("Token: " + this.token);
		}

		// Report state.
		this.comunicator.setToken(this.token);
		this.getLogger().info("Reporting state...");
		this.comunicator.reportAsync();

		// Start periodic sending.
		this.getProxy().getScheduler().schedule(this, new Runnable() {
			@Override
			public void run() {
				ServerlistBungeePlugin.this.comunicator.reportAsync();
			}
		}, 0L, 1L, TimeUnit.MINUTES);
	}

	@Override
	public void onDisable() {
		// Shutdown comunicator.
		this.comunicator.shutdown();
		// Save config.
		this.saveToken();
	}

	private void saveToken() {
		try {
			Files.write(this.token, new File(this.getDataFolder()
					.getAbsolutePath() + "token.secure"),
					Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadToken() {
		try {
			this.token = Files.readFirstLine(
					new File(this.getDataFolder().getAbsolutePath()
							+ "token.secure"), Charset.forName("UTF-8")).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
