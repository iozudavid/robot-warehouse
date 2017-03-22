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
		System.out.println("Receiver for "+robotName+" started");
	}

	public void run() {
		try {
			int mode;
			while (true) {
				// Reads from the input stream
				mode = fromRobot.readInt();
				String response = fromRobot.readUTF();
				if (mode == 0 && response != null) {
					String[] responseSplit = response.split(",");
					Coordinate c = new Coordinate(Integer.parseInt(responseSplit[0]),
							Integer.parseInt(responseSplit[1]));
					robotTable.addReceivedCoordinate(new Message(robotName, c));
				} else if (mode == 2 && response != null) {
					robotTable.addReceivedMessage(new Message(robotName, response));
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}