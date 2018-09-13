package List;

public class Main {

  public static void main(String[] args) {

    MySet<Integer> mySet = new MySet<>();

    mySet.add(1);
    mySet.add(1);
    mySet.add(4);
    mySet.add(3);
    System.out.println(mySet);

    MyArrayList<Integer> myArrayList = new MyArrayList<>();
//    for (int i = 0; i < 11; i++) {
//      myArrayList.add(i);
//    }
//    myArrayList.remove(5);

    System.out.println(myArrayList);
    System.out.println("Array size: " + myArrayList.size());
//    System.out.println(myArrayList.get(-3));
//    System.out.println(myArrayList.get(0));
//    System.out.println(myArrayList.get(1));
//    System.out.println(myArrayList.get(200));
  }
}

