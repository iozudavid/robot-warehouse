package networking;

import lejos.nxt.LCD;

public class RobotNtrkTst {
	public static void main(String args[]) {
		RobotClient r = new RobotClient();
		r.waitForConnection();
		while (true) {
			String msg = r.getReceivedMessage();
			if (msg != null) {
				LCD.clear();
				System.out.println(msg);
			}
		}
	}
}
