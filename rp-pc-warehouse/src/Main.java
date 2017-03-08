import java.awt.EventQueue;
import java.util.ArrayList;

import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import networking.RobotServer;
import warehouse.Coordinate;
import warehouse.PathFinding;
import warehouse.SearchCell;
import warehouse.jobInput.JobAssignment;
import warehouse.jobInput.Reading;
import warehouse.jobInput.SortJobs;
import warehouseInterface.RunWarehouse;
import warehouseInterface.Window;

public class Main {

	public static void main(String[] args) {
		
		JobAssignment jobs  = new JobAssignment();
		
		RunWarehouse.runWarehouseInterface();
		
		//A* search on test coordinates
		SearchCell start=new SearchCell(new Coordinate(0,0));
		SearchCell goal=new SearchCell(new Coordinate(6,6));
		PathFinding graph=new PathFinding(start,goal);
		ArrayList<Coordinate> list=graph.aStar();
		
		//Information about single nxt robot
		String robotName = "NXT";
		String robotAddress = "0016530C73B0";

		//Infomation put into NXTInfo list which can be itterated through
		NXTInfo[] robots = { new NXTInfo(NXTCommFactory.BLUETOOTH, robotName, robotAddress) };

		//Set up server
		RobotServer rs = new RobotServer(robots);
		rs.connectToNxts();
		
		//Loop to send coordinates ONE at a time
		for (Coordinate c: list){
			System.out.println("x: "+c.getX()+" y: "+c.getY());
			rs.sendCoordinates(robotName, c);
			Window.addCoordinateRobotA(c);
		}
	}
}
