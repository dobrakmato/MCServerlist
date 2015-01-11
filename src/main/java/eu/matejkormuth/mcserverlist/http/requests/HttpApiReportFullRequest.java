package eu.matejkormuth.mcserverlist.http.requests;

import eu.matejkormuth.mcserverlist.api.Plugin;
import eu.matejkormuth.mcserverlist.api.Server;
import eu.matejkormuth.mcserverlist.api.Tag;
import eu.matejkormuth.mcserverlist.http.HttpApiRequest;
import eu.matejkormuth.mcserverlist.json.JsonArray;
import eu.matejkormuth.mcserverlist.json.JsonObject;

public class HttpApiReportFullRequest extends HttpApiRequest {

	public HttpApiReportFullRequest(final Server server) {
		super("report.php");

		this.addPlugins(server);
		this.addPlayers(server);
		this.addBans(server);
		this.addTags(server);
		this.addBasicInfo(server);
	}

	private void addBasicInfo(final Server server) {
		if (server.getName() != null) {
			this.getRequestData().add("name", server.getName());
		}
		if (server.getIPAddress() != null) {
			this.getRequestData().add("ip", server.getIPAddress());
		}
		if (server.getMotd() != null) {
			this.getRequestData().add("motd", server.getMotd());
		}
		if (server.getWeb() != null) {
			this.getRequestData().add("web", server.getWeb().toString());
		}

		this.getRequestData().add("onlineMode", server.isOnlineModeEnabled());

		this.getRequestData().add("version_server",
				server.getVersion().getServerVersion());
		this.getRequestData().add("software",
				server.getVersion().getServerSoftware().name());
		this.getRequestData().add("version_minecraft",
				server.getVersion().getMinecraftVersion().name());
	}

	private void addBans(final Server server) {

	}

	private void addTags(final Server server) {
		JsonArray tagsArray = new JsonArray();
		for (Tag tag : server.getTags()) {
			tagsArray.add(tag.getName());
		}

		this.getRequestData().add("tags", tagsArray);
	}

	private void addPlayers(final Server server) {
		JsonArray playerArray = new JsonArray();
		for (String player : server.getPlayers()) {
			playerArray.add(player);
		}

		this.getRequestData().add("players", playerArray);
		this.getRequestData().add("maxPlayers", server.getMaxPlayers());
	}

	private void addPlugins(final Server server) {
		JsonArray pluginsArray = new JsonArray();
		for (Plugin p : server.getPlugins()) {
			JsonObject pluginJsonObject = new JsonObject();

			pluginJsonObject.add("name", p.getName());
			pluginJsonObject.add("version", p.getVersion());
			pluginJsonObject.add("mainClass", p.getMainClass());

			pluginsArray.add(pluginJsonObject);
		}

		this.getRequestData().add("plugins", pluginsArray);
	}

}
