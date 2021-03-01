package ProducerConsumer;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * https://blog.csdn.net/u010983881/article/details/78554671
 */
public class ProducerConsumerByBQ {
    private static final int CAPACITY = 5;

    public static void main(String[] args) {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();

        Thread producer1 = new Producer("P-1", queue, CAPACITY);
        Thread producer2 = new Producer("P-2", queue, CAPACITY);

        Thread consumer1 = new Consumer("C1", queue, CAPACITY);
        Thread consumer2 = new Consumer("C2", queue, CAPACITY);
        Thread consumer3 = new Consumer("C3", queue, CAPACITY);

        producer1.start();
        producer2.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();
    }

    public static class Producer extends Thread {
        private LinkedBlockingQueue<Integer> blockingQueue;
        String name;
        int maxSize;
        int i = 0;

        public Producer(String name, LinkedBlockingQueue<Integer> queue, int maxSize) {
            super(name);
            this.name = name;
            this.blockingQueue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    blockingQueue.put(i);
                    System.out.println("[" + name + "] Producing value: +" + i);
                    i++;

                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Consumer extends Thread {
        private LinkedBlockingQueue<Integer> blockingQueue;
        String name;
        int maxSize;

        public Consumer(String name, LinkedBlockingQueue<Integer> queue, int maxSize) {
            super(name);
            this.name = name;
            this.blockingQueue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int x = blockingQueue.take();
                    System.out.println("[" + name + "] Consuming: " + x);

                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
