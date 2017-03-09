package networking;

import java.util.ArrayList;
import java.util.EmptyQueueException;
import java.util.Queue;

import warehouse.Coordinate;
import warehouse.Path;

public class MessageQueue {
	// Outgoing queue
	private Queue<String> msgOutQueue = new Queue<String>();

	// Incoming queue
	private Queue<String> receivedQueue = new Queue<String>();
	private Queue<Coordinate> coordinateQueue = new Queue<Coordinate>();

	// Msg out queue methods
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

	// Received strings methods
	public void addReceivedMessage(String msg) {
		receivedQueue.addElement(msg);
	}

	private String getReceivedMessage() {
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

	// Received coordinates methods
	private Coordinate getReceivedCoordinate() {
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

	// Get Path, currently cannot handle when there has been an error in sending
	// the path
	public Path getPath() {
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		int numOfItems = 0;
		String msgStart = this.getReceivedMessage();
		while (!msgStart.equals("PATHSTART")) {
			msgStart = this.getReceivedMessage();
		}
		String receivedMessage = this.getReceivedMessage();
		while (isCoordinate(receivedMessage)) {
			coordinates.add(toCoordinate(receivedMessage));
			receivedMessage = this.getReceivedMessage();
		}
		if (receivedMessage.equals("NUMOFITEMS")) {
			receivedMessage = this.getReceivedMessage();
			numOfItems = Integer.parseInt(receivedMessage);
		}
		return new Path(coordinates, numOfItems);
	}

	// Coordinate splitting
	private boolean isCoordinate(String s) {
		return coordSplit(s).length == 2;
	}

	private Coordinate toCoordinate(String s) {
		String[] messageSplit = coordSplit(s);
		if (messageSplit.length == 2) {
			return new Coordinate(Integer.parseInt(messageSplit[0]), Integer.parseInt(messageSplit[1]));
		} else {
			return new Coordinate(-1, -1);
		}
	}

	private String[] coordSplit(String s) {
		// If it is -1 there are no , in the list and just the list is returned
		if (s.lastIndexOf(',') != -1) {
			// Separates the two numbers and returns them as strings in an array
			String[] rtn = { (s.substring(0, s.lastIndexOf(','))), (s.substring(s.lastIndexOf(',') + 1)) };
			return rtn;
		}
		// No , so just an array of just the input string is returned
		String[] rtn = { s };
		return rtn;
	}

}
