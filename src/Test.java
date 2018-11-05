import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Test {

  static HashMap<String, Object> hashMap = new HashMap<>();
  static ArrayList<HashMap> users = new ArrayList<>();


  public static void main(String[] args) throws IOException {
//    String[] array = "#%".split("[^A-Za-z0-9\\-\\_]");

    String join = "JOIN " + "username"+ ", 127.0.0.1:5656";

    String[] array = join.split("[, ]");
    System.out.println(join);
    System.out.println(array.length);
    System.out.println(Arrays.toString(array));


  }
}
