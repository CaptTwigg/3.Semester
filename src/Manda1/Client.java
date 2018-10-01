package Manda1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  static BufferedReader inFromUser;
  static Socket clientSocket;
  static DataOutputStream outToServer;
  static BufferedReader inFromServer;
  static String username = "hanse9";
  static String sentence = "";
  static String msg = "";
  static Thread aliveThread;

  public static void main(String argv[]) throws Exception {

    inFromUser = new BufferedReader(new InputStreamReader(System.in));
    do {
      clientSocket = new Socket("localhost", 5656);
      outToServer = new DataOutputStream(clientSocket.getOutputStream());
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter username: ");
      username = scanner.next();
      outToServer.writeBytes("JOIN " + username+ ", 127.0.0.1:5656\n");

    }while (!ableToJoinServer());
    threadAlive();
    threadReceive();

    while (true) {
      inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      System.out.print("Please type your text: ");
      msg = inFromUser.readLine();
      sentence = String.format("DATA %s: %s", username, msg);
      outToServer.writeBytes(sentence + '\n');
      if (msg.equals("QUIT")){
        aliveThread.interrupt();
        break;
      }

    }
    //clientSocket.close();

  }

  static void threadAlive() {
    aliveThread = new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(60000);
          if (msg.equals("QUIT"))
            break;
          outToServer.writeBytes("IMAV\n");
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
          break;
        }
      }
    });
    aliveThread.start();

  }

  static void threadReceive() {
    Thread thread = new Thread(() -> {
      while (true) {
        try {
          String sentence;
          BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
          sentence = inFromClient.readLine();
          if (sentence == null)
            break;
          System.out.println(sentence);
        } catch (IOException  e) {
          e.printStackTrace();
          break;
        }
      }
    });
    thread.start();
  }

  static boolean ableToJoinServer() throws IOException {
    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    String sentence = inFromClient.readLine();
    System.out.println(sentence);
    return sentence.equals("J_OK");

  }

}
