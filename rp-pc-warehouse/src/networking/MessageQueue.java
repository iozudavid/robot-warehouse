package networking;

import java.util.concurrent.*;

/*Originally from martins messaging excercise*/
public class MessageQueue {

	private BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();

	public void offer(Message m) {
		queue.offer(m);
	}

	public Message take() {
		//Will not return until a message is in the queue
		while (true) {
			try {
				return (queue.take());
			} catch (InterruptedException e) {
				System.err.println(e);
			}

		}
	}
}
