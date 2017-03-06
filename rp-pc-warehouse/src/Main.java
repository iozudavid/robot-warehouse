import java.util.ArrayList;

import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import networking.RobotServer;
import warehouse.Coordinate;
import warehouse.PathFinding;
import warehouse.SearchCell;

public class Main {

	public static void main(String[] args) {
		SearchCell start=new SearchCell(new Coordinate(0,0));
		SearchCell goal=new SearchCell(new Coordinate(6,6));
		PathFinding graph=new PathFinding(start,goal);
		ArrayList<Coordinate> list=graph.aStar();
//		for (Coordinate c: list){
//			System.out.println("x: "+c.getX()+" y: "+c.getY());
//		}
		String robotName = "NXT";
		String robotAddress = "0016530C73B0";

		NXTInfo[] robots = { new NXTInfo(NXTCommFactory.BLUETOOTH, robotName, robotAddress) };

		RobotServer rs = new RobotServer(robots);
		rs.connectToNxts();
		for (Coordinate c: list){
			System.out.println("x: "+c.getX()+" y: "+c.getY());
			rs.sendCoordinates(robotName, c);
		}
	}

	private String[] coordSplit(String s) {
		if (s.lastIndexOf(',') != -1) {
			String[] rtn = { (s.substring(0, s.lastIndexOf(','))), (s.substring(s.lastIndexOf(',') + 1)) };
			return rtn;
		}
		String[] rtn = { s };
		return rtn;
	}
}
