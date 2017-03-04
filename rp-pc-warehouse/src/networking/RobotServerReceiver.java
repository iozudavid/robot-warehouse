package networking;

import java.io.DataInputStream;
import java.io.IOException;

public class RobotServerReceiver extends Thread {

	private RobotTable robotTable;
	private DataInputStream fromRobot;
	private boolean runThread;

	public RobotServerReceiver(RobotTable r, DataInputStream i) {
		this.robotTable = r;
		this.fromRobot = i;
	}

	public void run() {
		try {
			runThread = true;
			while (runThread) {
				Integer response = fromRobot.readInt();
				if (response != null) {
					System.out.println(response);
				}
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
