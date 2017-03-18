package networking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import warehouse.Coordinate;

public class RobotTable {
	// Outgoing table
	private ConcurrentMap<String, MessageQueue> queueTable = new ConcurrentHashMap<String, MessageQueue>();

	// Incoming tables
	private ConcurrentMap<String, ReceivedQueue> receivedMessages = new ConcurrentHashMap<String, ReceivedQueue>();
	private ConcurrentMap<String, ReceivedQueue> receivedCoordinates = new ConcurrentHashMap<String, ReceivedQueue>();
	//private BlockingQueue<Message> receivedMessages = new LinkedBlockingQueue<Message>();
	//private BlockingQueue<Message> receivedCoordinates = new LinkedBlockingQueue<Message>();

	// For outing table queueTable
	public void addRobot(String robotName) {
		queueTable.putIfAbsent(robotName, new MessageQueue());
		receivedMessages.putIfAbsent(robotName, new ReceivedQueue());
		receivedCoordinates.putIfAbsent(robotName, new ReceivedQueue());
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

	// For incoming table for STRINGS
	public void addReceivedMessage(String sender, String msg) {
		ReceivedQueue robotQueue = receivedMessages.get(sender);
		robotQueue.offerString(msg);
	}

	public String takeReceivedMessage(String nxtName) {
		ReceivedQueue robotQueue = receivedMessages.get(nxtName);
		return (robotQueue.takeString());
	}

	public boolean isReceivedEmpty(String nxtName) {
		ReceivedQueue robotQueue = receivedMessages.get(nxtName);
		return robotQueue.isStringEmpty();
	}

	// For incoming table for COORDINATES
	public void addReceivedCoordinate(String nxtName, Coordinate c) {
		ReceivedQueue robotQueue = receivedMessages.get(nxtName);
		robotQueue.offerCoordinate(c);
	}

	public Coordinate takeReceivedCoordinate(String nxtName) {
		while (true) {
			ReceivedQueue robotQueue = receivedMessages.get(nxtName);
			return (robotQueue.takeCoordinate());
		}
	}

	public boolean isCoordinateEmpty(String nxtName) {
		ReceivedQueue robotQueue = receivedMessages.get(nxtName);
		return robotQueue.isCoordinateEmpty();
	}
}
