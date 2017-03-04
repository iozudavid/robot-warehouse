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
		while(true){
			String msg = messageQueue.take();
			try {
				toRobot.writeUTF(msg);
				toRobot.flush();
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
}
