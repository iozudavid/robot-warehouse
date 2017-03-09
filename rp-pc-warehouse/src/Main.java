import java.awt.EventQueue;
import java.util.ArrayList;

import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import networking.RobotServer;
import warehouse.Coordinate;
import warehouse.Path;
import warehouse.PathFinding;
import warehouse.SearchCell;
import warehouse.jobInput.JobAssignment;
import warehouse.jobInput.Reading;
import warehouse.jobInput.SortJobs;
import warehouseInterface.RunWarehouse;
import warehouseInterface.Window;

public class Main {

	public static void main(String[] args) {

		//JobAssignment jobs = new JobAssignment();

		//RunWarehouse.runWarehouseInterface();

		// A* search on test coordinates
		SearchCell start = new SearchCell(new Coordinate(0, 0));
		SearchCell goal = new SearchCell(new Coordinate(6, 6));
		PathFinding graph = new PathFinding(start, goal);
		ArrayList<Coordinate> list = graph.aStar();
		Path c = new Path(list,1);

		// Information about single nxt robot
		String robotName = "NXT";
		String robotAddress = "0016530C73B0";

		// Infomation put into NXTInfo list which can be itterated through
		NXTInfo[] robots = { new NXTInfo(NXTCommFactory.BLUETOOTH, robotName, robotAddress) };

		// Set up server
		RobotServer rs = new RobotServer(robots);
		rs.connectToNxts();

		rs.sendPath(robotName, c);
		//Window.addCoordinateRobotA(c);
	}
}
