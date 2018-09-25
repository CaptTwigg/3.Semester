package Network;
/**
 * Write a description of class UDP_1 here.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_11 {
  public static void main(String args[]) throws Exception {
    String sentence;
    int length;

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
   // Scanner inFromKbd = new Scanner(System.in);
    DatagramSocket receivingSocket = new DatagramSocket(6701);
    DatagramSocket sendingSocket = new DatagramSocket();
    InetAddress IPAddress = InetAddress.getByName("127.0.0.1");

    //Gui gui = new Gui();
    UDP_00.threadReceive(receivingSocket);

    while (true) {
      byte[] data = new byte[1024];

      System.out.print("6701: ");
      sentence = inFromUser.readLine();
      //sentence = inFromKbd.nextLine();
      length = sentence.length();
      data = sentence.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(data, length, IPAddress, 6710);
      sendingSocket.send(sendPacket);


    }
  }
}


