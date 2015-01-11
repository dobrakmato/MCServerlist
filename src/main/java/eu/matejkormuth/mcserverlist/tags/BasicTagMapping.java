package eu.matejkormuth.mcserverlist.tags;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import eu.matejkormuth.mcserverlist.api.Server;
import eu.matejkormuth.mcserverlist.api.Tag;
import eu.matejkormuth.mcserverlist.api.enums.ServerSoftware;

public class BasicTagMapping extends TagMapping {
	@Override
	Collection<Tag> provideTags(Server server) {
		Set<Tag> tags = new HashSet<Tag>();
		
		this.checkAndAdd(tags, new Tag("online-mode"), server.isOnlineModeEnabled());
		this.checkAndAdd(tags, new Tag("no-plugins"), server.getPlugins().size() == 0);
		this.checkAndAdd(tags, new Tag("craftbukkit"), server.getVersion().getServerSoftware().equals(ServerSoftware.CRAFTBUKKIT));
		this.checkAndAdd(tags, new Tag("spigot"), server.getVersion().getServerSoftware().equals(ServerSoftware.SPIGOT));
		this.checkAndAdd(tags, new Tag("sponge"), server.getVersion().getServerSoftware().equals(ServerSoftware.SPONGE));
		this.checkAndAdd(tags, new Tag("lobby"), server.getVersion().getServerSoftware().equals(ServerSoftware.BUNGEECORD));
		
		return tags;
	}
	
	private void checkAndAdd(final Set<Tag> tags, final Tag tag, final boolean condition) {
		if(condition) {
			tags.add(tag);
		}
	}
}
