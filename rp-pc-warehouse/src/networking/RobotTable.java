package networking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

public class RobotTable {
	// Outgoing table
	private ConcurrentMap<String, MessageQueue> queueTable = new ConcurrentHashMap<String, MessageQueue>();

	// Incoming tables
	//private ConcurrentMap<String, ReceivedQueue> receivedMessages = new ConcurrentHashMap<String, ReceivedQueue>();
	//private ConcurrentMap<String, ReceivedQueue> receivedCoordinates = new ConcurrentHashMap<String, ReceivedQueue>();
	private BlockingQueue<Message> receivedMessages = new LinkedBlockingQueue<Message>();
	private BlockingQueue<Message> receivedCoordinates = new LinkedBlockingQueue<Message>();

	// For outing table queueTable
	public void addRobot(String robotName) {
		queueTable.putIfAbsent(robotName, new MessageQueue());
		//receivedMessages.putIfAbsent(robotName, new ReceivedQueue());
		//receivedCoordinates.putIfAbsent(robotName, new ReceivedQueue());
	}

	public MessageQueue getMessages(String robotName) {
		return queueTable.get(robotName);
	}

	public boolean addMessage(String robotName, Message msg) {
		MessageQueue robotQueue = queueTable.get(robotName);
		if (robotQueue != null) {
			robotQueue.offer(msg);
			return true;
		}
		return false;
	}

	// For incoming table for STRINGS
	public void addReceivedMessage(Message msg) {
		//ReceivedQueue robotQueue = receivedMessages.get(sender);
		receivedMessages.offer(msg);
	}

	public Message takeReceivedMessage() {
		//ReceivedQueue robotQueue = receivedMessages.get(nxtName);
		try {
			return (receivedMessages.take());
		} catch (InterruptedException e) {
			System.err.println(e);
		}
		return null;
	}

	public boolean isReceivedEmpty() {
		//ReceivedQueue robotQueue = receivedMessages.get(nxtName);
		return receivedMessages.isEmpty();
	}

	// For incoming table for COORDINATES
	public void addReceivedCoordinate(Message c) {
		//ReceivedQueue robotQueue = receivedMessages.get(nxtName);
		receivedCoordinates.offer(c);
	}

	public Message takeReceivedCoordinate() {
		try {
			return receivedCoordinates.take();
		} catch (InterruptedException e) {
			System.err.println(e);
		}
		return null;
	}

	public boolean isCoordinateEmpty() {
		//ReceivedQueue robotQueue = receivedMessages.get(nxtName);
		return receivedCoordinates.isEmpty();
	}
}