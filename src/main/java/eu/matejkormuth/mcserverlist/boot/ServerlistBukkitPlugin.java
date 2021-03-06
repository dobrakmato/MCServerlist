package eu.matejkormuth.mcserverlist.boot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import eu.matejkormuth.mcserverlist.Comunicator;
import eu.matejkormuth.mcserverlist.gatherers.BukkitServerInformationGatherer;
import eu.matejkormuth.mcserverlist.json.JsonObject;

public class ServerlistBukkitPlugin extends JavaPlugin {
	private final static String API_ROOT = "http://minecraftservery.eu/api/";

	private String token = null;
	private Comunicator comunicator;

	@Override
	public void onEnable() {
		this.getLogger().info("Initializing Serverlist plugin...");

		this.getCommand("sltoken").setExecutor(this);

		// Initialize comunicator.
		this.comunicator = new Comunicator(API_ROOT,
				new BukkitServerInformationGatherer(true));

		// Read from config.
		this.token = this.getConfig().getString("token");

		// Register if can't find token.
		if (this.token == null) {
			this.getLogger().info("Registering on serverlist...");
			JsonObject resp = this.comunicator.registerNew();
			if (resp != null) {
				this.token = resp.getString("token", null);
			}

			if (this.token == null) {
				this.getLogger()
						.severe("Can't obtain indentification token from serverlist. Giving up!");
				this.getPluginLoader().disablePlugin(this);
				return;
			}

			this.getLogger()
					.info("This server's identification token will be printed! Do not give this token to anyone, because It allows others to preted as Your server!");
			this.getLogger().info(
					"Token will be stored in plugin configuration.");
			this.getLogger().info("Token: " + this.token);
		}

		// Report state.
		this.comunicator.setToken(this.token);
		this.getLogger().info("Reporting state...");
		this.comunicator.reportAsync();

		// Start periodic sending.
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				ServerlistBukkitPlugin.this.comunicator.reportAsync();
			}
		}, 0L, 60L);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (command.getName().equals("sltoken")) {
			if (sender.hasPermission("serverlist.token")) {
				sender.sendMessage(ChatColor.GREEN + "Token:" + this.token);
			} else {
				sender.sendMessage(ChatColor.RED
						+ "You don't have enought permissions!");
			}
			return true;
		}
		return false;
	}

	@Override
	public void onDisable() {
		// Shutdown comunicator.
		this.comunicator.shutdown();
		// Save config.
		this.saveConfig();
	}
}
