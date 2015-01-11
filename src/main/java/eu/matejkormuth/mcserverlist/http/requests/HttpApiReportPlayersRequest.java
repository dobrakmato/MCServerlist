package eu.matejkormuth.mcserverlist.http.requests;

import eu.matejkormuth.mcserverlist.api.Server;
import eu.matejkormuth.mcserverlist.http.HttpApiRequest;
import eu.matejkormuth.mcserverlist.json.JsonArray;

public class HttpApiReportPlayersRequest extends HttpApiRequest {
	public HttpApiReportPlayersRequest(final Server server) {
		super("report.php");
		
		JsonArray playerArray = new JsonArray();
		for(String player : server.getPlayers()) {
			playerArray.add(player);
		}
		
		this.getRequestData().add("players", playerArray);
		this.getRequestData().add("maxPlayers", server.getMaxPlayers());
	}
}
