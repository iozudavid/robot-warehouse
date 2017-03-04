package networking;

import java.util.EmptyQueueException;
import java.util.Queue;

public class MessageQueue {
	private Queue<String> msgOutQueue = new Queue<String>();
	private Queue<String> receivedQueue = new Queue<String>();

	public String getMessage() {
		String rtn;
		try {
			rtn = (String) msgOutQueue.pop();
		} catch (EmptyQueueException e) {
			rtn = null;
		}
		return rtn;
	}

	public void addMessage(String msg) {
		msgOutQueue.addElement(msg);
	}

	public void addReceivedMessage(String msg) {
		receivedQueue.addElement(msg);
	}

	public String getReceivedMessage() {
		String rtn;
		try {
			rtn = (String) receivedQueue.pop();
		} catch (EmptyQueueException e) {
			rtn = null;
		}
		return rtn;
	}
}
