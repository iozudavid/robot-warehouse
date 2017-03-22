package networking;

import java.util.EmptyQueueException;
import java.util.Queue;

import warehouse.Coordinate;
import warehouse.Path;

public class MessageQueue {
	// Outgoing queue
	private Queue<Message> msgOutQueue = new Queue<Message>();

	// Incoming queue
	private Queue<String> stringQueue = new Queue<String>();
	private Queue<Coordinate> coordinateQueue = new Queue<Coordinate>();
	private Queue<Path> pathQueue = new Queue<Path>();

	// Msg out queue methods
	public Message getOutgoingMessage() {
		Message rtn;
		while (true) {
			try {
				rtn = (Message) msgOutQueue.pop();
			} catch (EmptyQueueException e) {
				rtn = null;
			}
			if (rtn != null) {
				return rtn;
			}
		}
	}

	public void addOutgoingMessage(Message msg) {
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
	public String getReceivedString(){
		String rtn;
		while (true) {
			try {
				rtn = (String) stringQueue.pop();
			} catch (EmptyQueueException e) {
				rtn = null;
			}
			if (rtn != null) {
				return rtn;
			}
		}
	}
	
	public Coordinate getReceivedCoordinate(){
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
	
	public Path getReceivedPath(){
		Path rtn;
		while (true) {
			try {
				rtn = (Path) pathQueue.pop();
			} catch (EmptyQueueException e) {
				rtn = null;
			}
			if (rtn != null) {
				return rtn;
			}
		}
	}
}
