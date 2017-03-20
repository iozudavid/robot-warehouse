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
		
	}
}
