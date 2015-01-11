package eu.matejkormuth.mcserverlist.tags;

import java.util.Collection;

import eu.matejkormuth.mcserverlist.api.Server;
import eu.matejkormuth.mcserverlist.api.Tag;

public abstract class TagMapping {
	abstract Collection<Tag> provideTags(Server server);
}
