package prime;

public class PrimeThread implements Runnable {
  static int noOfThreads = 2;
  static long max = 10000000 L;
  static long end = max / noOfThreads;
  static long start = 2;
  static long interval = max / noOfThreads;
  static final LongCounter counter = new LongCounter();

  public static void main(String[] args) throws InterruptedException {

    long timestart = System.currentTimeMillis();
    Thread[] threads = new Thread[noOfThreads];//amount of threads
    for (int b = 0; b < threads.length; b++) {
      threads[b] = new Thread(new PrimeThread());
      threads[b].start();
      System.out.println("start: " + start);
      System.out.println("End:   " + end);
      if(b < (noOfThreads-1)) {
        start = end + 1;
        end = (interval * (b + 2));
      }
    }
    for (Thread thread : threads) {
      thread.join();
    }
    long timestop = System.currentTimeMillis();

    System.out.println("Count: " + counter.get());
    System.out.println("Execution time: " + (timestop - timestart) + " ms");

  }

  public static  boolean isPrime(long number) {
    if (number == 2) {
      return true;
    }
    if (number % 2 == 0) {
      return false;
    }

    for (int i = 3; i <= Math.sqrt(number); i += 2) {
      if (number % i == 0) {
        return false;
      }
    }
    //System.out.println("Prime: " + number);
    return true;
  }

  @Override
  public void run() {
    for (long i = start; i <= end; i++) {
      if (isPrime(i)) {
        counter.increment();
      }
    }
  }
}
