import java.util.concurrent.atomic.AtomicReference;

// CLH LOCK implementation from textbook

class CLHLock {

    private final AtomicReference<Node> tail;

    private final ThreadLocal<Node> myNode;

    private final ThreadLocal<Node> myPred;

    public CLHLock() {

        tail = new AtomicReference<>(new Node());

        myNode = ThreadLocal.withInitial(() -> new Node());

        myPred = ThreadLocal.withInitial(() -> null);

    }
    public void lock() throws InterruptedException {

        Node node = myNode.get();

        node.locked = true;

        Node pred = tail.getAndSet(node);

        myPred.set(pred);

        while (pred.locked){ }

    }
    public void unlock(){

        Node node = myNode.get();

        node.locked=false;

        myNode.set(myPred.get());

    }
    static class Node {
        volatile boolean locked = false;
    }
}

class myGuest implements Runnable {
    static CLHLock lock = new CLHLock();

    @Override
    public void run() {
        try {
            lock.lock();
            // Here is where a guest would be seeing the vase
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


public class ProbTwo {

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new myGuest());
        Thread two = new Thread(new myGuest());
        Thread three = new Thread(new myGuest());
        Thread four = new Thread(new myGuest());
        Thread five = new Thread(new myGuest());

        one.start();
        two.start();
        three.start();
        four.start();
        five.start();

        one.join();
        two.join();
        three.join();
        four.join();
        five.join();

        System.out.println("Everyones seen the vase");
    }

}
