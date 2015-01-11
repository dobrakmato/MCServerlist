package eu.matejkormuth.mcserverlist.api;

public interface InformationGatherer {
	void gatherAll();
	
	void gatherPlugins();
	
	void gatherPlayers();
	
	void gatherBans();

	Server toServer();
}
