package mainLoop;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.print.attribute.standard.Finishings;

import Variables.Messages;
import jobPackage.JobAssignment;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import networking.Message;
import networking.RobotServer;
import warehouse.Coordinate;
import warehouse.HCAStar;
import warehouse.Path;
import warehouse.PathFinding;
import warehouse.SearchCell;
import warehouse.jobInput.Job;
import warehouseInterface.RunWarehouse;
import warehouseInterface.Window;

public class Main {

	public static JobAssignment jobs;

	public static NXTInfo[] robots = { new NXTInfo(NXTCommFactory.BLUETOOTH, "NXT", "0016530C73B0"),
			new NXTInfo(NXTCommFactory.BLUETOOTH, "William", "00165308E546"),
			new NXTInfo(NXTCommFactory.BLUETOOTH, "Phil", "0016530A631F") };
	
	public static void main(String[] args) {
		// Infomation put into NXTInfo list which can be itterated through

		jobs = new JobAssignment(robots);
		
		// Set up server
		RobotServer rs = new RobotServer(robots);
		rs.connectToNxts();
		RunWarehouse.runWarehouseInterface();

		LinkedHashMap<Coordinate,Coordinate> startToDestinations = new LinkedHashMap<Coordinate,Coordinate>();
		startToDestinations.put(jobs.getRobotJobAssignment(robots[0].name).getCoordinate(), jobs.getRobotJobAssignment(robots[0].name).nextCoordinate());
		startToDestinations.put(jobs.getRobotJobAssignment(robots[1].name).getCoordinate(), jobs.getRobotJobAssignment(robots[1].name).nextCoordinate());
		startToDestinations.put(jobs.getRobotJobAssignment(robots[2].name).getCoordinate(), jobs.getRobotJobAssignment(robots[2].name).nextCoordinate());
		
		HCAStar astar = new HCAStar(startToDestinations);
		ArrayList<Path> paths = astar.startFindingPaths();
		for(int i = 0;i<robots.length;i++){
			paths.get(i).setNumberOfItems(jobs.getRobotJobAssignment(robots[i].name).getNumOfItems());
			rs.sendPath(robots[i].name, paths.get(i));
		}
		boolean[] finished = {false,false,false};
		while (true) {
			while (!rs.isCoordinateEmpty()) {
				Message receivedMsg = rs.getReceivedCoordinate();
				Window.addCoordinateRobot(receivedMsg.getCoord(),receivedMsg.getSender());
			}
			while (!rs.isReceivedEmpty()) {
				Message receivedMessage = rs.getReceivedMessage();
				for(int i = 0;i<robots.length;i++){
					if(receivedMessage.getSender().equals(robots[i].name) && receivedMessage.getMsg().equals("ITEMPICKUP")){
						finished[i] = true;
					}
				}
				
				if (finished[0] && finished[1] && finished[2]) {
					startToDestinations = new LinkedHashMap<Coordinate,Coordinate>();
					startToDestinations.put(jobs.getRobotJobAssignment(robots[0].name).getCoordinate(), jobs.getRobotJobAssignment(robots[0].name).nextCoordinate());
					startToDestinations.put(jobs.getRobotJobAssignment(robots[1].name).getCoordinate(), jobs.getRobotJobAssignment(robots[1].name).nextCoordinate());
					startToDestinations.put(jobs.getRobotJobAssignment(robots[2].name).getCoordinate(), jobs.getRobotJobAssignment(robots[2].name).nextCoordinate());
					
					astar = new HCAStar(startToDestinations);
					paths = astar.startFindingPaths();
					for(int i = 0;i<robots.length;i++){
						paths.get(i).setNumberOfItems(jobs.getRobotJobAssignment(robots[i].name).getNumOfItems());
						rs.sendPath(robots[i].name, paths.get(i));
					}
				}
			}
		}
		
//		ArrayList<RobotLoop> robotLoops = new ArrayList<RobotLoop>();
		
//		robotLoops.add(new RobotLoop(rs, robots[0].name, 0, jobs.getRobotJobAssignment( robots[0].name)));
//		robotLoops.get(robotLoops.size() - 1).start();
//
//		robotLoops.add(new RobotLoop(rs, robots[1].name, 1, jobs.getRobotJobAssignment( robots[1].name)));
//		robotLoops.get(robotLoops.size() - 1).start();
//
//		robotLoops.add(new RobotLoop(rs, robots[2].name, 2, jobs.getRobotJobAssignment( robots[2].name)));
//		robotLoops.get(robotLoops.size() - 1).start();
//
//		try {
//			robotLoops.get(0).join();
//			robotLoops.get(1).join();
//			robotLoops.get(2).join();
//
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
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