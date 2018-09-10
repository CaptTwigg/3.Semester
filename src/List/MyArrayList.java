package List;

public class MyArrayList<T> implements MyList {
  private int arrayLength = 5;
  private int size = 0;
  private T[] array = (T[]) new Object[arrayLength];

  @Override
  public T get(int index) {
    if (index > size || index < 0)
      throw new IndexOutOfBoundsException("Too large or negative :) index: " + index);

    return array[index];
  }

  @Override
  public void add(Object o) {
    if (size == arrayLength) {
      expandArray();
    }
    array[size] = (T) o;
    size++;
  }

  @Override
  public void remove(int index) {
    size--;

    if (index > size)
      throw new IndexOutOfBoundsException(String.format("Index %s out-of-bounds for length %s", index, size));

    for (int i = index; i < size; i++) {
      array[i] = array[i + 1];
    }
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void set(int index, Object o) {
    array[index] = (T) o;
  }
  private void expandArray() {
    T[] tempArray = array;
    arrayLength += 5;
    array = (T[]) new Object[arrayLength];

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

