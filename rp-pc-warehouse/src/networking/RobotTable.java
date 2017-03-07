package networking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import warehouse.Coordinate;

public class RobotTable {
	//Outgoing table
	private ConcurrentMap<String, MessageQueue> queueTable = new ConcurrentHashMap<String, MessageQueue>();
	
	//Incoming tables
	private BlockingQueue<Message> receivedMessages = new LinkedBlockingQueue<Message>();
	private BlockingQueue<Message> receivedCoordinates = new LinkedBlockingQueue<Message>();

	
	//For outing table queueTable
	public void addRobot(String robotName) {
		queueTable.putIfAbsent(robotName, new MessageQueue());
	}

	public MessageQueue getMessages(String robotName) {
		return queueTable.get(robotName);
	}

	public boolean addMessage(String robotName, String msg) {
		MessageQueue robotQueue = queueTable.get(robotName);
		if (robotQueue != null) {
			robotQueue.offer(msg);
			return true;
		}
		return false;
	}

	//For incoming table for STRINGS
	public void addReceivedMessage(String sender, String msg) {
		receivedMessages.offer(new Message(sender,msg));
	}

	public Message takeReceivedMessage() {
		while (true) {
			try {
				return (receivedMessages.take());
			} catch (InterruptedException e) {
				System.err.println(e);
			}

		}
	}
	
	
	//For incoming table for COORDINATES	
	public void addReceivedCoordinate(String sender,Coordinate c) {
		receivedCoordinates.offer(new Message(sender,c));
	}

	public Message takeReceivedCoordinate() {
		while (true) {
			try {
				return (receivedCoordinates.take());
			} catch (InterruptedException e) {
				System.err.println(e);
			}

		}
	}
}
