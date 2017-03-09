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
		ArrayList<Coordinate> c = new ArrayList<Coordinate>();
		c.add(new Coordinate(2, 3));
		c.add(new Coordinate(2, 4));
		c.add(new Coordinate(2, 5));
		c.add(new Coordinate(2, 6));
		c.add(new Coordinate(2, 7));
		Path p = new Path(c, 33);
		rs.sendPath(robotName, p);
		System.out.println("Message sent");

	}

}
