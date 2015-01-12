package eu.matejkormuth.mcserverlist.hooks.concrete;

import org.bukkit.plugin.java.JavaPlugin;

import eu.matejkormuth.mcserverlist.api.Tag;
import eu.matejkormuth.mcserverlist.hooks.BukkitPluginHook;

public class DynmapHook extends BukkitPluginHook {
	private static final String DYNMAP_PLUGIN_NAME = "Dynmap";
	
	public DynmapHook() {
		super(DYNMAP_PLUGIN_NAME);
	}
	
	@Override
	public void onPluginFound(final JavaPlugin plugin) {
		this.getLocalServer().addTag(new Tag("dynmap"));
		// TODO: Add special dynmap value.
	}
}
