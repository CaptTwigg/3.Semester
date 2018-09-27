package Manda1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

  static ServerSocket welcomeSocket;
  static BufferedReader inFromUser;

  public static void main(String argv[]) throws Exception {
    welcomeSocket = new ServerSocket(5656);
    inFromUser = new BufferedReader(new InputStreamReader(System.in));


    while (true) {
      Socket connectionSocket = welcomeSocket.accept();
      System.out.println(connectionSocket.getInetAddress());
      System.out.println(connectionSocket.getPort());
      threadReceive(connectionSocket);
    }


    //connectionSocket.close();
    // welcomeSocket.close();
  }

  static void threadReceive(Socket connectionSocket) {

    Thread thread = new Thread(() -> {
      while (true) {
        try {
          String sentence;
          String userText;
          BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
          DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
          sentence = inFromClient.readLine();
          if (sentence.equals("QUIT"))
            break;

          System.out.println("FROM CLIENT: " + sentence);
          userText = "Back";
          //userText = inFromUser.readLine();
          outToClient.writeBytes(userText + '\n');
        } catch (SocketException e) {
          break;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      try {
        System.out.println(connectionSocket.getPort() + " Left");
        connectionSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    thread.start();
  }
}
