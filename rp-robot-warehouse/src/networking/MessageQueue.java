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
	private Queue<String> stringQueue = new Queue<String>();
	private Queue<Coordinate> coordinateQueue = new Queue<Coordinate>();
	private Queue<Path> pathQueue = new Queue<Path>();

	// Msg out queue methods
	public String getOutgoingMessage() {
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

	public void addOutgoingMessage(String msg) {
		msgOutQueue.addElement(msg);
	}

	// Received strings methods
		//Add to the queues
	
	public void addReceivedString(String msg){
		stringQueue.addElement(msg);
	}
	
	public void addReceivedCoordinate(Coordinate msg){
		coordinateQueue.addElement(msg);
	}
	
	public void addReceivedPath(Path msg){
		pathQueue.addElement(msg);
	}

		//Get from the queues
}
