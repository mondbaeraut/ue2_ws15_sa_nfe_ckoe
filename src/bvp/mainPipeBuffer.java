package bvp;

import bvp.pipe.PipeBufferImpl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by mod on 11/16/15.
 */
public class mainPipeBuffer {


    public static void main(String[] args) {
        BlockingQueue q = new ArrayBlockingQueue(4);
        Producer p2 = new Producer(q,2);
        Producer p3 = new Producer(q,3);
        Producer p4 = new Producer(q,4);

        Consumer c1 = new Consumer(q);
        new Thread(p2).start();
        new Thread(p3).start();
        new Thread(p4).start();
        new Thread(c1).start();
    }

    static class Producer implements Runnable {
        private final BlockingQueue queue;
        private int value;
        Producer(BlockingQueue q,int value) {
            this.value = value;
            queue = q; }
        public void run() {
            try {
                while (true) { queue.put(produce()); }
            } catch (InterruptedException ex) {ex.printStackTrace();}
        }
        Object produce() {return value;}
    }

    static class Consumer implements Runnable {
        private final BlockingQueue queue;
        Consumer(BlockingQueue q) { queue = q; }
        public void run() {
            try {
                while (true) { consume(queue.take()); }
            } catch (InterruptedException ex) { ex.printStackTrace();}
        }
        void consume(Object x) {
            System.out.println(x.toString());
        }
    }
}
