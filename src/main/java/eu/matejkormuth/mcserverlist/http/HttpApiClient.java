package eu.matejkormuth.mcserverlist.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpApiClient {
	private final String apiRoot;
	private final ExecutorService executor;
	private String token;
	
	public HttpApiClient(final String apiRoot) {
		this(apiRoot, Executors.newFixedThreadPool(1));
	}
	
	public HttpApiClient(final String apiRoot, final ExecutorService executor) {
		this.apiRoot = apiRoot;
		this.executor = executor;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public HttpApiResponse sendSync(final HttpApiRequest request) {
		// Automatically add token to request.
		if(this.token != null) {
			request.getRequestData().add("token", this.token);
		}
		return this.sendSync0(this.apiRoot + request.getRelPath(), request.toJson());
	}
	
	public Future<HttpApiResponse> sendAsync(final HttpApiRequest request) {
		return this.executor.submit(new Callable<HttpApiResponse>() {
			@Override
			public HttpApiResponse call() throws Exception {
				return HttpApiClient.this.sendSync(request);
			}
		});
	}
	
	public void shutdown() {
		this.executor.shutdown();
	}
	
	private HttpApiResponse sendSync0(final String urlstring, final String jsonData) {
		StringBuilder response;
		HttpURLConnection connection = null;
		try {
			URL url = new URL(urlstring);
			connection = (HttpURLConnection) url.openConnection();
			// Send request.
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(jsonData.getBytes().length));
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			DataOutputStream stream = new DataOutputStream(
					connection.getOutputStream());
			stream.writeBytes(jsonData);
			stream.flush();
			stream.close();
			// Get response.
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line = "";
			response = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			return new HttpApiResponse(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
