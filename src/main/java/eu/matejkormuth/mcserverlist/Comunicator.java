package eu.matejkormuth.mcserverlist;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import eu.matejkormuth.mcserverlist.api.InformationGatherer;
import eu.matejkormuth.mcserverlist.api.Server;
import eu.matejkormuth.mcserverlist.http.HttpApiClient;
import eu.matejkormuth.mcserverlist.http.HttpApiResponse;
import eu.matejkormuth.mcserverlist.http.requests.HttpApiRegisterRequest;
import eu.matejkormuth.mcserverlist.http.requests.HttpApiReportBansRequest;
import eu.matejkormuth.mcserverlist.http.requests.HttpApiReportFullRequest;
import eu.matejkormuth.mcserverlist.http.requests.HttpApiReportPlayersRequest;
import eu.matejkormuth.mcserverlist.http.requests.HttpApiReportPluginsRequest;
import eu.matejkormuth.mcserverlist.json.JsonObject;

public class Comunicator {
	private final Logger log = Logger.getLogger(this.getClass().getName());

	private final HttpApiClient client;
	private final InformationGatherer gatherer;

	public Comunicator(final String apiRoot, final InformationGatherer gatherer) {
		this.client = new HttpApiClient(apiRoot);
		this.gatherer = gatherer;
	}

	public Comunicator(final String apiRoot, final ExecutorService executor,
			final InformationGatherer gatherer) {
		this.client = new HttpApiClient(apiRoot, executor);
		this.gatherer = gatherer;
	}

	public JsonObject registerNew() {
		this.log.info("Registering on serverlist...");
		return this.client.sendSync(new HttpApiRegisterRequest()).getResposne();
	}

	public void reportAsync() {
		this.reportAsync(ReportType.FULL);
	}

	public InformationGatherer getGatherer() {
		return gatherer;
	}

	public Future<HttpApiResponse> reportAsync(final ReportType type) {
		switch (type) {
		case BANS:
			this.gatherer.gatherBans();
			return this.client.sendAsync(new HttpApiReportBansRequest(
					this.gatherer.toServer()));
		case FULL:
			this.gatherer.gatherAll();
			return this.client.sendAsync(new HttpApiReportFullRequest(
					this.gatherer.toServer()));
		case PLAYERS:
			this.gatherer.gatherPlayers();
			return this.client.sendAsync(new HttpApiReportPlayersRequest(
					this.gatherer.toServer()));
		case PLUGINS:
			this.gatherer.gatherPlugins();
			return this.client.sendAsync(new HttpApiReportPluginsRequest(
					this.gatherer.toServer()));
		default:
			throw new UnsupportedOperationException(
					"Can't send this report: Invalid ReportType!");
		}
	}

	public void setToken(String token) {
		this.client.setToken(token);
	}

	public void shutdown() {
		this.log.info("Shutting down HttpClient...");
		this.client.shutdown();
	}

	public enum ReportType {
		FULL, PLUGINS, PLAYERS, BANS;
	}
}
