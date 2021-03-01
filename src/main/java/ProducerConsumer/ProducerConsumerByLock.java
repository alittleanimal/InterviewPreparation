package ProducerConsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerByLock {
    private static final int CAPACITY = 5;
    private static final Lock lock = new ReentrantLock();
    private static final Condition fullCondition = lock.newCondition();
    private static final Condition emptyCondition = lock.newCondition();

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<Integer>();

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
        private Queue<Integer> queue;
        String name;
        int maxSize;
        int i = 0;

        public Producer(String name, Queue<Integer> queue, int maxSize) {
            super(name);
            this.name = name;
            this.queue = queue;
            this.maxSize = maxSize;
        }

        public void run() {
            while (true) {
                lock.lock();
                while (queue.size() == maxSize) {
                    System.out.println("Queue is full, Producer[" + name
                            + "] thread is waiting for consumer to take something from the queue.");
                    try {
                        fullCondition.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("[" + name + "] Producing value: +" + i);
                queue.offer(i++);

                fullCondition.signalAll();
                emptyCondition.signalAll();

                lock.unlock();

                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Consumer extends Thread {
        private Queue<Integer> queue;
        String name;
        int maxSize;

        public Consumer(String name, Queue<Integer> queue, int maxSize) {
            super(name);
            this.name = name;
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();

                while (queue.isEmpty()) {
                    try {
                        System.out.println("Queue is empty, Consumer [" + name + "] thread is waiting for Producer");
                        emptyCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int x = queue.poll();
                System.out.println("[" + name + "] Consuming value: " + x);

                fullCondition.signalAll();
                emptyCondition.signalAll();

                lock.unlock();

                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
