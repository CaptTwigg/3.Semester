package Manda1Cryp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import Manda1.Gui;

public class GuiClient {
  static BufferedReader inFromUser;
  static Socket clientSocket;
  static OutputStream outToServer;
  static InputStream inFromServer;
  static String username = "hanse9";
  static String sentence = "";
  static volatile String msg = "";
  static Thread aliveThread;
  static Thread threadReceive;
  static final Gui gui = new Gui();


  public static void main(String argv[]) {


  }

  static void sendMsg(String username, String msg) {
    try {
      sentence = String.format("DATA %s: %s", username, msg);
      if (msg.equals("QUIT")) {
        outToServer.write(msg.getBytes());
        aliveThread.interrupt();
        msg = "QUIT";
      }
      outToServer.write(sentence.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static String connect(String username, String ip, String port) {
    String sentence = "J_ER bad IP or port";
    try {
      clientSocket = new Socket(ip.trim(), Integer.parseInt(port.trim()));
      outToServer = clientSocket.getOutputStream();
      outToServer.write(("JOIN " + username + ", " + ip + ":" + Integer.parseInt(port)).getBytes());
      InputStream inFromClient = (clientSocket.getInputStream());
      byte[] bytes = new byte[1024];
      inFromClient.read(bytes);
      sentence = new String(bytes);
      if (sentence.trim().equals("J_OK")) {
        System.out.println("starting threads");
        threadAlive();
        threadReceive();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sentence;
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
    threadReceive = new Thread(() -> {
      while (true) {
        try {

          InputStream inFromClient = (clientSocket.getInputStream());
          byte[] bytes = new byte[1024];
          inFromClient.read(bytes);
          if(bytes[0] == 0)
            break;
          String sentence = new String(bytes);
          gui.addToChat(sentence.trim());
          System.out.println("from server: " + sentence.trim());
        } catch (IOException e) {
          e.printStackTrace();
          break;
        }
      }
    });
    threadReceive.start();
  }
}
