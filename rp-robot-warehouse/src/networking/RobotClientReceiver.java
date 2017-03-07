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
					//Message is split to see if it is a coordinate
					//Note due to lejos not having a split method my own has been
					//implemented
					String[] messageSplit = coordSplit(message);
					if (messageSplit.length == 2) {
						//It's a coordinate
						Coordinate c = new Coordinate(Integer.parseInt(messageSplit[0]),
								Integer.parseInt(messageSplit[1]));
						queue.addCoordinate(c);
					} else {
						//It's a string
						queue.addReceivedMessage(message);
					}
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private String[] coordSplit(String s) {
		//If it is -1 there are no , in the list and just the list is returned
		if (s.lastIndexOf(',') != -1) {
			//Separates the two numbers and returns them as strings in an array
			String[] rtn = { (s.substring(0, s.lastIndexOf(','))), (s.substring(s.lastIndexOf(',') + 1)) };
			return rtn;
		}
		//No , so just an array of just the input string is returned
		String[] rtn = { s };
		return rtn;
	}
}
