package networking;

import java.util.ArrayList;

import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import warehouse.Coordinate;
import warehouse.Path;

public class networkingTest {

	public static void main(String[] args) {

		String robotName = "NXT";
		String robotAddress = "0016530C73B0";

		NXTInfo[] robots = { new NXTInfo(NXTCommFactory.BLUETOOTH, robotName, robotAddress) };

		RobotServer rs = new RobotServer(robots);
		rs.connectToNxts();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			System.err.println(e);
		}
		
		Message s = rs.getReceivedMessage();
		Message s2 = rs.getReceivedMessage();
		Message c = rs.getReceivedCoordinate();
		Message c2 = rs.getReceivedCoordinate();
		System.out.println("String sent from: "+s.getSender()+" Message: "+s.getMsg());
		System.out.println("String sent from: "+s2.getSender()+" Message: "+s2.getMsg());
		System.out.println("Coordinate sent from: "+c.getSender()+" Message: "+c.getCoord().toString());
		System.out.println("Coordinate sent from: "+c2.getSender()+" Message: "+c2.getCoord().toString());
		
//		String s = "Hello world!";
//		rs.sendMessage(robotName, s);
//		
//		String s2 = "Risk 4 life";
//		rs.sendMessage(robotName, s2);
		
//		Coordinate c = new Coordinate(2,3);
//		Coordinate c2 = new Coordinate(3,3);
//		rs.sendCoordinate(robotName, c);
//		rs.sendCoordinate(robotName, c2);
		
//		ArrayList<Coordinate> c = new ArrayList<Coordinate>();
//		c.add(new Coordinate(2, 3));
//		c.add(new Coordinate(2, 4));
//		c.add(new Coordinate(2, 5));
//		c.add(new Coordinate(2, 6));
//		c.add(new Coordinate(2, 7));
//		Path p = new Path(c, 33);
//		rs.sendPath(robotName, p);
	
//		ArrayList<Coordinate> c = new ArrayList<Coordinate>();
//		c.add(new Coordinate(2,2));
//		ArrayList<Coordinate> d = new ArrayList<Coordinate>();
//		d.add(new Coordinate(2,2));
//		Path p = new Path(c,33);
//		Path p2 = new Path(d,22);
//		rs.sendPath(robotName, p);
//		rs.sendPath(robotName, p2);
//		System.out.println("Message sent");

	}

}
