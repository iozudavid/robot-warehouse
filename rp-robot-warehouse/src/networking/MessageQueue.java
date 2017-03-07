package networking;

import java.util.EmptyQueueException;
import java.util.Queue;

import warehouse.Coordinate;

public class MessageQueue {
	//Outgoing queue
	private Queue<String> msgOutQueue = new Queue<String>();
	
	//Incoming queue
	private Queue<String> receivedQueue = new Queue<String>();
	private Queue<Coordinate> coordinateQueue = new Queue<Coordinate>();

	
	//Msg out queue methods
	public String getMessage() {
		String rtn;
		while (true) {
			try {
				rtn = (String) msgOutQueue.pop();
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

	//Received strings methods
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

	//Received coordinates methods
	public Coordinate getReceivedCoordinate() {
		Coordinate rtn;
		while (true) {
			try {
				rtn = (Coordinate) coordinateQueue.pop();
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
