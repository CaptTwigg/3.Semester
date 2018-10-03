package Manda1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  static BufferedReader inFromUser;
  static Socket clientSocket;
  static OutputStream outToServer;
  static InputStream inFromServer;
  static String username = "hanse9";
  static String sentence = "";
  static String msg = "";
  static Thread aliveThread;

  public static void main(String argv[]) throws Exception {

    inFromUser = new BufferedReader(new InputStreamReader(System.in));
    do {
//      clientSocket = new Socket("172.16.20.144", 4545);
      clientSocket = new Socket("localhost", 5656);
      outToServer = clientSocket.getOutputStream();
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter username: ");
      username = scanner.next();
      outToServer.write(("JOIN " + username+ ", 127.0.0.1:5656").getBytes());

    }while (!ableToJoinServer());
    threadAlive();
    threadReceive();
    System.out.print("Please type your text: ");

    while (true) {
      inFromServer =(clientSocket.getInputStream());
      msg = inFromUser.readLine();
      sentence = String.format("DATA %s: %s", username, msg);
      outToServer.write(sentence.getBytes());
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
          outToServer.write("IMAV".getBytes());
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
          InputStream inFromClient = (clientSocket.getInputStream());
          byte[] bytes = new byte[1024];
          inFromClient.read(bytes);
          String sentence= new String(bytes);
          if (msg.equals("QUIT"))
            break;
          System.out.println("from server: " + sentence.trim());
        } catch (IOException  e) {
          e.printStackTrace();
          break;
        }
      }
    });
    thread.start();
  }

  static boolean ableToJoinServer() throws IOException {
    InputStream inFromClient = (clientSocket.getInputStream());
    byte[] bytes = new byte[1024];
    inFromClient.read(bytes);
    String sentence= new String(bytes);
    System.out.println(sentence.trim());
    return sentence.trim().equals("J_OK");

  }

}
