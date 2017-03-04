package networking;

import java.util.EmptyQueueException;
import java.util.Queue;

import warehouse.Coordinate;

public class MessageQueue {
	private Queue<String> msgOutQueue = new Queue<String>();
	private Queue<String> receivedQueue = new Queue<String>();
	private Queue<Coordinate> coordinateQueue = new Queue<Coordinate>();

	public String getMessage() {
		String rtn;
		while (true) {
			try {
				rtn = (String) receivedQueue.pop();
			} catch (EmptyQueueException e) {
				rtn = null;
			}
			if (rtn != null) {
				return rtn;
			}
		}
	}

	public void addMessage(String msg) {
		msgOutQueue.addElement(msg);
	}

	public void addReceivedMessage(String msg) {
		receivedQueue.addElement(msg);
	}

	public String getReceivedMessage() {
		String rtn;
		while (true) {
			try {
				rtn = (String) receivedQueue.pop();
			} catch (EmptyQueueException e) {
				rtn = null;
			}
			if (rtn != null) {
				return rtn;
			}
		}
	}

	public Coordinate getReceivedCoordinate() {
		Coordinate rtn;
		while (true) {
			try {
				rtn = (Coordinate) receivedQueue.pop();
			} catch (EmptyQueueException e) {
				rtn = null;
			}
			if (rtn != null) {
				return rtn;
			}
		}
	}

	public void addCoordinate(Coordinate c) {
		coordinateQueue.addElement(c);
	}
}
