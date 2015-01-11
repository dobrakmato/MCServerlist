package eu.matejkormuth.mcserverlist.api;

import java.io.Serializable;

public class Plugin implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final String version;
	private final String mainClass;
	private final boolean isEnabled;

	public Plugin(final String name, final String version,
			final String mainClass, final boolean isEnabled) {
		this.name = name;
		this.version = version;
		this.mainClass = mainClass;
		this.isEnabled = isEnabled;
	}

	public String getName() {
		return this.name;
	}

	public String getVersion() {
		return this.version;
	}

	public String getMainClass() {
		return this.mainClass;
	}

	public boolean isEnabled() {
		return this.isEnabled;
	}
}
