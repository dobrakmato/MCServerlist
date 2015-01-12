package eu.matejkormuth.mcserverlist.hooks.concrete;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapCore;
import org.dynmap.bukkit.DynmapPlugin;

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

		try {
			// Some reflection.
			Field coreField = DynmapPlugin.class.getDeclaredField("core");
			if (!coreField.isAccessible()) {
				coreField.setAccessible(true);
			}
			DynmapCore core = (DynmapCore) coreField.get(DynmapPlugin.plugin);
			this.getLocalServer().setSpecial("dynmap",
					core.configuration.getString("webserver-bindaddress", Bukkit.getServer().getIp()) 
					+ ":"
					+ core.configuration.getString("webserver-port", "8123"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
