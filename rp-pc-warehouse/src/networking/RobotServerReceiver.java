package networking;

import java.io.DataInputStream;
import java.io.IOException;

import warehouse.Coordinate;

public class RobotServerReceiver extends Thread {

	private RobotTable robotTable;
	private DataInputStream fromRobot;
	private boolean runThread;
	private String robotName;

	public RobotServerReceiver(RobotTable r, DataInputStream i, String name) {
		this.robotTable = r;
		this.fromRobot = i;
		this.robotName = name;
	}

	public void run() {
		try {
			runThread = true;
			while (runThread) {
				String response = fromRobot.readUTF();
				if (response != null) {
					String[] responseSplit = response.split(",");
					if (responseSplit.length == 2) {
						Coordinate c = new Coordinate(Integer.parseInt(responseSplit[0]),
								Integer.parseInt(responseSplit[1]));
						robotTable.addReceivedCoordinate(robotName, c);
					} else {
						robotTable.addReceivedMessage(robotName, response);
					}
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
