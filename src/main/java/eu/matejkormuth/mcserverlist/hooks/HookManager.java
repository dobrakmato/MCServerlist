package eu.matejkormuth.mcserverlist.hooks;

import java.util.ArrayList;
import java.util.List;

import eu.matejkormuth.mcserverlist.api.Server;
import eu.matejkormuth.mcserverlist.hooks.concrete.DynmapHook;

public class HookManager {
	private final List<Hook<?>> hooks;
	private final Server server;
	
	public HookManager(final Server server) {
		this.hooks = new ArrayList<Hook<?>>();
		this.server = server;
		
		addHook(new DynmapHook());
	}
	
	public void addHook(final Hook<?> hook) {
		hook.setLocalServer(this.server);
		this.hooks.add(hook);
	}
}
