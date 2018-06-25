package com.limonnana.dataServer.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.*;


public class TcpClient {


     public static void main(String[] args) {
       String temp;
       String displayBytes;
      try
      {
      //create input stream
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      //create client socket, connect to server
      Socket clientSocket = new Socket("localhost",5555);
      //create output stream attached to socket
      DataOutputStream outToServer =
              new DataOutputStream(clientSocket.getOutputStream());



      System.out.print("Command : ");

      //create input stream attached to socket
      DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

      temp = inFromUser.readLine();
      outToServer.writeUTF(temp);
      outToServer.flush();


     //read line from server
      //displayBytes = inFromServer.readLine();
      
      //displayBytes = inFromServer.readUTF();
      
    //  while(displayBytes != null)
    //  {
   //   System.out.print(displayBytes);
   //   }
     // clientSocket.close();
  }
  catch(Exception ex)
  {
	  ex.printStackTrace();
  }
}
}