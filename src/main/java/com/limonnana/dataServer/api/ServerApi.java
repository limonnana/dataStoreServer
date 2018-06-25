package com.limonnana.dataServer.api;

import java.util.List;

public interface ServerApi {
	
	List<String> getAllKeys(String pattern); //– Returns all keys matching pattern.
	void rightAdd(String K, String V);   // – adds a value to key K, from the right
	void leftAdd(String K, String V);   //  // – adds a value to key K, from the left
	void set(String K, List<String> V);
	List<String> get(String K);            //– gets a list by its key

}
