package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import warehouse.Coordinate;
import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class RobotClient {
	
	private MessageQueue queue;

	public RobotClient(){
		queue = new MessageQueue();
	}
	
	//Will not end until timeout(?) or a connection is received from the server
	public void waitForConnection() {
		System.out.println("Waiting for connection ...");
		BTConnection bt = Bluetooth.waitForConnection();
		LCD.clear();
		DataInputStream fromServer = bt.openDataInputStream();
		DataOutputStream toServer = bt.openDataOutputStream();

		RobotClientReceiver r = new RobotClientReceiver(fromServer, queue);
		RobotClientSender s = new RobotClientSender(toServer, queue);

		r.start();
		s.start();
		LCD.clear();
		LCD.drawString("Threads started", 0, 0);

	}
	
	//Send a string based message (i.e. a command)
	public void sendMessage(String msg){
		queue.addMessage(msg);
	}
	
	//Send a coordinate to the server
	public void sendCoordinate(Coordinate c){
		String strCoord = c.getX()+","+c.getY();
		queue.addMessage(strCoord);
	}
	
	//Returns top of received message queue
	public String getReceivedMessage(){
		return queue.getReceivedMessage();
	}
	
	//Returns top of received coordinates queue
	public Coordinate getCoordinate(){
		return queue.getReceivedCoordinate();
	}
}
