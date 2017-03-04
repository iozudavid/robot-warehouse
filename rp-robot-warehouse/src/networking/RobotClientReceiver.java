package networking;

import java.io.DataInputStream;
import java.io.IOException;

import warehouse.Coordinate;

public class RobotClientReceiver extends Thread {

	private DataInputStream fromServer;
	private MessageQueue queue;

	public RobotClientReceiver(DataInputStream fromServer, MessageQueue queue) {
		this.fromServer = fromServer;
		this.queue = queue;
	}

	public void run() {
		try {
			while (true) {
				String message = fromServer.readUTF();
				if (message != null) {
					String[] messageSplit = coordSplit(message);
					if (messageSplit.length == 2) {
						Coordinate c = new Coordinate(Integer.parseInt(messageSplit[0]),
								Integer.parseInt(messageSplit[1]));
						queue.addCoordinate(c);
					} else {
						queue.addReceivedMessage(message);
					}
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private String[] coordSplit(String s) {
		if (s.lastIndexOf(',') != -1) {
			String[] rtn = { (s.substring(0, s.lastIndexOf(','))), (s.substring(s.lastIndexOf(',') + 1)) };
			return rtn;
		}
		String[] rtn = { s };
		return rtn;
	}
}
