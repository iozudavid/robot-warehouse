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

public class Main2 {

	public static JobAssignment jobs;

	public static NXTInfo[] robots = { new NXTInfo(NXTCommFactory.BLUETOOTH, "NXT", "0016530C73B0"),
			new NXTInfo(NXTCommFactory.BLUETOOTH, "William", "00165308E546"),
			new NXTInfo(NXTCommFactory.BLUETOOTH, "Phil", "0016530A631F") };

	public static void main(String[] args) {
		// Infomation put into NXTInfo list which can be itterated through

		jobs = new JobAssignment(robots);
		Coordinate[] tempCoordinate = {null,null,null};

		// Set up server
		RobotServer rs = new RobotServer(robots);
		rs.connectToNxts();
		RunWarehouse.runWarehouseInterface();

		
		while (true) {
			LinkedHashMap<Coordinate, Coordinate> startToDestinations = new LinkedHashMap<Coordinate, Coordinate>();
			Coordinate[][] nxtCoordinates = {
					{ jobs.getRobotJobAssignment(robots[0].name).getCoordinate(),
							jobs.getRobotJobAssignment(robots[0].name).nextCoordinate() },
					{ jobs.getRobotJobAssignment(robots[1].name).getCoordinate(),
							jobs.getRobotJobAssignment(robots[1].name).nextCoordinate() },
					{ jobs.getRobotJobAssignment(robots[2].name).getCoordinate(),
							jobs.getRobotJobAssignment(robots[2].name).nextCoordinate() }

			};
			for (int i = 0; i < nxtCoordinates.length; i++) {
				System.out.println("Robot " + i);
				System.out.println(nxtCoordinates[i][0].toString());
				System.out.println(nxtCoordinates[i][1].toString());
				System.out.println();
			}
			System.out.println(" ");

			for(int i = 0;i<nxtCoordinates.length;i++){
				for(int j = 0;j<nxtCoordinates.length;j++){
					if (j != i){
						if (nxtCoordinates[i][1].isEqual(nxtCoordinates[j][1])){
							tempCoordinate[j] = nxtCoordinates[j][1];
							nxtCoordinates[j][1] = nxtCoordinates[j][0];
						}
					}
				}
			}
			
			
			
			startToDestinations.put(nxtCoordinates[0][0], nxtCoordinates[0][1]);
			startToDestinations.put(nxtCoordinates[1][0], nxtCoordinates[1][1]);
			startToDestinations.put(nxtCoordinates[2][0], nxtCoordinates[2][1]);

			HCAStar astar = new HCAStar(startToDestinations);
			ArrayList<Path> paths = astar.startFindingPaths();
			System.out.println(paths.size());
			for (Path p : paths) {
				System.out.println("NEW PATH");
				System.out.println("--------");
				for (Coordinate c : p.getList()) {
					System.out.println(c.toString());
				}
			}
			
			boolean[] finishedPaths = { false, false, false };
			int count = 0;
			int received = 0;
			while (true) {
				if (!paths.get(0).reachedEnd()) {
					Coordinate nxtCoordinate = paths.get(0).getNextCoord();
					rs.sendCoordinate(robots[0].name, nxtCoordinate);
					System.out.println(nxtCoordinate.toString()+" sent to robot");
					Window.addCoordinateRobot(nxtCoordinate, robots[0].name);
				} else if (!finishedPaths[0]){
					rs.sendCoordinate(robots[0].name,
							new Coordinate(-1, jobs.getRobotJobAssignment(robots[0].name).getNumOfItems()));
					finishedPaths[0] = true;
					received ++;
				}
				if (!paths.get(1).reachedEnd()) {
					Coordinate nxtCoordinate = paths.get(1).getNextCoord();
					rs.sendCoordinate(robots[1].name, nxtCoordinate);
					System.out.println(nxtCoordinate.toString()+" sent to robot");
					Window.addCoordinateRobot(nxtCoordinate, robots[1].name);
				} else if (!finishedPaths[1]){
					rs.sendCoordinate(robots[1].name,
							new Coordinate(-1, jobs.getRobotJobAssignment(robots[1].name).getNumOfItems()));
					finishedPaths[1] = true;
					received++;
				}
				if (!paths.get(2).reachedEnd()) {
					Coordinate nxtCoordinate = paths.get(2).getNextCoord();
					rs.sendCoordinate(robots[2].name, nxtCoordinate);
					System.out.println(nxtCoordinate.toString()+" sent to robot");
					Window.addCoordinateRobot(nxtCoordinate, robots[2].name);
				} else if (!finishedPaths[2]){
					rs.sendCoordinate(robots[2].name,
					new Coordinate(-1, jobs.getRobotJobAssignment(robots[2].name).getNumOfItems()));
					finishedPaths[2] = true;
					received++;
				}
				while (count != (3-received)) {
					Message receivedMsg = rs.getReceivedCoordinate();
					System.out.println(receivedMsg.getCoord().toString());
					count++;
				}
				if (finishedPaths[0] && finishedPaths[1] && finishedPaths[2]) {
					Arrays.fill(finishedPaths, false);
					break;
				}
				count = 0;
			}

		}
	}
}