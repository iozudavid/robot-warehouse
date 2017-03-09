package networking;

import warehouse.Coordinate;
import warehouse.Path;

public class BTRobotExample {

	// This is an example of how to set up and receive messages/coordinates from
	// the server

	public static void main(String args[]) {
		// Wait for connection from server, waitForConnection will NOT end until
		// connection is received
		RobotClient r = new RobotClient();
		r.waitForConnection();

		Path p = r.getPath();
		while(!p.reachedEnd()){
			Coordinate c = p.getNextCoord();
			System.out.println("X: "+c.getX()+" Y: "+c.getY());
		}
		System.out.println("No. of items "+p.getNumberOFItems());
	}
}
