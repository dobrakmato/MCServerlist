package eu.matejkormuth.mcserverlist.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPluginHook extends Hook<JavaPlugin> {
	private final String pluginName;

	public BukkitPluginHook(final String pluginName) {
		this.pluginName = pluginName;
	}

	@Override
	public boolean isTargetAvailable() {
		return Bukkit.getPluginManager().getPlugin(this.pluginName) != null;
	}

	@Override
	public JavaPlugin getHookTarget() {
		return (JavaPlugin) Bukkit.getPluginManager()
				.getPlugin(this.pluginName);
	}

	public void onPluginFound(final JavaPlugin plugin) {
	}

	public void onPluginNotFound(final JavaPlugin plugin) {
	}

	@Override
	void checkForHook() {
		if (this.isTargetAvailable()) {
			this.onPluginFound(this.getHookTarget());
		} else {
			this.onPluginNotFound(this.getHookTarget());
		}
	}
}
