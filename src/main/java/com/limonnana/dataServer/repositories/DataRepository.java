package com.limonnana.dataServer.repositories;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface DataRepository {
	
	Map<String, ConcurrentHashMap<String, List<String>>> getDataFromCache();
	
	Map<String, List<String>> getDataFromFile();
	
	void save(Map<String, List<String>> m);
	
	Map<String, List<String>> getHarcodedData();

}
