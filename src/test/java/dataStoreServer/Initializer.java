package dataStoreServer;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.limonnana.dataServer.repositories.DataRepository;
import com.limonnana.dataServer.repositories.DataRepositoryImp;

public class Initializer {
	
	public static void main(String[] args){
		
		DataRepository d = new DataRepositoryImp();
		Map<String, List<String>> m = d.getHarcodedData();
		Gson json = new Gson();
		String harcodedMap = json.toJson(m);
		System.out.println(harcodedMap);
	}

}
