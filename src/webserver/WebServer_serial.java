package webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WebServer_serial {

  static int threadcounter = 0;

  public static void main(String[] args) {
    System.out.println("OK, we are starting the WebServer.");

    try {
      ServerSocket listnerSocket = new ServerSocket(8080);
      System.out.println("OK, we have a listening socket.");

      while (true) {
        Socket newsocket = listnerSocket.accept();
        System.out.println("OK, we got a clinet connection!");
//        threadService(newsocket);
        ServiceTheClient(newsocket);
        System.out.println("Number of thread: " + threadcounter);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void threadService(Socket serverSocket) {
    Thread thread = new Thread(() -> {
      while (true) {
        ServiceTheClient(serverSocket);
      }
    });

    thread.start();
  }

  public static void ServiceTheClient(Socket con) {
    threadcounter++;
    try {
      System.out.println("****************************************************************************");
      System.out.println("OK, we are starting to service the client.");
//        String path = "C:\\Users\\mike\\Dropbox\\Java\\3.Semester\\src\\webserver\\files\\";
      String path = "/Users/bob/Dropbox/Java/3.Semester/src/webserver/files/";
      String requestMessageLine;
      String fileName;

//      BufferedReader inFromClient = new BufferedReader(new InputStreamReader(con.getInputStream()));
      Scanner inFromClient = new Scanner(con.getInputStream());
      OutputStream outToClient = con.getOutputStream();
      requestMessageLine = inFromClient.nextLine();
      System.out.println("From Client:   " + requestMessageLine);

      StringTokenizer tokenizedLine = new StringTokenizer(requestMessageLine);

      if (tokenizedLine.nextToken().equals("GET")) {
        fileName = tokenizedLine.nextToken();

        if (fileName.startsWith("/")) {
          fileName = path + fileName;
        }

        if (fileName.endsWith("/")) {
          fileName = fileName + "index.html";
        }

        File file = new File(fileName);
        if (!file.isFile()) {
          fileName = path + "error404.html";
          file = new File(fileName);
        }

        System.out.println("Trying to find file: " + fileName);

        int numOfBytes = (int) file.length();
        FileInputStream inFile = new FileInputStream(fileName);
        byte[] fileInBytes = new byte[numOfBytes];
        System.out.println("file length: " + file.length());
        inFile.read(fileInBytes);
        inFile.close();  //***** remember to close the file after usage *****
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        outToClient.write("HTTP/1.0 200 Ok\r\n".getBytes());
        outToClient.write(("Date: " + dateFormat.format(date) + "\r\n").getBytes());
        outToClient.write("Server: Very nice server right here\r\n".getBytes());

        if (fileName.endsWith(".jpg"))
          outToClient.write("Content-Type: image/jpg\r\n".getBytes());
        else if (fileName.endsWith(".gif"))
          outToClient.write("Content-Type: image/gif\r\n".getBytes());
        else if (fileName.endsWith(".ico"))
          outToClient.write("Content-Type: image/ico\r\n".getBytes());
        else if (fileName.endsWith(".jpeg"))
          outToClient.write("Content-Type: image/jpeg\r\n".getBytes());
        else if (fileName.endsWith(".png"))
          outToClient.write("Content-Type: image/png\r\n".getBytes());
        else if (fileName.endsWith(".pdf"))
          outToClient.write("Content-Type: image/pdf\r\n".getBytes());
        else if (fileName.endsWith(".svg"))
          outToClient.write("Content-Type: image/svg\r\n".getBytes());
        else if (fileName.endsWith(".tiff"))
          outToClient.write("Content-Type: image/tiff\r\n".getBytes());
        else if (fileName.endsWith(".bmp"))
          outToClient.write("Content-Type: image/bmp\r\n".getBytes());
        else if (fileName.endsWith(".mp4"))
          outToClient.write("Content-Type: video/mp4\r\n".getBytes());
        else if (fileName.endsWith(".mow"))
          outToClient.write("Content-Type: video/mow\r\n".getBytes());
        else
          outToClient.write("Content-Type: text/html\r\n".getBytes());


        outToClient.write(("Content-Length: " + numOfBytes + "\r\n").getBytes());
        outToClient.write("\r\n".getBytes());
        int steps = fileInBytes.length/16000;
        int off = 0;
        System.out.println("steps " + steps);
        int length = fileInBytes.length > 16000 ? 16000 : fileInBytes.length;
        byte[] temp = new byte[length];
        while(off < fileInBytes.length) {
          if(steps > steps -1)
            break;
          for (int i = 0; i < length; i++) {
              temp[i] = fileInBytes[i+ off];
          }
          off+= 16000;
          outToClient.write(temp);
        }
        temp = new byte[fileInBytes.length-off];
        for (int i = off; i < fileInBytes.length; i++) {
          temp[i] = fileInBytes[i];
        }
        outToClient.write(temp);


//        outToClient.write(fileInBytes);
        outToClient.write("\r\n".getBytes());

        System.out.println("OK, the file is sent to Client.");
        System.out.println("****************************************************************************");


      } else // no "GET"
      {
        System.out.println("Bad request Message");
        outToClient.write("HTTP/1.1 500  I do not understand. I am from Barcelona.\r\n".getBytes());
        outToClient.write("\n".getBytes());
        con.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }


  }  // end of


}


