package networking;

import warehouse.Coordinate;

public class BTRobotExample {

	// This is an example of how to set up and receive messages/coordinates from
	// the server

	public static void main(String args[]) {
		// Wait for connection from server, waitForConnection will NOT end until
		// connection is received
		RobotClient r = new RobotClient();
		r.waitForConnection();

		// Simple loop to keep trying to get items from the coordinate queue,
		// this is how you may have to
		// implement getting coordinates, contact me if you would like me to
		// change anything about this
		// implementation
		while (true) {
			//getCoordinate will not return until a coordinate is in the coordinate queue
			Coordinate msg = r.getCoordinate();
			//Perform action on coordinate
			
			//System.out.println(msg.getX() + " " + msg.getY());
		}
	}
}
