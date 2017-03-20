package jobPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import Variables.StartCoordinate;
import lejos.pc.comm.NXTInfo;
import lejos.robotics.pathfinding.AstarSearchAlgorithm;
import mainLoop.Main;
import warehouse.Coordinate;
import warehouse.PathFinding;
import warehouse.SearchCell;
import warehouse.jobInput.Item;
import warehouse.jobInput.Job;
import warehouseInterface.RunWarehouse;
import warehouseInterface.Window;

public class JobAssignment {

	protected static List<Job> jobs;
	protected static int currentJobIndex = 0;
	protected static ArrayList<String> compleatedJobs = new ArrayList<String>();
	protected SingleRobotJobAssignment RobotA;
	protected SingleRobotJobAssignment RobotB;
	protected SingleRobotJobAssignment RobotC;
	ConcurrentMap<String,SingleRobotJobAssignment> robotAssignments = new ConcurrentHashMap<String,SingleRobotJobAssignment>();
	public static ArrayList<Coordinate> dropOffs;

	public JobAssignment(NXTInfo[] robots) {

		Reading.readItem();
		Reading.readJobs();
		Reading.readDropOff();
		
		dropOffs = Reading.returnDropOffs();
		jobs = Reading.returnJobs();
		//jobs = SortJobs.sortByReward(jobs);
		
		for (NXTInfo nxt: robots){
			robotAssignments.put(nxt.name, new SingleRobotJobAssignment(nxt.name));
		}
	}
	
	//for the test class
	public JobAssignment(ArrayList<Job> testJobs){
		jobs = testJobs;
		jobs = SortJobs.sortByReward(jobs);
		
		RobotA = new SingleRobotJobAssignment("a");
		RobotB = new SingleRobotJobAssignment("b");
		RobotC = new SingleRobotJobAssignment("c");
	}
	
	public SingleRobotJobAssignment getRobotJobAssignment(String nxtName){
		return robotAssignments.get(nxtName);
	}
		
	public static synchronized Job nextJob(){
		Window.logMessage("Currently at job index " + (currentJobIndex+1));
		return jobs.get(currentJobIndex++);
	}
	
	public static Coordinate startPositionSelector(String name){
		if (name.equals(Main.robots[0].name)){
			return StartCoordinate.STARTCOORDINATEA;
		} else if (name.equals(Main.robots[1].name)){
			return StartCoordinate.STARTCOORDINATEB;
		} else {
			return StartCoordinate.STARTCOORDINATEC;
		}
	}
	
	public ArrayList<String> getCompleatedJobs(){
		return compleatedJobs;
	}
		
	public static void addCompleatedJob(Job job){
		compleatedJobs.add(job.returnN());
	}
	
	public static Coordinate findDropOff(Coordinate current){
		Coordinate close = dropOffs.get(0);
		for (Coordinate drops : dropOffs){
			if(lineDistanceTrig(drops, current) < lineDistanceTrig(close, current)){
				close = drops;
			}
		}
		return close;
	}
	
	//this returns the next closet item in the list
	protected static Item getNextClosest(List<Item> itemList, Coordinate currentCoordinate){
		Item closestItem = itemList.get(0);
		Double bestDistance = lineDistanceAStar(currentCoordinate, closestItem);
		for (Item each : itemList){
			if (lineDistanceAStar(currentCoordinate, each) < bestDistance){
				closestItem = each;
				bestDistance = lineDistanceAStar(currentCoordinate, each);
			}
		}
		return closestItem;
	}
	
	//returns the "as the crow flys" distance between the two items more 
	//efficient but could yield inaccurate results as it will not
	//take into account walls
	private static double lineDistanceTrig(Coordinate item1, Coordinate item2){
		return Math.sqrt(Math.pow((item2.getX() - item1.getX()), 2) + Math.pow((item2.getY() - item1.getY()), 2));
	}
	
	//uses the astar algorithm to get the distance between the two items
	//this is less efficient but will be much more reliable as it
	//will take into account any obstacles
	private static double lineDistanceAStar(Coordinate item1, Item item2){
		SearchCell start = new SearchCell(item1);
		SearchCell goal = new SearchCell(item2.rCoordinate());
		PathFinding graph = new PathFinding(start, goal);
		return (double) graph.aStar().size();
	}
}
