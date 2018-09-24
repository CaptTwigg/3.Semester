package Network;

import java.io.*;
import java.net.*;

class TCPClient {
  public static void main(String argv[]) throws Exception {
    System.out.println("starting TCPClient main");
    String sentence;
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("trying to connect");
    Socket clientSocket = new Socket("192.168.43.219", 5656);
    System.out.println("we are connected");
    while (true) {

      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      System.out.print("Please type your text: ");
      sentence = inFromUser.readLine();

      outToServer.writeBytes(sentence + '\n');
      if(sentence.equalsIgnoreCase("quit"))
        break;
      sentence = inFromServer.readLine();
      if(sentence.equalsIgnoreCase("quit"))
        break;
      System.out.println("FROM SERVER: " + sentence);
    }

    System.out.println("Closing program");
    clientSocket.close();

  }

}
