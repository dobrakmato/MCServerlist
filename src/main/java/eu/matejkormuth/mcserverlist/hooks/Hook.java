package eu.matejkormuth.mcserverlist.hooks;
import eu.matejkormuth.mcserverlist.api.Server;

public abstract class Hook<T> {
	private Server thisServer;
	
	public Server getLocalServer() {
		return thisServer;
	}
	
	public abstract boolean isTargetAvailable();
	
	public abstract T getHookTarget();
	
	abstract void checkForHook();
}
