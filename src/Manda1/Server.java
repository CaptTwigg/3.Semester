package Manda1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

  static ServerSocket welcomeSocket;
  static BufferedReader inFromUser;
  static ArrayList<Socket> sockets = new ArrayList<>();
  static ArrayList<HashMap> users = new ArrayList<>();


  public static void main(String argv[]) throws Exception {

    welcomeSocket = new ServerSocket(5656);
    inFromUser = new BufferedReader(new InputStreamReader(System.in));
    System.out.println(welcomeSocket.getLocalSocketAddress());
    Socket connectionSocket = welcomeSocket.accept();
    System.out.println(connectionSocket.getInetAddress());

    while (true) {
      HashMap<String, Object> hashMap = new HashMap<>();
      String name = joinData(connectionSocket);
      if(freeUsername(name)){
        serverResponseMsg(connectionSocket, "J_ER Duplicate username: Username " + name + "taken." );
        continue;
      } else
        serverResponseMsg(connectionSocket,"J_OK");
      hashMap.put("username", name);
      hashMap.put("alive", true);
      hashMap.put("socket", connectionSocket);
      users.add(hashMap);

      newUserJoined();
      threadReceive(connectionSocket);
      sockets.add(connectionSocket);
    }


    //connectionSocket.close();
    // welcomeSocket.close();
  }

  static void threadReceive(Socket connectionSocket) {

    Thread thread = new Thread(() -> {
      while (true) {
        try {
          String sentence;
          BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

          sentence = inFromClient.readLine();
          String[] data = sentence.split(":");
          if (commandValidation(data)){
            serverResponseMsg(connectionSocket,"J_OK");
          }
          if (sentence.equals("IMAV")){
            updateIMAV(connectionSocket);
            continue;
          }

          if (data[1].trim().equals("QUIT")) {
            break;
          }
          System.out.println("FROM CLIENT: " + sentence);
          syncChat(data[1].trim(), data[0].substring(4).trim());
        } catch (NullPointerException | IOException e) {
          break;
        }
      }
      try {
        System.out.println(connectionSocket.getPort() + " Left");
        removeByPort(connectionSocket.getPort());
        connectionSocket.close();
      } catch (IOException e) {
        System.out.println("EIO");
        e.printStackTrace();
      }
    });
    thread.start();
  }

  static void syncChat(String msg, String name) throws IOException {
    for (HashMap userMap : users) {
      if(name.equals(userMap.get("username").toString()))
        continue;
      DataOutputStream outToClient = new DataOutputStream(((Socket) userMap.get("socket")).getOutputStream());
      outToClient.writeBytes(name + ": " + msg + '\n');
    }
  }

  static void newUserJoined() throws IOException {
    ArrayList<String> names = new ArrayList<>();
    for (HashMap user : users)
      names.add(user.get("username").toString());
    for (HashMap userMap : users) {
      DataOutputStream outToClient = new DataOutputStream(((Socket) userMap.get("socket")).getOutputStream());
      outToClient.writeBytes("New user joined: " + names.toString() + '\n');
    }

  }

  static boolean commandValidation(String[] data)  {
    String command = data[0].substring(0,4).trim();
    return command.equals("JOIN") | command.equals("DATA") | command.equals("IMAV") | command.equals("QUIT");
  }

  static String joinData(Socket connectionSocket)throws IOException{
    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
    String join = inFromClient.readLine();
    System.out.println(join);
    String[] joinarray = join.split(",");
    String name = joinarray[0].substring(4).trim();
    String[] addr = joinarray[1].split(":");
    String IP = addr[0].trim();
    String port = addr[1].trim();

    return name;
  }

  static void serverResponseMsg(Socket socket, String msg) throws IOException {
    DataOutputStream outToClient = new DataOutputStream((socket.getOutputStream()));
    outToClient.writeBytes(msg+"\n");
  }

  static void updateIMAV(Socket socket){
    for (HashMap user : users){
      if(((Socket)user.get("socket")).getPort() == socket.getPort())
        user.put("alive",true);
      break;
    }
    System.out.println("Update alive for: " +socket.getPort());
  }

  static boolean freeUsername(String name){
    for (HashMap user : users)
      return user.get("username").toString().equalsIgnoreCase(name);
    return false;
  }

  static void removeByPort(int port) {
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
