package eu.matejkormuth.mcserverlist.tags;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.matejkormuth.mcserverlist.api.Server;
import eu.matejkormuth.mcserverlist.api.Tag;

public class TagResolver {
	private final List<TagMapping> mappings;
	
	public TagResolver(final TagMapping... mappings) {
		this.mappings = Arrays.asList(mappings);
	}
	
	public Set<Tag> resolve(final Server server) {
		Set<Tag> tags = new HashSet<Tag>();
		
		for(TagMapping mapping : this.mappings) {
			tags.addAll(mapping.provideTags(server));
		}
		
		return tags;
	}
}
