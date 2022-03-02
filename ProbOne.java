import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class Guest implements Runnable {
    static ReentrantLock lock = new ReentrantLock();
    static AtomicInteger count = new AtomicInteger(1);
    static AtomicBoolean cake = new AtomicBoolean(true);
    AtomicBoolean hasBeenCounted = new AtomicBoolean(false);
    final static int numThreads = 3;
    @Override
    public void run() {
        while (count.get() != numThreads) {
            lock.lock();
            try {
                if (Thread.currentThread().getName().equals("Counter")) {
                    if (cake.compareAndSet(false, true)) {
                        int res = count.incrementAndGet();
                        if (res == numThreads-1) {
                            System.out.println("No more guests waiting to see the vase");
                        }
                    }
                } else {
                    if (!hasBeenCounted.get() && cake.compareAndSet(true, false)) {
                        hasBeenCounted.set(true);
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }
}

public class ProbOne {

    public static void main(String[] args) throws InterruptedException {
        // Declare 3 threads and name one counter
        Thread one = new Thread(new Guest());
        one.setName("Counter");
        Thread two = new Thread(new Guest());
        Thread three = new Thread(new Guest());


        one.start();
        two.start();
        three.start();

        one.join();
        two.join();
        three.join();


    }

}
