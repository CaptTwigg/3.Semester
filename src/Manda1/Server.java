package Manda1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

  private static InputStream inFromUser;
  private static ArrayList<HashMap> users = new ArrayList<>();


  public static void main(String argv[]) throws Exception {

    ServerSocket welcomeSocket = new ServerSocket(5656);

    System.out.println(welcomeSocket.getLocalSocketAddress());

    while (true) {
      Socket connectionSocket = welcomeSocket.accept();
      inFromUser = connectionSocket.getInputStream();
      HashMap<String, Object> hashMap = new HashMap<>();
      String name = joinData(connectionSocket).trim();

      if (name.equals("UNKNOWN")) {
        serverResponseMsg(connectionSocket, "J_ER Unknown command: " + name);
        continue;
      }
      if (freeUsername(name)) {
        serverResponseMsg(connectionSocket, "J_ER Duplicate username: Username " + name + " taken.");
        continue;
      }
      if (illegalChar(name)) {
        serverResponseMsg(connectionSocket, "J_ER Illegal character: Only letters, digits, ‘-‘ and ‘_’ allowed.");
        continue;
      }
      if (name.length() > 12) {
        serverResponseMsg(connectionSocket, "J_ER Username too long : Max 12 characters.");
        continue;
      }

      serverResponseMsg(connectionSocket, "J_OK");

      hashMap.put("username", name);
      hashMap.put("socket", connectionSocket);
      users.add(hashMap);

      newUserJoined();
      threadReceive(connectionSocket);
    }
  }

  private static void threadReceive(Socket connectionSocket) {
    AtomicInteger i = new AtomicInteger();
    timerThread(i, connectionSocket.getPort());
    Thread thread = new Thread(() -> {

      while (true) {
        try {
          InputStream inFromClient = connectionSocket.getInputStream();
          byte[] bytes = new byte[1024];
          inFromClient.read(bytes);
          String sentence = new String(bytes);
          String[] splitSentence = sentence.split(":",2);
          if (sentence.trim().equals("IMAV")) {
            System.out.println("\u001B[31mUpdate alive for: " + connectionSocket.getPort() + "\u001B[0m");
            i.set(0);
            continue;
          }
          if (sentence.split(" ")[0].equals("DATA")) {
            //serverResponseMsg(connectionSocket, "J_OK");
          } else {
            serverResponseMsg(connectionSocket, "J_ER Unknown command: " + splitSentence[0].split(" ")[0].trim());
            continue;
          }
          if (sentence.trim().length() > 250) {
            System.out.println(sentence.trim().length());
            serverResponseMsg(connectionSocket, "J_ER Message too long: " + sentence + '\n');
            continue;
          }
          if (sentence.trim().equals("QUIT")) {
            break;
          }
          System.out.println("FROM CLIENT: " + sentence.trim());
          syncChat(splitSentence[1].trim(), splitSentence[0].substring(4).trim());
        } catch (NullPointerException | IOException e) {
          break;
        }
      }
      try {
        System.out.println(connectionSocket.getPort() + " Left");
        removeByPort(connectionSocket.getPort());
        connectionSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    thread.start();
  }

  private static void timerThread(AtomicInteger i, int port) {
    Thread thread = new Thread(() -> {
      int u = 0;
      while (u < 80) {
        try {
          i.incrementAndGet();
          u = i.get();
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      removeByPort(port);
    });
    thread.start();

  }

  private static void syncChat(String msg, String name) throws IOException {
    for (HashMap userMap : users) {
      if (((Socket) userMap.get("socket")).isClosed())
        continue;
      OutputStream outToClient = (((Socket) userMap.get("socket")).getOutputStream());
      outToClient.write((name + ": " + msg).getBytes());
    }
  }

  private static void newUserJoined() throws IOException {
    StringBuilder names = new StringBuilder();
    for (HashMap user : users)
      names.append(user.get("username").toString()).append(" ");
    syncChat(names.toString()+'\n',"LIST");
  }

  static String joinData(Socket connectionSocket) throws IOException {
    InputStream inFromClient = connectionSocket.getInputStream();
    byte[] bytes = new byte[1024];
    inFromClient.read(bytes);
    String join = new String(bytes);
    if (join.split(" ")[0].equals("JOIN")) {
      System.out.println(join.trim());
      String[] joinarray = join.split(",");
      String name = joinarray[0].substring(4).trim();
//      String[] addr = joinarray[1].split(":");
//      String IP = addr[0].trim();
//      String port = addr[1].trim();
      return name.trim();
    }
    return "UNKNOWN";
  }

  static boolean commandValidation(String[] data) {
    String command = data[0].split(" ")[0].trim();
    return command.equals("JOIN") | command.equals("DATA") | command.equals("IMAV") | command.equals("QUIT");
  }

  private static void serverResponseMsg(Socket socket, String msg) throws IOException {
    OutputStream outToClient = (socket.getOutputStream());
    outToClient.write(msg.getBytes());
  }

  private static boolean freeUsername(String name) {
    for (HashMap user : users)
      return user.get("username").toString().equalsIgnoreCase(name);
    return false;
  }

  private static boolean illegalChar(String name) {
    String[] array = (name + "A").split("[^A-Za-z0-9\\-\\_]");
    return array.length != 1;
  }

  private static void removeByPort(int port) {
    for (HashMap user : users) {
      if (((Socket) user.get("socket")).getPort() == port) {
        System.out.println(users);
        users.remove(user);
        System.out.println(users);
        break;
      }
    }
  }
}
