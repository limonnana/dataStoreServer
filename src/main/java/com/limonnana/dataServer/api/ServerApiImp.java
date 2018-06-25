package com.limonnana.dataServer.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.limonnana.dataServer.repositories.DataRepository;
import com.limonnana.dataServer.repositories.DataRepositoryImp;

public class ServerApiImp implements ServerApi{
	
	private DataRepository dataRepository = new DataRepositoryImp();
	private static final String KEY_NAME_MAIN_MAP = "data";

	@Override
	public List<String> getAllKeys(String pattern) {
		Map<String, List<String>> m = getMap();
		List<String> l = new ArrayList<String>(m.keySet());
		return l;
	}

	@Override
	public void rightAdd(String key, String value) {
		
		Map<String, List<String>> m = getMap();
		List<String> l = m.get(key); 
		l.add(value);
		m.put(key, l);
		dataRepository.save(m);
	}

	@Override
	public void leftAdd(String key, String value) {
		Map<String, List<String>> m = getMap();
		List<String> l = m.get(key); 
		l.add(0,value);
		m.put(key, l);
		dataRepository.save(m);
		
	}

	@Override
	public void set(String key, List<String> value) {
		Map<String, List<String>> m = getMap();
		m.put(key, value);
		dataRepository.save(m);
	}

	@Override
	public List<String> get(String key) {
		Map<String, List<String>> m = getMap();
		return m.get(key);
	}
	
	private Map<String, List<String>> getMap(){
		return dataRepository.getDataFromCache().get(KEY_NAME_MAIN_MAP);
	}

}
