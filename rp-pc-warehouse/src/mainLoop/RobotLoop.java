package mainLoop;

import java.util.ArrayList;

import Variables.Messages;
import Variables.StartCoordinate;
import jobPackage.SingleRobotJobAssignment;
import networking.RobotServer;
import warehouse.Coordinate;
import warehouse.Path;
import warehouse.PathFinding;
import warehouse.SearchCell;
import warehouseInterface.Window;

public class RobotLoop extends Thread {

	private RobotServer server;
	private String robotName;
	private SingleRobotJobAssignment jobs;
	private int robotID;

	public RobotLoop(RobotServer rs, String name,int robotID,SingleRobotJobAssignment robotA) {
		this.server = rs;
		this.robotName = name;
		this.jobs = robotA;
		this.robotID = robotID;
	}

	public void run(){
		Coordinate startCoord = jobs.getCoordinate();
		Coordinate finishCoord = jobs.nextCoordinate();
		// A* search on test coordinates
		SearchCell start = new SearchCell(startCoord);
		SearchCell goal = new SearchCell(finishCoord);
		PathFinding graph = new PathFinding(start, goal);
		ArrayList<Coordinate> list = graph.aStar();
		Path c = new Path(list, jobs.getNumOfItems());
		server.sendPath(robotName, c);
		// Window.addCoordinateRobotA(c);
		
		while (true) {
			while (!server.isCoordinateEmpty(robotName)) {
				Coordinate receivedMsg = server.getReceivedCoordinate(robotName);
				Window.addCoordinateRobotA(receivedMsg);
			}
			while (!server.isReceivedEmpty(robotName)) {
				String recivedMessage = server.getReceivedMessage(robotName);
				if (recivedMessage.equals(Messages.GOTITEM)) {
					startCoord = jobs.getCoordinate();
					finishCoord = jobs.nextCoordinate();
					System.out.println(startCoord.getX()+" "+startCoord.getY() + "start");
					System.out.println(finishCoord.getX()+" "+finishCoord.getY() + "finish");
					// A* search on test coordinates
					start = new SearchCell(startCoord);
					goal = new SearchCell(finishCoord);
					graph = new PathFinding(start, goal);
					list = graph.aStar();
					c = new Path(list, jobs.getNumOfItems());
					server.sendPath(robotName, c);
				}
			}
		}
	}
}
