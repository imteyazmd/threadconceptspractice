package com.consumerproducer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Worker {
    private static ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void produce() throws InterruptedException {
        lock.lock();
        System.out.println("Producer method");
        condition.await();
        System.out.println("Again the producer method");
        lock.unlock();
    }

    public void consume() throws InterruptedException {
        //we want to make sure that we start with producer
        Thread.sleep(3000);
        lock.lock();
        System.out.println("consumer method");
        Thread.sleep(2000);
        //notify()
        condition.signal();
        lock.unlock();
    }
}

public class ProducerConsumerWithReentrantLock {
    public static void main(String[] args) {

        Worker worker = new Worker();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    worker.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    worker.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
    }
}
