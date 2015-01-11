package eu.matejkormuth.mcserverlist.api;

import java.io.Serializable;

import eu.matejkormuth.mcserverlist.api.enums.MinecraftVersion;
import eu.matejkormuth.mcserverlist.api.enums.ServerSoftware;

public final class Version implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final MinecraftVersion mcversion;
	private final ServerSoftware serverSoftware;
	private final String serverVersion;

	public Version(final MinecraftVersion mcversion,
			final ServerSoftware serverSoftware, final String serverVersion) {
		this.mcversion = mcversion;
		this.serverSoftware = serverSoftware;
		this.serverVersion = serverVersion;
	}

	public MinecraftVersion getMinecraftVersion() {
		return mcversion;
	}

	public ServerSoftware getServerSoftware() {
		return serverSoftware;
	}
	
	public String getServerVersion() {
		return serverVersion;
	}
}
