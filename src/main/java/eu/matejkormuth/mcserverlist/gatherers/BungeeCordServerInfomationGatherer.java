package eu.matejkormuth.mcserverlist.gatherers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
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

public class BungeeCordServerInfomationGatherer implements InformationGatherer,
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
	private String motd;
	private int maxPlayers;

	private TagResolver tagResolver;

	public BungeeCordServerInfomationGatherer() {
		this(false);
	}

	public BungeeCordServerInfomationGatherer(final boolean gatherImmidiately) {
		this.plugins = new ArrayList<Plugin>();
		this.tags = new HashSet<Tag>();
		this.players = new ArrayList<String>();

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

	@SuppressWarnings("deprecation")
	@Override
	public void gatherAll() {
		this.onlineModeEnabled = ProxyServer.getInstance().getConfig()
				.isOnlineMode();

		this.maxPlayers = ProxyServer.getInstance().getConfig()
				.getPlayerLimit();

		this.name = ProxyServer.getInstance().getName();
		this.motd = "can't get"; // TODO: Get bungee motd
		this.description = null;
		this.web = null;
		this.ipAddress = "can't get"; // TODO: Make reverse dns lookup.

		// TODO: Guess minecraft version.
		this.version = new Version(MinecraftVersion.MC1_8_1,
				ServerSoftware.BUNGEECORD, ProxyServer.getInstance()
						.getVersion());

		this.gatherPlayers();

		this.gatherPlugins();

		// Add tags by tag resolver.
		this.tags.addAll(this.tagResolver.resolve(this));
	}

	@Override
	public void gatherPlugins() {
		// TODO: Figure out what do now?
	}

	@SuppressWarnings("deprecation")
	@Override
	public void gatherPlayers() {
		this.players.clear();

		for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			this.players.add(p.getName());
		}

		this.maxPlayers = ProxyServer.getInstance().getConfig()
				.getPlayerLimit();
	}

	@Override
	public void gatherBans() {
		// TODO: Figure out what do now?
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
}
