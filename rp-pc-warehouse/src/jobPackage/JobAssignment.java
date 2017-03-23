package jobPackage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import Variables.StartCoordinate;
import lejos.pc.comm.NXTInfo;
import lejos.robotics.pathfinding.AstarSearchAlgorithm;
import mainLoop.Main;
import networking.RobotTable;
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
	static ConcurrentMap<String, SingleRobotJobAssignment> robotAssignments = new ConcurrentHashMap<String, SingleRobotJobAssignment>();
	public static ArrayList<Coordinate> dropOffs;
	protected static float totalReward = 0f;

	public JobAssignment(NXTInfo[] robots) {

		Reading.readItem();
		Reading.readJobs();
		Reading.readDropOff();

		dropOffs = Reading.returnDropOffs();
		jobs = Reading.returnJobs();
		jobs = SortJobs.sortByCancellation(jobs);
		jobs = SortJobs.sortByReward(jobs);

		for (NXTInfo nxt : robots) {
			robotAssignments.put(nxt.name, new SingleRobotJobAssignment(nxt.name));
		}
	}

	// for the test class
	public JobAssignment(ArrayList<Job> testJobs) {
		jobs = testJobs;
		jobs = SortJobs.sortByReward(jobs);

		RobotA = new SingleRobotJobAssignment("a");
		RobotB = new SingleRobotJobAssignment("b");
		RobotC = new SingleRobotJobAssignment("c");
	}

	public SingleRobotJobAssignment getRobotJobAssignment(String nxtName) {
		return robotAssignments.get(nxtName);
	}

	public static synchronized Job nextJob(Coordinate current) {
		if ((currentJobIndex) == jobs.size()) {
			currentJobIndex = 0;
		}
		jobs.get(currentJobIndex).setItems(TSsort(jobs.get(currentJobIndex), current));
		while (itemCheck()) {
			currentJobIndex++;
			if ((currentJobIndex + 1) == jobs.size()) {
				currentJobIndex = 0;
			}
		}
		Window.logMessage("Currently at job index " + (currentJobIndex + 1));
		return jobs.get(currentJobIndex++);
	}

	public static boolean itemCheck() {
		for (SingleRobotJobAssignment rb : robotAssignments.values()) {
			if (listItemsComparison(jobs.get(currentJobIndex).returnItems(), rb.items())) {
				return true;
			}
		}
		return false;
	}

	public static boolean listItemsComparison(List<Item> l1, List<Item> l2) {
		l1.size();
		if (l1.size() <= l2.size()) {
			for (int i = 0; i < l1.size(); i++) {
				if (l1.get(i).rName().equals(l2.get(i).rName())) {
					return true;
				}
			}
		} else {
			for (int i = 0; i < l2.size(); i++) {
				if (l1.get(i).rName().equals(l2.get(i).rName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public float returnTotalReward(){
		return totalReward;
	}

	public static Coordinate startPositionSelector(String name) {
		if (name.equals(Main.robots[0].name)) {
			return StartCoordinate.STARTCOORDINATEA;
		} else if (name.equals(Main.robots[1].name)) {
			return StartCoordinate.STARTCOORDINATEB;
		} else {
			return StartCoordinate.STARTCOORDINATEC;
		}
	}

	public ArrayList<String> getCompleatedJobs() {
		return compleatedJobs;
	}

	public static void addCompleatedJob(Job job) {
		compleatedJobs.add(job.returnN());
	}

	public static void removeCompleatedJob(Job job) {
		jobs.remove(job);
	}

	public static Coordinate findDropOff(Coordinate current) {
		Coordinate close = dropOffs.get(0);
		for (Coordinate drops : dropOffs) {
			if (lineDistanceTrig(drops, current) < lineDistanceTrig(close, current)) {
				close = drops;
			}
		}
		return close;
	}

	// runs through all the jobs getting the next closest job each time
	// This should only be run when the job is requested by the robot and NOT
	// for all the jobs otherwise it will take about 20 mins to sort all the
	// items
	public static List<Item> TSsort(Job job, Coordinate current1) {
		List<Item> items = job.returnItems();
		List<Item> newItems = new ArrayList<Item>();
		Coordinate current = current1;

		while (!items.isEmpty()) {
			Item temp = JobAssignment.getNextClosest(items, current);
			items.remove(temp);
			newItems.add(temp);
			current = temp.rCoordinate();
		}

		return newItems;
	}

	// this returns the next closet item in the list
	protected static Item getNextClosest(List<Item> itemList, Coordinate currentCoordinate) {
		Item closestItem = itemList.get(0);
		Double bestDistance = lineDistanceAStar(currentCoordinate, closestItem);
		for (Item each : itemList) {
			if (lineDistanceAStar(currentCoordinate, each) < bestDistance) {
				closestItem = each;
				bestDistance = lineDistanceAStar(currentCoordinate, each);
			}
		}
		return closestItem;
	}

	// returns the "as the crow flys" distance between the two items more
	// efficient but could yield inaccurate results as it will not
	// take into account walls
	private static double lineDistanceTrig(Coordinate item1, Coordinate item2) {
		return Math.sqrt(Math.pow((item2.getX() - item1.getX()), 2) + Math.pow((item2.getY() - item1.getY()), 2));
	}

	// uses the astar algorithm to get the distance between the two items
	// this is less efficient but will be much more reliable as it
	// will take into account any obstacles
	private static double lineDistanceAStar(Coordinate item1, Item item2) {
		SearchCell start = new SearchCell(item1);
		SearchCell goal = new SearchCell(item2.rCoordinate());
		PathFinding graph = new PathFinding(start, goal);
		return (double) graph.aStar().size();
	}
}
