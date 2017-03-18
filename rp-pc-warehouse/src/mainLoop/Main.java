package mainLoop;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.print.attribute.standard.Finishings;

import Variables.Messages;
import jobPackage.JobAssignment;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import networking.Message;
import networking.RobotServer;
import warehouse.Coordinate;
import warehouse.Path;
import warehouse.PathFinding;
import warehouse.SearchCell;
import warehouse.jobInput.Job;
import warehouseInterface.RunWarehouse;
import warehouseInterface.Window;

public class Main {

	public static JobAssignment jobs;

	public static void main(String[] args) {
		// Infomation put into NXTInfo list which can be itterated through
		NXTInfo[] robots = { new NXTInfo(NXTCommFactory.BLUETOOTH, "NXT", "0016530C73B0"),
				new NXTInfo(NXTCommFactory.BLUETOOTH, "William", "00165308E546"),
				new NXTInfo(NXTCommFactory.BLUETOOTH, "Phil", "0016530A631F") };

		jobs = new JobAssignment(robots);
		
		// Set up server
		RobotServer rs = new RobotServer(robots);
		rs.connectToNxts();
		RunWarehouse.runWarehouseInterface();

		ArrayList<RobotLoop> robotLoops = new ArrayList<RobotLoop>();

		
		robotLoops.add(new RobotLoop(rs, robots[0].name, 0, jobs.RobotA));
		robotLoops.get(robotLoops.size() - 1).start();

		robotLoops.add(new RobotLoop(rs, robots[1].name, 1,jobs.RobotB));
		robotLoops.get(robotLoops.size() - 1).start();

		robotLoops.add(new RobotLoop(rs, robots[2].name, 2,jobs.RobotC));
		robotLoops.get(robotLoops.size() - 1).start();

		try {
			robotLoops.get(0).join();
			robotLoops.get(1).join();
			robotLoops.get(2).join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*Set path testing
		ArrayList<Coordinate> path1 = new ArrayList<Coordinate>();
		path1.add(new Coordinate(0,0));
		path1.add(new Coordinate(1,0));
		path1.add(new Coordinate(2,0));
		path1.add(new Coordinate(2,0));
		path1.add(new Coordinate(3,0));
		path1.add(new Coordinate(4,0));
		path1.add(new Coordinate(5,0));
		path1.add(new Coordinate(5,0));
		path1.add(new Coordinate(6,0));
		
		ArrayList<Coordinate> path2 = new ArrayList<Coordinate>();
		path2.add(new Coordinate(3,3));
		path2.add(new Coordinate(3,2));
		path2.add(new Coordinate(3,1));
		path2.add(new Coordinate(3,0));
		path2.add(new Coordinate(4,0));
		path2.add(new Coordinate(5,0));
		path2.add(new Coordinate(5,1));
		path2.add(new Coordinate(5,2));
		path2.add(new Coordinate(5,3));
		
		ArrayList<Coordinate> path3 = new ArrayList<Coordinate>();
		path3.add(new Coordinate(11,0));
		path3.add(new Coordinate(10,0));
		path3.add(new Coordinate(9,0));
		path3.add(new Coordinate(8,0));
		path3.add(new Coordinate(7,0));
		path3.add(new Coordinate(6,0));
		path3.add(new Coordinate(6,1));
		path3.add(new Coordinate(6,2));
		path3.add(new Coordinate(6,3));
		
		Path path01 = new Path(path1,2);
		Path path02 = new Path(path2,2);
		Path path03 = new Path(path3,2);
		*/
	}
}