package networking;

import java.io.DataOutputStream;
import java.io.IOException;

public class RobotServerSender extends Thread {
	private DataOutputStream toRobot;
	private MessageQueue messageQueue;

	public RobotServerSender(MessageQueue m, DataOutputStream s) {
		this.messageQueue = m;
		this.toRobot = s;
	}

	public void run() {
		// Continually checks if there are any messages in the queue and if
		// there are it sends them
		while (true) {
			Message msg = messageQueue.take();
			try {
				if (msg.isCoordinate()) {
					// Coordinate sending
					toRobot.writeInt(0);
				} else if (msg.isPath()) {
					// Path sending
					toRobot.writeInt(1);
					String strCoord = msg.getCoord().getX()+","+msg.getCoord().getY();
					toRobot.writeUTF(strCoord);
				} else {
					toRobot.writeInt(2);
					toRobot.writeUTF(msg.getMsg());
				}
				// Not sure why but you need this (Below)
				toRobot.flush();
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
}
