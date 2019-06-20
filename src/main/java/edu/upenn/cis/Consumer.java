package edu.upenn.cis;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Class for Consumer thread.
 * 
 * Consumer reads integer from the shared queue as long as it is not empty.
 */
public class Consumer extends Thread{

	static final Logger logger = Logger.getLogger(Consumer.class);	

	private final ArrayList<Integer> sharedQueue;
	private final int queueSize;

	public Consumer(ArrayList<Integer> sharedQueue, int size) {
		this.sharedQueue = sharedQueue;
		this.queueSize = size;
	}

	/**
	 * Method to read from the queue.
	 * @return - element read from queue
	 * @throws InterruptedException
	 */
	private int readFromQueue() throws InterruptedException {
	    while (true) {
		synchronized (sharedQueue) {
		    if (sharedQueue.isEmpty()) {
			//If the queue is empty, we push the current thread to waiting state. Way to avoid polling.
			System.out.println("Queue is currently empty ");
			sharedQueue.wait();
		    } else {
			sharedQueue.notifyAll();
			return (Integer) sharedQueue.remove(0);
		    }
		}
	    }
	}

	public void run() {
		while(true) {
			try {
				int elementReadFromQueue = readFromQueue();
				System.out.println("Consumed " + elementReadFromQueue +" from shared queue");
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				logger.error("Interrupt Exception in Consumer thread");
			}
		}
	}


}
