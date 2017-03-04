package networking;

import java.io.DataOutputStream;

public class RobotClientSender extends Thread {

	private DataOutputStream toServer;
	private MessageQueue messageQueue;

	public RobotClientSender(DataOutputStream toServer, MessageQueue queue) {
		this.toServer = toServer;
		this.messageQueue = queue;
	}

	public void run() {
		try {
			while (true) {
				String msg = messageQueue.getMessage();
				if (msg != null) {
					toServer.writeUTF(msg);
					toServer.flush();
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
