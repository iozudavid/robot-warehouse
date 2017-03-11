package mainLoop;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.print.attribute.standard.Finishings;

import jobPackage.JobAssignment;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import networking.Message;
import networking.RobotServer;
import warehouse.Coordinate;
import warehouse.Path;
import warehouse.PathFinding;
import warehouse.SearchCell;
import warehouseInterface.RunWarehouse;
import warehouseInterface.Window;

public class Main {
	
	public static JobAssignment jobs;

	public static void main(String[] args) {

		jobs = new JobAssignment();
		String robotName = "NXT";
		String robotAddress = "0016530C73B0";

		// Infomation put into NXTInfo list which can be itterated through
		NXTInfo[] robots = { new NXTInfo(NXTCommFactory.BLUETOOTH, robotName, robotAddress) };

		// Set up server
		RobotServer rs = new RobotServer(robots);
		rs.connectToNxts();
		RunWarehouse.runWarehouseInterface();

		Coordinate startCoord = jobs.getCoordinate();
		Coordinate finishCoord = jobs.nextCoordinate();

		System.out.println(startCoord.getX()+" "+startCoord.getY() + "start");
		System.out.println(finishCoord.getX()+" "+finishCoord.getY() + "finish");

		// A* search on test coordinates
		SearchCell start = new SearchCell(startCoord);
		SearchCell goal = new SearchCell(finishCoord);
		PathFinding graph = new PathFinding(start, goal);
		ArrayList<Coordinate> list = graph.aStar();
		Path c = new Path(list, jobs.getNumOfItems());
		rs.sendPath(robotName, c);
		// Window.addCoordinateRobotA(c);
		while (true) {
			while (!rs.isCoordinateEmpty(robotName)) {
				Message receivedMsg = rs.getReceivedCoordinate();
				Window.addCoordinateRobotA(receivedMsg.getCoord());
			}
			while (!rs.isReceivedEmpty(robotName)) {
				Message recivedMessage = rs.getReceivedMessage();
				if (recivedMessage.getMsg().equals("ITEMPICKUP")) {
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
					rs.sendPath(robotName, c);
				}
			}
		}
	}
}
