package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class RobotClient {
	
	private MessageQueue queue;

	public RobotClient(){
		queue = new MessageQueue();
	}
	
	public void waitForConnection() {
		System.out.println("Waiting for connection ...");
		// Ask about this
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
		// try{
		// r.join();
		// s.join();
		// toServer.close();
		// fromServer.close();
		// bt.close();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}
	
	public void sendMessage(String msg){
		queue.addMessage(msg);
	}
	
	public String getReceivedMessage(){
		return queue.getReceivedMessage();
	}
}
