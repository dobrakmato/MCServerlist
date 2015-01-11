package eu.matejkormuth.mcserverlist.tags;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import eu.matejkormuth.mcserverlist.api.Plugin;
import eu.matejkormuth.mcserverlist.api.Server;
import eu.matejkormuth.mcserverlist.api.Tag;

public class PluginTagMapping extends TagMapping {
	private Map<String, Tag> mapping = new HashMap<String, Tag>();

	// Empty plugin-tag mapping.
	public PluginTagMapping() {
	}

	// Plugin-tag mapping from file.
	public PluginTagMapping(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			String[] parts = null;
			String mainClass = null;
			String tag = null;
			while (reader.ready()) {
				line = reader.readLine();
				parts = line.split(Pattern.quote(":"));
				mainClass = parts[0];
				tag = parts[1];
				if (tag.startsWith("#")) {
					tag = tag.substring(1);
				}
				this.mapping.put(mainClass, new Tag(tag));
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PluginTagMapping(InputStream stream) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream));
			String line = null;
			String[] parts = null;
			String mainClass = null;
			String tag = null;
			while (reader.ready()) {
				line = reader.readLine();
				parts = line.split(Pattern.quote(":"));
				mainClass = parts[0];
				tag = parts[1];
				if (tag.startsWith("#")) {
					tag = tag.substring(1);
				}
				this.mapping.put(mainClass, new Tag(tag));
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addMapping(String mainClass, String tagName) {
		this.mapping.put(mainClass, new Tag(tagName));
	}

	@Override
	Collection<Tag> provideTags(Server server) {
		Set<Tag> tags = new HashSet<Tag>();
		for (Plugin plugin : server.getPlugins()) {
			if (mapping.containsKey(plugin.getMainClass())) {
				tags.add(mapping.get(plugin.getMainClass()));
			}
		}
		return tags;
	}
}
