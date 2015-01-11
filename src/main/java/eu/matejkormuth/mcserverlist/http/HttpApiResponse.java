package eu.matejkormuth.mcserverlist.http;

import eu.matejkormuth.mcserverlist.json.JsonObject;

public class HttpApiResponse {
	private final JsonObject resposne;

	public HttpApiResponse(final String json) {
		this.resposne = JsonObject.readFrom(json);
	}

	public int getErrorCode() {
		return this.resposne.getInt("errorCode", 0);
	}

	public String getError() {
		return this.resposne.getString("errorMessage", "ok");
	}

	public boolean isSuccessfull() {
		return this.getErrorCode() == 0;
	}

	public JsonObject getResposne() {
		return this.resposne.get("response").asObject();
	}
}
