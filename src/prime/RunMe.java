package prime;

public class RunMe {
  public static void main(String[] args) {

    long max = 10000000L;
    final LongCounter counter = new LongCounter();
    int noOfThreads = 4;

    long start = System.currentTimeMillis();

    Thread t1 = new Thread(()->{
      for (long i = 2; i <= max/2; i++) {
        if (isPrime(i)) {
          counter.increment();
        }
      }
    });

    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (long i = (max/2)+1; i <= max; i++) {
          if (isPrime(i)) {
            counter.increment();
          }
        }
      }
    });


    t1.start();
    t2.start();

    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    long stop = System.currentTimeMillis();

    System.out.println(counter.get());
    System.out.println("Execution time: " + (stop - start) + " ms");

//        System.out.println("hi");
//
//        LongCounter c = new LongCounter();
//
//
//        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < 1_000_000; i++) {
//                c.increment();
//            }
//        });
//
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 1_000_000; i++) {
//                    c.decrement();
//                }
//            }
//        });
//
//        t1.start();
//        t2.start();
//
//        try {
//            t1.join();
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////      788061
//        System.out.println("Counter is: " + c.get());
  }

  public static boolean isPrime(long number) {
    if(number == 2){
      return true;
    }
    if(number % 2 == 0){
      return false;
    }

    for (int i = 3; i <= Math.sqrt(number); i+=2) {
      if (number % i == 0) {
        return false;
      }
    }

    return true;
  }
}
