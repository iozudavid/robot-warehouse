package networking;

import lejos.nxt.LCD;
import warehouse.Coordinate;

public class RobotNtrkTst {
	public static void main(String args[]) {
		RobotClient r = new RobotClient();
		r.waitForConnection();
		int counter = 0;
		while (true) {
			Coordinate msg = r.getCoordinate();
			if (msg != null) {
				//LCD.clear();
				System.out.println(msg.getX()+" "+msg.getY());
			}
		}
	}
}
