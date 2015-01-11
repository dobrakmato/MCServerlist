package eu.matejkormuth.mcserverlist.http.requests;

import eu.matejkormuth.mcserverlist.api.Plugin;
import eu.matejkormuth.mcserverlist.api.Server;
import eu.matejkormuth.mcserverlist.http.HttpApiRequest;
import eu.matejkormuth.mcserverlist.json.JsonArray;
import eu.matejkormuth.mcserverlist.json.JsonObject;

public class HttpApiReportPluginsRequest extends HttpApiRequest {

	public HttpApiReportPluginsRequest(final Server server) {
		super("report.php");
		
		JsonArray pluginsArray = new JsonArray();
		for(Plugin p : server.getPlugins()) {
			JsonObject pluginJsonObject = new JsonObject();
			
			pluginJsonObject.add("name", p.getName());
			pluginJsonObject.add("version", p.getVersion());
			pluginJsonObject.add("mainClass", p.getMainClass());
			
			pluginsArray.add(pluginJsonObject);
		}
		
		this.getRequestData().add("plugins", pluginsArray);
	}

}
