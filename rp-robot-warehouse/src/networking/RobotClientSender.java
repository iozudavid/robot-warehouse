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
				Message msg = messageQueue.getOutgoingMessage();
				if (msg != null) {
					if (msg.isMessage()) {
						toServer.writeInt(2);
						toServer.writeUTF(msg.getMsg());
					} else {
						toServer.writeInt(0);
						toServer.writeUTF(msg.getCoordinate().getX() + "," + msg.getCoordinate().getY());
					}
					toServer.flush();
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
