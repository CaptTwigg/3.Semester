package Network;


/**
 * Write a description of class UDP_0 here.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_00 {
  public static void main(String args[]) throws Exception {
    String sentence;
    int length;
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    DatagramSocket receivingSocket = new DatagramSocket(6710);
    DatagramSocket sendingSocket = new DatagramSocket();
    InetAddress IPAddress = InetAddress.getByName("127.0.0.1");

   // Gui gui = new Gui();

    threadReceive(receivingSocket);


    while (true) {
      byte[] data = new byte[1024];

      System.out.print("6710: ");
      sentence = inFromUser.readLine();
      length = sentence.length();
      data = sentence.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(data, length, IPAddress, 6701);
      sendingSocket.send(sendPacket);

    }

  }

  static void threadReceive(DatagramSocket receivingSocket) {
    Thread thread = new Thread(() -> {
      while (true) {
        byte[] data = new byte[1024];
        String receiveSentence;
        try {
          DatagramPacket receivePacket = new DatagramPacket(data, data.length);
          receivingSocket.receive(receivePacket);
          receiveSentence = new String(receivePacket.getData());


          System.out.println("\nFROM SERVER:" + receiveSentence);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();
  }


}



