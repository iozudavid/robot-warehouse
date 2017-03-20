package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import warehouse.Coordinate;
import warehouse.Path;
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
		//The command below will wait until a connection is received, no timeout
		BTConnection bt = Bluetooth.waitForConnection();
		//Used to clear any messages that the bluetooth class pushed out
		LCD.clear();
		
		//Connection received so data streams in and out are opened
		DataInputStream fromServer = bt.openDataInputStream();
		DataOutputStream toServer = bt.openDataOutputStream();

		//Sender and receiver threads are created
		RobotClientReceiver r = new RobotClientReceiver(fromServer, queue);
		RobotClientSender s = new RobotClientSender(toServer, queue);

		//Threads started
		r.start();
		s.start();
	}
	
	//Send a string based message (i.e. a command)
	public void sendMessage(String msg){
		queue.addOutgoingMessage(new Message(msg));
	}
	
	//Send a coordinate to the server
	public void sendCoordinate(Coordinate c){
		queue.addOutgoingMessage(new Message(c));
	}
	
	public Path getPath(){
		return queue.getReceivedPath();
	}
	//Returns top of received message queue
	public String getReceivedMessage(){
		return queue.getReceivedString();
	}
	
	//Returns top of received coordinates queue
	public Coordinate getCoordinate(){
		return queue.getReceivedCoordinate();
	}
}
