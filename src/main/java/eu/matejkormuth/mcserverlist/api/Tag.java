package eu.matejkormuth.mcserverlist.api;

import java.io.Serializable;

public final class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final char HASH = '#';
	
	private final String name;
	
	public static Tag of(final String hashtag) {
		return new Tag(hashtag.trim().substring(1));
	}
	
	public Tag(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getHashtag() {
		return HASH + this.name;
	}
}
