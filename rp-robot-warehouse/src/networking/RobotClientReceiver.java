package networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import warehouse.Coordinate;
import warehouse.Path;

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
				int mode = fromServer.readInt();
				if (mode == 0) {
					// Coordinate
					String coordinateStr = fromServer.readUTF();
					queue.addReceivedCoordinate(toCoordinate(coordinateStr));
				} else if (mode == 1) {
					// Path
					ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
					int numOfItems = 0;
					String msgStart = fromServer.readUTF();
					while (!msgStart.equals("PATHSTART")) {
						msgStart = fromServer.readUTF();
					}
					String receivedMessage = fromServer.readUTF();
					while (isCoordinate(receivedMessage)) {
						coordinates.add(toCoordinate(receivedMessage));
						receivedMessage = fromServer.readUTF();
					}
					if (receivedMessage.equals("NUMOFITEMS")) {
						receivedMessage = fromServer.readUTF();
						numOfItems = Integer.parseInt(receivedMessage);
					}
					queue.addReceivedPath(new Path(coordinates, numOfItems));
				} else if (mode == 2) {
					// String
					String receivedMessage1 = fromServer.readUTF();
					queue.addReceivedString(receivedMessage1);
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private boolean isCoordinate(String s) {
		return coordSplit(s).length == 2;
	}

	private Coordinate toCoordinate(String s) {
		String[] messageSplit = coordSplit(s);
		if (messageSplit.length == 2) {
			return new Coordinate(Integer.parseInt(messageSplit[0]), Integer.parseInt(messageSplit[1]));
		} else {
			return new Coordinate(-1, -1);
		}
	}

	private String[] coordSplit(String s) {
		// If it is -1 there are no , in the list and just the list is returned
		if (s.lastIndexOf(',') != -1) {
			// Separates the two numbers and returns them as strings in an array
			String[] rtn = { (s.substring(0, s.lastIndexOf(','))), (s.substring(s.lastIndexOf(',') + 1)) };
			return rtn;
		}
		// No , so just an array of just the input string is returned
		String[] rtn = { s };
		return rtn;
	}
}
