package networking;

import java.util.concurrent.*;

/*Originally from martins messaging excercise*/
public class MessageQueue {

	private BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

	public void offer(String m) {
		queue.offer(m);
	}

	public String take() {
		while (true) {
			try {
				return (queue.take());
			} catch (InterruptedException e) {
				System.err.println(e);
			}

		}
	}
}
