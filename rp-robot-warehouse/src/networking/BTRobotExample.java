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

		r.sendMessage("Hello");
		r.sendMessage("World");
		r.sendCoordinate(new Coordinate(2,3));
		r.sendCoordinate(new Coordinate(3, 3));
		
//		String s = r.getReceivedMessage();
//		System.out.println(s);
//		String s2 = r.getReceivedMessage();
//		System.out.println(s2);
		
//		Coordinate c = r.getCoordinate();
//		Coordinate c2 = r.getCoordinate();
//		System.out.println(c.toString());
//		System.out.println(c2.toString());
//		
//		Path p = r.getPath();
//		while(!p.reachedEnd()){
//			Coordinate c = p.getNextCoord();
//			System.out.println("X: "+c.getX()+" Y: "+c.getY());
//		}
//		System.out.println("No. of items "+p.getNumberOFItems());
		
//		Path p = r.getPath();
//		Path p2 = r.getPath();
//		while(!p.reachedEnd()){
//			Coordinate c = p.getNextCoord();
//			System.out.println("X: "+c.getX()+" Y: "+c.getY());
//		}
//		System.out.println("No. of items "+p.getNumberOFItems());//		while(!p.reachedEnd()){
//		while (!p2.reachedEnd()){
//			Coordinate c = p2.getNextCoord();
//			System.out.println("X: "+c.getX()+" Y: "+c.getY());
//		}
//		System.out.println("No. of items "+p2.getNumberOFItems());		
	}
}
