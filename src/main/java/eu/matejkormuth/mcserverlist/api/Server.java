package eu.matejkormuth.mcserverlist.api;

import java.net.URL;
import java.util.List;
import java.util.Set;

public interface Server {
	String getName();
	
	String getMotd();
	
	int getMaxPlayers();
	
	int getPlayerCount();
	
	String getDescription();
	
	URL getWeb();
	
	boolean isOnlineModeEnabled();
	
	String getIPAddress();
	
	Version getVersion();
	
	List<Plugin> getPlugins();
	
	Set<Tag> getTags();
	
	void addTag(Tag tag);
	
	void removeTag(Tag tag);
	
	List<String> getPlayers();
}
