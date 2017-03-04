package networking;

import java.io.DataInputStream;
import java.io.IOException;

public class RobotClientReceiver extends Thread {

	private DataInputStream fromServer;
	private MessageQueue queue;

	public RobotClientReceiver(DataInputStream fromServer,MessageQueue queue) {
		this.fromServer = fromServer;
		this.queue = queue;
	}

	public void run() {
		try {
			while (true) {
				String message = fromServer.readUTF();
				if (message != null){
					queue.addReceivedMessage(message);
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
