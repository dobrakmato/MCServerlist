package eu.matejkormuth.mcserverlist.hooks;
import eu.matejkormuth.mcserverlist.api.Server;

public abstract class Hook<T> {
	private Server localServer;
	
	public Server getLocalServer() {
		return localServer;
	}
	
	void setLocalServer(final Server server) {
		this.localServer = server;
	}
	
	public abstract boolean isTargetAvailable();
	
	public abstract T getHookTarget();
	
	abstract void checkForHook();
}
