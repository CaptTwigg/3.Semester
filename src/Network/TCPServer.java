package Network;

import java.io.*;
import java.net.*;

public class TCPServer {
  public static void main(String argv[]) throws Exception {
    System.out.println("starting TCP Server main program");
    String sentence;
    String userText;

    ServerSocket welcomeSocket = new ServerSocket(5656);
    System.out.println("we have a socket");
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

      System.out.println("waiting for a connection");
      Socket connectionSocket = welcomeSocket.accept();
      System.out.println("Connected");
    while (true) {
      BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
      DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
      sentence = inFromClient.readLine();
      if(sentence.equalsIgnoreCase("quit"))
        break;
      System.out.println("FROM CLIENT: " + sentence);

      System.out.print("Please type your text: ");
      //userText = "Back";
      userText = inFromUser.readLine();
      outToClient.writeBytes(userText + '\n');
      if(userText.equalsIgnoreCase("quit"))
        break;

    }
    System.out.println("Closing program");
    connectionSocket.close();
    welcomeSocket.close();
  }

}