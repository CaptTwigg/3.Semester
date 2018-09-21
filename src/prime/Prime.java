package prime;

public class Prime {

  public static void main(String[] args) {
    long counter= 0;
    double start = System.currentTimeMillis();
    for (int i = 2; i < 10_000_000L; i++) {
      boolean isPrime = true;

      for (int j = 2; j < Math.sqrt(i); j++) {
        if (i % j <= 0) {
          isPrime = false;
          break;
        }
      }
      if (isPrime) {
        counter++;
       // System.out.println("Prime: " + i);
      }
    }
    System.out.println(counter);
    System.out.println("Done in: " + (System.currentTimeMillis() - start));

  }
}
