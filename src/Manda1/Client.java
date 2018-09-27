package Manda1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
  static BufferedReader inFromUser;
  static volatile Socket clientSocket;
  static DataOutputStream outToServer;
  static String username;
  public static void main(String argv[]) throws Exception {
    String sentence;
    clientSocket = new Socket("127.0.0.1", 5656);
    inFromUser = new BufferedReader(new InputStreamReader(System.in));
    outToServer = new DataOutputStream(clientSocket.getOutputStream());
     threadAlive();
    while (true) {
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      System.out.print("Please type your text: ");
      sentence = inFromUser.readLine();

      outToServer.writeBytes(sentence + '\n');

      sentence = inFromServer.readLine();

      System.out.println("FROM SERVER: " + sentence);
      System.out.println(clientSocket.isConnected());
    }

    //clientSocket.close();

  }

  static void threadAlive( ) {
    Thread thread = new Thread(() -> {

      Socket clientSocket = null;
      DataOutputStream outToServer  = null;

      try {
        clientSocket = new Socket("127.0.0.1", 5656);
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
      } catch (IOException e) {
        e.printStackTrace();
      }

      while (true) {
        try {
          outToServer.writeBytes("IMAV");
          System.out.println("IMAV");

          Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();
  }


}
