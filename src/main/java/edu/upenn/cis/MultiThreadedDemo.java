package edu.upenn.cis;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Demo to showcase multi-threading and use of wait, notifyAll, synchronized.
 * 
 * About the problem we are solving:
 * We try to solve Producer Consumer problem which is a classical problem
 * to understand synchronization. We create a producer thread and a consumer
 * thread and use a common queue(ArrayList here) where the producer pushes and
 * consumer consumes from.The delay added in the consumer thread ensures wait
 * and notifyAll gets called.
 * 
 */
public class MultiThreadedDemo {
	
	/* Using log4j framework for logging.
	 * The configuration for logger would be in log4j.properties
	 */
	static final Logger logger = Logger.getLogger(MultiThreadedDemo.class);	

	public static void main(String args[]) {
		logger.info("Solving Producer Consumer Problem");
		
		// Arraylist here acts as a shared queue.Its would a better idea to use
		// Vector here instead of ArrayList since Arraylist is not synchronized
		// across threads while Vector is.   
		ArrayList<Integer> sharedQueue = new ArrayList<Integer>();
		int sizeOfQueue = 4;

		//Creating a producer thread
		Producer producerObj= new Producer(sharedQueue,sizeOfQueue);
		Thread prodThread = new Thread(producerObj);

		//Creating consumer thread.
		Consumer consumerObj= new Consumer(sharedQueue,sizeOfQueue);
		Thread consThread = new Thread(consumerObj);

		//Starting both producer and consumer thread.
		prodThread.start();
		consThread.start();
	}
}
