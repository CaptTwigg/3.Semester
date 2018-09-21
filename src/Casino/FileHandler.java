package Casino;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHandler<T> {
  private String file;
  private ArrayList<T> arrayList;

  public FileHandler(String file) {
    this.file = file;
    this.arrayList = loadSerFile();
  }

  public void saveSerFile(ArrayList<T> arrayList) {
    try {
      FileOutputStream fileOut = new FileOutputStream(file);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(arrayList);
      out.close();
      fileOut.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public ArrayList<T> loadSerFile() {
    ArrayList<T> arrayList = new ArrayList<>();
    try {
      FileInputStream fileIn = new FileInputStream(file);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      arrayList = (ArrayList<T>) in.readObject();
      in.close();
      fileIn.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return arrayList;
  }
}
