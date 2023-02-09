package com.consumerproducer;

import java.util.LinkedList;

/**
 * This example shows Producer and Consumer Problem
 * 
 * @author imteyaz
 *
 */
class Processor {

	private LinkedList<Integer> list = new LinkedList<>();
	private final static int UPPER_SIZE = 2;
	private final static int LOWER_SIZE = 0;
	private final Object object = new Object();
	private int value = 0;

	public void producer() throws InterruptedException {
		synchronized (object) {
			while (true) {
				if (list.size() == UPPER_SIZE) {
					System.out.println("Waiting for removing items...");
					object.wait(); // wait() processor will lock
				} else {
					System.out.println("Adding value " + value);
					list.add(value);
					value++;
					object.notify();
				}
				Thread.sleep(500);
			}
		}
	}

	public void consumer() throws InterruptedException {
		synchronized (object) {
			while (true) {
				if (list.size() == LOWER_SIZE) {
					System.out.println("Waiting for adding items...");
					value = 0;
					object.wait(); // wait() processor will lock
				} else {
					System.out.println("Removing value " + list.removeFirst());
					object.notify();
				}
				Thread.sleep(500);
			}
		}
	}
}

public class MainApp {

	public static void main(String[] args) {

		Processor process = new Processor();

		// Producer Thread
		new Thread(() -> {
			try {
				process.producer();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

		// Consumer Thread
		new Thread(() -> {
			try {
				process.consumer();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

	}
}
