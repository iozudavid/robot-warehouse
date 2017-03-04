package networking;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RobotTable {
	private ConcurrentMap<String, MessageQueue> queueTable = new ConcurrentHashMap<String, MessageQueue>();
	
	public void addRobot(String robotName){
		queueTable.putIfAbsent(robotName, new MessageQueue());
	}
	
	public MessageQueue getMessages(String robotName){
		return queueTable.get(robotName);
	}
	
	public boolean addMessage(String robotName,String msg){
		MessageQueue robotQueue= queueTable.get(robotName);
		if (robotQueue != null){
			robotQueue.offer(msg);
			return true;
		}
		return false;
	}
}
