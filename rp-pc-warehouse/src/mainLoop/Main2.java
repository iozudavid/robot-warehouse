package mainLoop;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import jobPackage.JobAssignment;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import networking.Message;
import networking.RobotServer;
import warehouse.Coordinate;
import warehouse.HCAStar;
import warehouse.Path;
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

			printStartAndEnd(nxtCoordinates);
			//The two following sections MAY result in deadlocks and there needs to be a check and 
			//resolve solution put in place
			
			//Resolves same start coordinates (maybe)
			if (nxtCoordinates[0][1].isEqual(nxtCoordinates[1][1])
					&& nxtCoordinates[1][1].isEqual(nxtCoordinates[2][1])) {
				jobs.getRobotJobAssignment(robots[0].name)
						.addItem(jobs.getRobotJobAssignment(robots[0].name).getNumOfItems());
				jobs.getRobotJobAssignment(robots[1].name)
						.addItem(jobs.getRobotJobAssignment(robots[1].name).getNumOfItems());
				nxtCoordinates[0][1] = nxtCoordinates[0][0];
				nxtCoordinates[0][1] = nxtCoordinates[0][0];
			} else if (nxtCoordinates[0][1].isEqual(nxtCoordinates[1][1])) {
				jobs.getRobotJobAssignment(robots[0].name)
						.addItem(jobs.getRobotJobAssignment(robots[0].name).getNumOfItems());
				nxtCoordinates[0][1] = nxtCoordinates[0][0];
			} else if (nxtCoordinates[1][1].isEqual(nxtCoordinates[2][1])) {
				jobs.getRobotJobAssignment(robots[1].name)
						.addItem(jobs.getRobotJobAssignment(robots[1].name).getNumOfItems());
				nxtCoordinates[1][1] = nxtCoordinates[1][0];
			} else if (nxtCoordinates[0][1].isEqual(nxtCoordinates[2][1])) {
				jobs.getRobotJobAssignment(robots[2].name)
						.addItem(jobs.getRobotJobAssignment(robots[2].name).getNumOfItems());
				nxtCoordinates[2][1] = nxtCoordinates[2][0];
			}

			//Resolves same start and end coordinates (maybe)
			for (int i = 0;i<nxtCoordinates.length;i++){
				for (int j = 0;j<nxtCoordinates.length;j++){
					if (i != j){
						if (nxtCoordinates[i][1].isEqual(nxtCoordinates[j][0])){
							jobs.getRobotJobAssignment(robots[i].name)
							.addItem(jobs.getRobotJobAssignment(robots[i].name).getNumOfItems());
							nxtCoordinates[i][1] = nxtCoordinates[i][0];
						}
					}
				}
			}
			
			startToDestinations.put(nxtCoordinates[0][0], nxtCoordinates[0][1]);
			startToDestinations.put(nxtCoordinates[1][0], nxtCoordinates[1][1]);
			startToDestinations.put(nxtCoordinates[2][0], nxtCoordinates[2][1]);

			HCAStar astar = new HCAStar(startToDestinations);
			ArrayList<Path> paths = astar.startFindingPaths();

			printPaths(paths);

			boolean[] finishedPaths = { false, false, false };
			int count = 0;
			int received = 0;
			while (true) {
				/* Might be worth using the following loop to help improve code understanding (as it's minimal at the moment)
				for (int i = 0;i<robots.length;i++){
					if (!paths.get(i).reachedEnd()) {
						Coordinate nxtCoordinate = paths.get(i).getNextCoord();
						rs.sendCoordinate(robots[i].name, nxtCoordinate);
						System.out.println(nxtCoordinate.toString() + " sent to robot");
						Window.addCoordinateRobot(nxtCoordinate, robots[i].name);
					} else if (!finishedPaths[i]) {
						if (paths.get(i).getIsWait()) {
							rs.sendCoordinate(robots[i].name, new Coordinate(-5, -5));
						} else {
							rs.sendCoordinate(robots[i].name,
									new Coordinate(-1, jobs.getRobotJobAssignment(robots[0].name).getNumOfItems()));
						}
						finishedPaths[0] = true;
						received++;
					}
				}*/
				if (!paths.get(0).reachedEnd()) {
					Coordinate nxtCoordinate = paths.get(0).getNextCoord();
					rs.sendCoordinate(robots[0].name, nxtCoordinate);
					System.out.println(nxtCoordinate.toString() + " sent to robot");
					Window.addCoordinateRobot(nxtCoordinate, robots[0].name);
				} else if (!finishedPaths[0]) {
					if (paths.get(0).getIsWait()) {
						rs.sendCoordinate(robots[0].name, new Coordinate(-5, -5));
					} else {
						rs.sendCoordinate(robots[0].name,
								new Coordinate(-1, jobs.getRobotJobAssignment(robots[0].name).getNumOfItems()));
					}
					finishedPaths[0] = true;
					received++;
				}
				if (!paths.get(1).reachedEnd()) {
					Coordinate nxtCoordinate = paths.get(1).getNextCoord();
					rs.sendCoordinate(robots[1].name, nxtCoordinate);
					System.out.println(nxtCoordinate.toString() + " sent to robot");
					Window.addCoordinateRobot(nxtCoordinate, robots[1].name);
				} else if (!finishedPaths[1]) {
					if (paths.get(1).getIsWait()) {
						rs.sendCoordinate(robots[1].name, new Coordinate(-5, -5));
					} else {
						rs.sendCoordinate(robots[1].name,
								new Coordinate(-1, jobs.getRobotJobAssignment(robots[1].name).getNumOfItems()));
					}
					finishedPaths[1] = true;
					received++;
				}
				if (!paths.get(2).reachedEnd()) {
					Coordinate nxtCoordinate = paths.get(2).getNextCoord();
					rs.sendCoordinate(robots[2].name, nxtCoordinate);
					System.out.println(nxtCoordinate.toString() + " sent to robot");
					Window.addCoordinateRobot(nxtCoordinate, robots[2].name);
				} else if (!finishedPaths[2]) {
					if (paths.get(2).getIsWait()) {
						rs.sendCoordinate(robots[2].name, new Coordinate(-5, -5));
					} else {
						rs.sendCoordinate(robots[2].name,
								new Coordinate(-1, jobs.getRobotJobAssignment(robots[2].name).getNumOfItems()));
					}
					finishedPaths[2] = true;
					received++;
				}
				while (count != (3 - received)) {
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

	private static void printPaths(ArrayList<Path> paths) {
		for (Path p : paths) {
			System.out.println("NEW PATH");
			System.out.println("--------");
			for (Coordinate c : p.getList()) {
				System.out.println(c.toString());
			}
		}
	}

	private static void printStartAndEnd(Coordinate[][] nxtCoordinates) {
		for (int i = 0; i < nxtCoordinates.length; i++) {
			System.out.println("Robot " + i);
			System.out.println(nxtCoordinates[i][0].toString());
			System.out.println(nxtCoordinates[i][1].toString());
			System.out.println();
		}
		System.out.println(" ");
	}
}