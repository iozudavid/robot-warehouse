package mainLoop;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import javax.print.attribute.standard.Finishings;

import Variables.Messages;
import jobPackage.JobAssignment;
import lejos.nxt.remote.NXTCommRequest;
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
		Coordinate[][] nxtCoordinates = {{jobs.getRobotJobAssignment(robots[0].name).getCoordinate(), jobs.getRobotJobAssignment(robots[0].name).nextCoordinate()},
				{jobs.getRobotJobAssignment(robots[1].name).getCoordinate(), jobs.getRobotJobAssignment(robots[1].name).nextCoordinate()},
				{jobs.getRobotJobAssignment(robots[2].name).getCoordinate(), jobs.getRobotJobAssignment(robots[2].name).nextCoordinate()}
				
		};
		for(int i = 0;i<nxtCoordinates.length;i++){
			System.out.println("Robot "+i);
			System.out.println(nxtCoordinates[i][0].toString());
			System.out.println(nxtCoordinates[i][1].toString());
			System.out.println();
		}
		System.out.println(" ");
		
		startToDestinations.put(nxtCoordinates[0][0],nxtCoordinates[0][1]);
		startToDestinations.put(nxtCoordinates[1][0],nxtCoordinates[1][1]);
		startToDestinations.put(nxtCoordinates[2][0],nxtCoordinates[2][1]);
			
		HCAStar astar = new HCAStar(startToDestinations);
		ArrayList<Path> paths = astar.startFindingPaths();
		System.out.println(paths.size());
		for(Path p:paths){
			System.out.println("NEW PATH");
			System.out.println("--------");
			for(Coordinate c: p.getList()){
				System.out.println(c.toString());
			}
		}
		for(int i = 0;i<robots.length;i++){
			paths.get(i).setNumberOfItems(jobs.getRobotJobAssignment(robots[i].name).getNumOfItems());
			System.out.println("The number of items is: "+paths.get(i).getNumberOFItems());
			rs.sendPath(robots[i].name, paths.get(i));
		}
		
		boolean[] finished = {false,false,false};
		
		while (true) {
			while (!rs.isCoordinateEmpty()) {
				Message receivedMsg = rs.getReceivedCoordinate();
				Window.addCoordinateRobot(receivedMsg.getCoord(),receivedMsg.getSender());
				System.out.println("Robot: "+receivedMsg.getSender()+" coordinate: "+receivedMsg.getCoord().toString());
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
					Coordinate[][] nxtCoordinates2 = {{jobs.getRobotJobAssignment(robots[0].name).getCoordinate(), jobs.getRobotJobAssignment(robots[0].name).nextCoordinate()},
							{jobs.getRobotJobAssignment(robots[1].name).getCoordinate(), jobs.getRobotJobAssignment(robots[1].name).nextCoordinate()},
							{jobs.getRobotJobAssignment(robots[2].name).getCoordinate(), jobs.getRobotJobAssignment(robots[2].name).nextCoordinate()}
							
					};
					
					for(int i = 0;i<nxtCoordinates.length;i++){
						System.out.println("Robot "+i);
						System.out.println(nxtCoordinates2[i][0].toString());
						System.out.println(nxtCoordinates2[i][1].toString());
						System.out.println();
					}
					System.out.println(" ");
					
					startToDestinations.put(nxtCoordinates2[0][0],nxtCoordinates2[0][1]);
					startToDestinations.put(nxtCoordinates2[1][0],nxtCoordinates2[1][1]);
					startToDestinations.put(nxtCoordinates2[2][0],nxtCoordinates2[2][1]);
					
					astar = new HCAStar(startToDestinations);
					paths = astar.startFindingPaths();
					System.out.println(paths.size());
					for(Path p:paths){
						System.out.println("NEW PATH");
						System.out.println("--------");
						for(Coordinate c: p.getList()){
							System.out.println(c.toString());
						}
					}
					for(int i = 0;i<robots.length;i++){
						paths.get(i).setNumberOfItems(jobs.getRobotJobAssignment(robots[i].name).getNumOfItems());
						rs.sendPath(robots[i].name, paths.get(i));
					}
					
					Arrays.fill(finished, false); 
				}
			}
		}
		
	}
}