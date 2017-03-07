package networking;

import java.io.DataInputStream;
import java.io.IOException;

import warehouse.Coordinate;

public class RobotServerReceiver extends Thread {

	private RobotTable robotTable;
	private DataInputStream fromRobot;
	private String robotName;

	public RobotServerReceiver(RobotTable r, DataInputStream i, String name) {
		this.robotTable = r;
		this.fromRobot = i;
		this.robotName = name;
	}

	public void run() {
		//A point worth noting about this implementation is that you should NOT
		//send "," for a plain text string otherwise it WILL break it
		try {
			while (true) {
				//Reads from the input stream
				String response = fromRobot.readUTF();
				if (response != null) {
					//Splits the string to see if it is a coordinate
					String[] responseSplit = response.split(",");
					if (responseSplit.length == 2) {
						//It is a coordinate
						Coordinate c = new Coordinate(Integer.parseInt(responseSplit[0]),
								Integer.parseInt(responseSplit[1]));
						robotTable.addReceivedCoordinate(robotName, c);
					} else {
						//Not a coordinate just a string
						robotTable.addReceivedMessage(robotName, response);
					}
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
