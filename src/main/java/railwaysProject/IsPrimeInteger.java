package railwaysProject;

import org.apache.commons.lang3.ArrayUtils;

import java.util.concurrent.*;

import static org.apache.commons.math3.primes.Primes.isPrime;

public class IsPrimeInteger {
    public static void main(String[] args) {
        calculatePrimesWith4Threads();  // avg 2.689
        calculatePrimesWithOneThread(); // avg 3.237
    }

    public static void calculatePrimesWithOneThread() {
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(10000000);

        ExecutorService service = Executors.newFixedThreadPool(1);

        MyThread thread = new MyThread(latch, 1, 10000000);
        Future<Boolean[]> futureList = service.submit(thread);

        try {
            latch.await();
        } catch (InterruptedException exc) {
            System.out.println(exc.getMessage());
        }

        service.shutdown();
        long end = System.currentTimeMillis();

        try {
            Boolean[] isPrimeList = futureList.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(" ");
        float sec = (end - start) / 1000F; System.out.println("One thread: " + sec + " seconds");
    }

    public static void calculatePrimesWith4Threads() {
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(10000000);


        ExecutorService service = Executors.newFixedThreadPool(4);

        MyThread thread1 = new MyThread(latch, 1, 25000000);
        MyThread thread2 = new MyThread(latch, 2500001, 5000000);
        MyThread thread3 = new MyThread(latch, 5000001, 7500000);
        MyThread thread4 = new MyThread(latch, 7500001, 10000000);

        Future<Boolean[]> futureList1 = service.submit(thread1);
        Future<Boolean[]> futureList2 = service.submit(thread2);
        Future<Boolean[]> futureList3 = service.submit(thread3);
        Future<Boolean[]> futureList4 = service.submit(thread4);


        try {
            latch.await();
        } catch (InterruptedException exc) {
            System.out.println(exc.getMessage());
        }

        service.shutdown();
        long end = System.currentTimeMillis();

        try {
            Boolean[] isPrimeList1 = futureList1.get();
            Boolean[] isPrimeList2 = futureList2.get();
            Boolean[] isPrimeList3 = futureList3.get();
            Boolean[] isPrimeList4 = futureList4.get();
            Boolean[] isPrimeList = new Boolean[0];
            isPrimeList = ArrayUtils.addAll(isPrimeList, isPrimeList1);
            isPrimeList = ArrayUtils.addAll(isPrimeList, isPrimeList2);
            isPrimeList = ArrayUtils.addAll(isPrimeList, isPrimeList3);
            isPrimeList = ArrayUtils.addAll(isPrimeList, isPrimeList4);

            for (int i = 1; i < 101; i++) {
                System.out.print(isPrimeList1 + " ");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(" ");
        float sec = (end - start) / 1000F; System.out.println("Multiple threads: " + sec + " seconds");
    }

    static class MyThread implements Callable<Boolean[]> {

        private CountDownLatch latch;
        private Boolean[] isPrimeList;
        int start;
        int range;

        public MyThread(CountDownLatch latch, int start, int range) {
            this.latch = latch;
            this.isPrimeList = new Boolean[range];
            this.start = start;
            this.range = range;
        }

        @Override
        public Boolean[] call() {
            int index = 0;
            for (int i = start; i < start + range; i++) {
                isPrimeList[index] = isPrime(i);
                latch.countDown();
                index++;
            }
            return isPrimeList;
        }
    }
}
