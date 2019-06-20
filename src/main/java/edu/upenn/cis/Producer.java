package edu.upenn.cis;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Class for Producer thread.
 * 
 * Producer pushes integer onto the shared queue as long as it is not full.
 * Although arraylist is dynamic, we use the size variable to mimic a fixed size
 * queue for our problem.
 */
public class Producer extends Thread{

	static final Logger logger = Logger.getLogger(Producer.class);	

	private final ArrayList<Integer> sharedQueue;
	private final int queueSize;

	public Producer(ArrayList<Integer> sharedQueue, int size) {
		this.sharedQueue = sharedQueue;
		this.queueSize = size;
	}

	/**
	 * Method which pushes an element passed as argument to shared queue.
	 * @param i - item to be added
	 * @throws InterruptedException
	 */
	private void addToQueue(int i) throws InterruptedException {
		logger.info("[Output from log4j] Adding element to queue");//This would be logged in the log file created and to the console.
		//wait if the queue is full
		while (true) {
		    synchronized (sharedQueue) {
			if (sharedQueue.size() == queueSize) {
			// Synchronizing on the sharedQueue to make sure no more than one
			// thread is accessing the queue same time.
				System.out.println("Queue is full!");
				sharedQueue.wait();
				// We use wait as a way to avoid polling the queue to see if
				// there was any space for the producer to push.
			} else {

			    //Adding element to queue and notifying all waiting consumers
			    
			    sharedQueue.add(i);
			    sharedQueue.notifyAll();
			    break;
			}
		    }
		}
	}
	
	public void run() {
		//Adding 10 elements onto queue.
		for (int i = 0; i < 10; i++) {
			try {
				System.out.println("Producer pushed: " + i + " onto shared queue");				
				addToQueue(i);
			} catch (InterruptedException ex) {
				logger.error("Interrupt Exception in Producer thread.");
			}
		}
	}
	
}
