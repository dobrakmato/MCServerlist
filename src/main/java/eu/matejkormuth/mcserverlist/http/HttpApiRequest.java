package eu.matejkormuth.mcserverlist.http;

import eu.matejkormuth.mcserverlist.json.JsonObject;

public abstract class HttpApiRequest {
	private static final String JSON_REQUEST_KEY = "request";

	private final String relPath;
	private final JsonObject json;

	public HttpApiRequest(final String relPath) {
		this.relPath = relPath;
		this.json = new JsonObject();
		// Initialize request object.
		this.json.add(JSON_REQUEST_KEY, new JsonObject());
	}

	public String getRelPath() {
		return this.relPath;
	}

	public JsonObject getRequestData() {
		return this.json.get(JSON_REQUEST_KEY).asObject();
	}

	public String toJson() {
		return this.json.toString();
	}
}
