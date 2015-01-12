package eu.matejkormuth.mcserverlist.gatherers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.matejkormuth.mcserverlist.api.InformationGatherer;
import eu.matejkormuth.mcserverlist.api.Plugin;
import eu.matejkormuth.mcserverlist.api.Server;
import eu.matejkormuth.mcserverlist.api.Tag;
import eu.matejkormuth.mcserverlist.api.Version;
import eu.matejkormuth.mcserverlist.api.enums.MinecraftVersion;
import eu.matejkormuth.mcserverlist.api.enums.ServerSoftware;
import eu.matejkormuth.mcserverlist.tags.BasicTagMapping;
import eu.matejkormuth.mcserverlist.tags.PluginTagMapping;
import eu.matejkormuth.mcserverlist.tags.TagResolver;

public class BukkitServerInformationGatherer implements InformationGatherer,
		Server {
	private final Logger log = Logger.getLogger(this.getClass().getName());
	
	private String name;
	private String description;
	private URL web;
	private boolean onlineModeEnabled;
	private String ipAddress;
	private Version version;
	private List<Plugin> plugins;
	private Set<Tag> tags;
	private List<String> players;
	private Map<String, String> specials;
	private String motd;
	private int maxPlayers;

	private TagResolver tagResolver;

	public BukkitServerInformationGatherer() {
		this(false);
	}

	public BukkitServerInformationGatherer(final boolean gatherImmidiately) {
		this.plugins = new ArrayList<Plugin>();
		this.tags = new HashSet<Tag>();
		this.players = new ArrayList<String>();
		this.specials = new HashMap<String, String>();

		PluginTagMapping pluginTagMapping = null;
		try {
			pluginTagMapping = new PluginTagMapping(this.getClass()
					.getResource("plugins.mapping").openStream());
		} catch (Exception e) {
			log.severe("Can't load plugins.mapping file!");
			e.printStackTrace();
		}

		if (pluginTagMapping == null) {
			pluginTagMapping = new PluginTagMapping();
		}

		this.tagResolver = new TagResolver(new BasicTagMapping(),
				pluginTagMapping);

		if (gatherImmidiately) {
			this.gatherAll();
		}
	}

	@Override
	public void gatherBans() {
		// TODO: Bans
	}

	@Override
	public void gatherAll() {
		this.onlineModeEnabled = Bukkit.getOnlineMode();

		this.maxPlayers = Bukkit.getMaxPlayers();

		this.name = Bukkit.getName();
		this.motd = Bukkit.getMotd();
		this.description = null;
		this.web = null;
		this.ipAddress = Bukkit.getIp(); // TODO: Make reverse dns lookup.

		// TODO: Guess minecraft version.
		if(Bukkit.getVersion().contains("Spigot")) {
			this.version = new Version(MinecraftVersion.MC1_8_1, ServerSoftware.SPIGOT, Bukkit.getVersion());
		} else {
			this.version = new Version(MinecraftVersion.MC1_8_1, ServerSoftware.CRAFTBUKKIT, Bukkit.getVersion());
		}

		this.gatherPlayers();

		this.gatherPlugins();

		// Add tags by tag resolver.
		this.tags.addAll(this.tagResolver.resolve(this));
	}

	@Override
	public void gatherPlayers() {
		this.players.clear();

		for (Player p : Bukkit.getOnlinePlayers()) {
			this.players.add(p.getName());
		}
	}

	@Override
	public void gatherPlugins() {
		this.plugins.clear();

		for (org.bukkit.plugin.Plugin p : Bukkit.getPluginManager()
				.getPlugins()) {
			this.plugins.add(new Plugin(p.getName(), p.getDescription()
					.getVersion(), p.getClass().getCanonicalName(), p
					.isEnabled()));
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public URL getWeb() {
		return this.web;
	}

	@Override
	public boolean isOnlineModeEnabled() {
		return this.onlineModeEnabled;
	}

	@Override
	public String getIPAddress() {
		return this.ipAddress;
	}

	@Override
	public Version getVersion() {
		return this.version;
	}

	@Override
	public List<Plugin> getPlugins() {
		return this.plugins;
	}

	@Override
	public Set<Tag> getTags() {
		return this.tags;
	}

	@Override
	public List<String> getPlayers() {
		return this.players;
	}

	@Override
	public String getMotd() {
		return this.motd;
	}

	@Override
	public int getMaxPlayers() {
		return this.maxPlayers;
	}

	@Override
	public int getPlayerCount() {
		return this.players.size();
	}

	@Override
	public Server toServer() {
		return this;
	}

	@Override
	public void addTag(final Tag tag) {
		this.tags.add(tag);
	}

	@Override
	public void removeTag(final Tag tag) {
		this.tags.remove(tag);
	}

	@Override
	public void setSpecial(String key, String value) {
		this.specials.put(key, value);
	}

	@Override
	public void removeSpecial(String key) {
		this.specials.remove(key);
	}
}
