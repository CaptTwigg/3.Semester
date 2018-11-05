package Network;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDP {
  BufferedReader inFromUser;
  DatagramSocket receivingSocket;
  DatagramSocket sendingSocket;
  InetAddress IPAddress;
  int sendToPort;
  Gui gui;

  public void setGui(Gui gui) {
    this.gui = gui;
  }

  public UDP(int fromPort, int toPort) {
    try {
      inFromUser = new BufferedReader(new InputStreamReader(System.in));
      receivingSocket = new DatagramSocket(fromPort);
      sendingSocket = new DatagramSocket();
      IPAddress = InetAddress.getByName("127.0.0.1");
      sendToPort = toPort;
    } catch (SocketException | UnknownHostException e) {
      e.printStackTrace();
    }
    threadReceive(receivingSocket);
  }

  void threadReceive(DatagramSocket receivingSocket) {
    Thread thread = new Thread(() -> {
      while (true) {
        byte[] data = new byte[1024];
        String receiveSentence;
        try {
          DatagramPacket receivePacket = new DatagramPacket(data, data.length);
          receivingSocket.receive(receivePacket);
          receiveSentence = new String(receivePacket.getData());
          gui.addToTextPanel(receiveSentence);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();
  }

  void send(String sentence) throws Exception {
    int length;
    byte[] data;
    length = sentence.length();
    data = sentence.getBytes();
    DatagramPacket sendPacket = new DatagramPacket(data, length, IPAddress, sendToPort);
    sendingSocket.send(sendPacket);
  }

}



