package List;

public class MySet<T> implements MyList, Comparable<T> {

  int size;
  private int arraySize = 10;
  T[] array = (T[]) new Object[arraySize];
  T compare = null;


  @Override
  public Object get(int index) {
    return array[index];
  }

  @Override
  public void add(Object o) {
    compare = (T)o;
    boolean dub = false;
    for (T t : array)
      if (t != null)
        dub = t.equals(o);
    if (!dub) {
      if (size == arraySize)
        expandArray();
      array[size] = (T) o;
      size++;
      //Arrays.sort(array);
    }
  }


  public void add(int index, Object o) {

  }

  @Override
  public void remove(int index) {

  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public void set(int index, Object o) {

  }

  private boolean equalsTo(T thisO, T o) {
    return thisO.equals(o);
  }

  @Override
  public int compareTo(T o) {
    return 1;
  }

  private void expandArray() {
    T[] tempArray = array;
    arraySize += 5;
    array = (T[]) new Object[arraySize];

    for (int i = 0; i < tempArray.length; i++)
      array[i] = tempArray[i];
  }

  @Override
  public String toString() {
    StringBuilder toString = new StringBuilder("[");
    for (int i = 0; i < size; i++) {
      toString.append(array[i].toString());
      if (i < size - 1)
        toString.append(", ");
    }
    toString.append("]");
    return toString.toString();
  }
}

