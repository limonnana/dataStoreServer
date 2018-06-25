package com.limonnana.dataServer.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.gson.Gson;
import com.limonnana.dataServer.cache.Cache;
import com.limonnana.dataServer.cache.InMemoryCache;
import com.limonnana.entities.KeyAndList;

public class DataRepositoryImp implements DataRepository{
	
	private static final String fileName = "C:/Users/rosen/Documents/WebDevelopment/input.txt";
	private static final String KEY_NAME_MAIN_MAP = "data";
	
	private static String clientWord;
	private static String line = null;
	private static String holder = null;
	private static int bytNumber;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ConcurrentHashMap<String, List<String>>> getDataFromCache() {
		
		Cache cache = new InMemoryCache();
		Map<String, ConcurrentHashMap<String, List<String>>> m = (Map<String, ConcurrentHashMap<String, List<String>>>)cache.get(KEY_NAME_MAIN_MAP);
	
	
		if(m == null){
			Map<String, List<String>> simpleMap = getDataFromFile();
			ConcurrentHashMap<String, List<String>> concurrentMap = new ConcurrentHashMap<String, List<String>> (simpleMap);
			
			m = new ConcurrentHashMap<>();
			m.put(KEY_NAME_MAIN_MAP,  concurrentMap);                      //new ConcurrentHashMap<String, List<String>>());
		}
		
		return m;
	}
	
	@Override
	public Map<String, List<String>> getDataFromFile() {
		
		return readDataFromFile();
	}
	
	@Override
	public void save(Map<String, List<String>> m) {
		Cache cache = new InMemoryCache();
		cache.add(KEY_NAME_MAIN_MAP, m, 3600000);
		saveMapOnFileSystem(m);
	}
	
	public void saveMapOnFileSystem(Map<String, List<String>> m){
		
		Set<String> keys = m.keySet();
		
		emptyCleanFile();
		
		List<String> keysList = new ArrayList<String>(keys);
		Gson json = new Gson();
		
		for(String key : keysList){
			KeyAndList kl = new KeyAndList();
			kl.setKey(key);
			kl.setValues(m.get(key));
			String line = json.toJson(kl, KeyAndList.class);
			writeToFileSystem(line);
		}
	}
	
	private void emptyCleanFile(){
		
		File file = new File(fileName);
		if (file.exists() && file.isFile())
		  {
		  file.delete();
		  }
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Map<String, List<String>> readDataFromFile(){
		
		Map<String, List<String>> m = new ConcurrentHashMap<String, List<String>>();
		//Map<String, ConcurrentHashMap<String, List<String>>> mc = new ConcurrentHashMap<>();
		
		 try {
		  
		  FileReader fileReader = new FileReader(fileName);
		  
		  BufferedReader buffer = new BufferedReader(fileReader);
		  
		  String line= "";
		  
		  
		  while((line = buffer.readLine()) != null)
		  { 
			  System.out.println(line);
			  Gson json = new Gson();
			  KeyAndList kl = json.fromJson(line, KeyAndList.class);
			  String key = kl.getKey();
			  List<String> valueList = kl.getValues();
			  m.put(key, valueList);
		  }
		  buffer.close();
		
		 
		 }
		 catch(IOException ex) { 
			  System.out.println( "Error reading file '" +
		  
		  fileName + "'"); 
		  
		  }
		 
		  return m;
	}
	
	private void writeToFileSystem(String lineJson){
		
			  try {
				  File file = new File(fileName);
			  FileWriter fr = new FileWriter(file, true);
			  BufferedWriter bw = new BufferedWriter(fr);
			  bw.write(lineJson);
			  bw.newLine();
			  bw.close();
			  fr.close();
			  } catch
			  (FileNotFoundException e) {
				  e.printStackTrace();
			  } catch (IOException e)
			  { 
				  e.printStackTrace(); 
			  }
	}
	
  public Map<String, List<String>> getHarcodedData(){
		
		Map<String, List<String>> m = new TreeMap<String, List<String>>();
		
		List<String> l1 = new ArrayList<>();
		l1.add("2");
		l1.add("3");
		m.put("2-3", l1);
		
		List<String> l4 = new ArrayList<>();
		l4.add("8");
		l4.add("9");
		m.put("8-9", l4);
		
		List<String> l2 = new ArrayList<>();
		l2.add("4");
		l2.add("5");
		m.put("4-5", l2);
		
		
		List<String> l0 = new ArrayList<>();
		l0.add("0");
		l0.add("1");
		m.put("0-1", l0);
		
		List<String> l3 = new ArrayList<>();
		l3.add("6");
		l3.add("7");
		m.put("6-7", l3);
		
	return m;
	}



}
