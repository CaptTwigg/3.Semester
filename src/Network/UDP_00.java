package Network;


/**
 * Write a description of class UDP_0 here.
 */

import java.io.*;
import java.net.*;

public class UDP_00 {
  public static void main(String args[]) throws Exception {
    String sentence;
    int length;
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    DatagramSocket receivingSocket = new DatagramSocket(6710);
    DatagramSocket sendingSocket = new DatagramSocket();
    InetAddress IPAddress = InetAddress.getByName("127.0.0.1");
    byte[] data = new byte[1024];
    while (true) {

      DatagramPacket receivePacket = new DatagramPacket(data, 33);
      receivingSocket.receive(receivePacket);
      sentence = new String(receivePacket.getData());
      int size = receivePacket.getLength();
      System.out.println("FROM SERVER size:" + size);
      System.out.println("FROM SERVER:" + sentence);

      System.out.println("Please type you message: ");
      sentence = inFromUser.readLine();
      length = sentence.length();
      data = sentence.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(data, length, IPAddress, 6701);
      sendingSocket.send(sendPacket);
      sentence = " ";
      data = sentence.getBytes();
    }

  }
}



