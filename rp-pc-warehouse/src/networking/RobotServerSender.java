package networking;

import java.io.DataOutputStream;
import java.io.IOException;

public class RobotServerSender extends Thread{
	private DataOutputStream toRobot;
	private MessageQueue messageQueue;

	public RobotServerSender(MessageQueue m, DataOutputStream s){
		this.messageQueue = m;
		this.toRobot = s;
	}
	
	public void run(){
		//Continually checks if there are any messages in the queue and if there are it sends them
		while(true){
			String msg = messageQueue.take();
			try {
				toRobot.writeUTF(msg);
				//Not sure why but you need this (Below)
				toRobot.flush();
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
}
