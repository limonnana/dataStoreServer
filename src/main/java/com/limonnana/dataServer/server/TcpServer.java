package com.limonnana.dataServer.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;
import com.limonnana.dataServer.api.ServerApi;
import com.limonnana.dataServer.api.ServerApiImp;
import com.limonnana.entities.KeyAndList;

public class TcpServer {

    private static final String GET_ALL_KEYS = "1";
	private static final String RIGHT_ADD = "2";
	private static final String LEFT_ADD = "3";
	private static final String SET = "4";
	private static final String GET = "5";

	private static ServerSocket welcomeSocket;
	private static DataOutputStream outToClient;
	private static String clientWord;
	private static String line = null;
	private static String holder = null;
	private static int bytNumber;
	private static int port = 5555;

	public static void main(String[] args) throws IOException {

		run(port);

	}
	
	private static void run(int port){
		
		
		
		ServerApi serverApi = new ServerApiImp();
		String[] inbound = getInbound(port);

		if (inbound[0].equals(GET_ALL_KEYS)) {
			String data = toJson(serverApi.getAllKeys(inbound[1]));
			responseData(data);
		}
		if (inbound[0].equals(RIGHT_ADD)) {
			serverApi.rightAdd(inbound[1], inbound[2]);
			responseData(" The value was added on the right side");
		}
		if (inbound[0].equals(LEFT_ADD)) {
			serverApi.leftAdd(inbound[1], inbound[2]);
			responseData(" The value was added on the left side");
		}
		if (inbound[0].equals(SET)) {
			serverApi.set(getKeyFromGson(inbound[1]), getListFromGson(inbound[1]));
			responseData(" The key with his values were save succesfully !");
		}
		if (inbound[0].equals(GET)) {
			String data = toJson(serverApi.get(inbound[1]));
			responseData(data);
		}
		port ++;
		run(port);
	}

	private static String[] getInbound(int port) {

		String[] clientWordToArray = null;

		try {
			welcomeSocket = new ServerSocket(5555);

			Socket connectionSocket = welcomeSocket.accept();

			DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());

			outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			clientWord = inFromClient.readUTF();

			System.out.println(" this is from client: " + clientWord);

			clientWordToArray = clientWord.split(" ");

		} catch (IOException ex) {
			System.out.println("Error" + ex.getMessage());
			ex.printStackTrace();
		}
		return clientWordToArray;
	}

	private static void responseData(String data) {
		try {
			while (data != null) {
				System.out.println(line);
				bytNumber = data.getBytes().length;
				holder = Integer.toString(bytNumber);
				outToClient.writeBytes(holder);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static String toJson(Object o) {
		Gson gson = new Gson();
		return gson.toJson(o);
	}

	private static String getKeyFromGson(String json) {
		Gson gson = new Gson();
		KeyAndList kAndL = gson.fromJson(json, KeyAndList.class);
		return kAndL.getKey();
	}

	private static List<String> getListFromGson(String json) {
		Gson gson = new Gson();
		KeyAndList kAndL = gson.fromJson(json, KeyAndList.class);
		return kAndL.getValues();
	}

	//private static final String fileName = "C:/Users/rosen/Documents/WebDevelopment/input.txt";
	/*
	 * public static void reader(){ try {
	 * 
	 * FileReader fileReader = new FileReader(fileName);
	 * 
	 * BufferedReader buffer = new BufferedReader(fileReader);
	 * 
	 * 
	 * while((line = buffer.readLine()) != null) { System.out.println(line);
	 * bytNumber = line.getBytes().length; holder=Integer.toString(bytNumber);
	 * //pr.println(bytNumber);//only printing first line not going until eof
	 * outToClient.writeBytes(holder); // line = buffer.readLine(); //
	 * pr.flush(); }
	 * 
	 * // Always close files. buffer.close(); } catch(FileNotFoundException ex)
	 * { System.out.println( "Unable to open file '" + fileName + "'"); }
	 * catch(IOException ex) { System.out.println( "Error reading file '" +
	 * fileName + "'"); // Or we could just do this: // ex.printStackTrace(); }
	 * 
	 * }
	 * 
	 * 
	 * 
	 * public static void BWriter(String content){
	 * 
	 * 
	 * try { File file = new File(fileName); FileWriter fr = new
	 * FileWriter(file, true); BufferedWriter bw = new BufferedWriter(fr);
	 * bw.write(content); bw.newLine(); bw.close(); fr.close(); } catch
	 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e)
	 * { e.printStackTrace(); } }
	 */
}