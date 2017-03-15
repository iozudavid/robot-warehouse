package jobPackage;

import java.util.ArrayList;
import java.util.List;

import Variables.StartCoordinate;
import lejos.robotics.pathfinding.AstarSearchAlgorithm;
import warehouse.Coordinate;
import warehouse.PathFinding;
import warehouse.SearchCell;
import warehouse.jobInput.Item;
import warehouse.jobInput.Job;
import warehouseInterface.RunWarehouse;
import warehouseInterface.Window;

public class JobAssignment {

	static List<Job> jobs;
	private int jobIndex;
	private int itemIndex;
	private Job job;
	private Item item;
	Coordinate dropOff = new Coordinate(3, 5);
	private Coordinate coord = StartCoordinate.STARTCOORDINATE;
	private float weightSum = 0;
	private final float maxWeight = 50;
	private int numOfItems;
	public boolean cancelCurrentJob = false;

	public JobAssignment() {

		Reading.readItem();
		Reading.readJobs();
		jobs = Reading.returnJobs();
		jobIndex = 0;
		itemIndex = 0;
		job = jobs.get(jobIndex);
		item = job.returnItems().get(itemIndex);

	}
	
	public static void main(String[] args){
		JobAssignment TSTestClass = new JobAssignment();
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(2, 4), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(3, 3), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(5, 6), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job4"));
		testJob.setItems(itemList);
		
		System.out.println("first: " + itemList.toString());
		testJob = TSTestClass.TSsort(testJob);
		System.out.println("second: " + testJob.returnItems().toString());
	}

	public synchronized Coordinate nextCoordinate() {
		job = jobs.get(jobIndex);

		if (isDropOff()) {

			numOfItems = 0;
			System.out.println("dropOff");
			itemIndex = 0;
			jobIndex++;
			// after picking all of the items we go to the dropOff point,
			// normally the closest one, but here is a temporary one
			coord = dropOff;
			weightSum = 0;

		} else {

			item = job.returnItems().get(itemIndex);
			Float itemsWeight = item.rWeight() * getNumOfItems();
			weightSum += itemsWeight;
			
			if (weightSum > maxWeight) {

				if (itemsWeight < maxWeight) {
					coord = dropOff;
				} else {

					float w = weightSum - itemsWeight;
					int num = (int) (w / item.rWeight());
					int initialNum = job.returnNmbr(item.rName());
					int newNum = initialNum - num;
					numOfItems = num;
					job.setNumOfItems(item.rName(), newNum);

				}
				weightSum = 0;

			} else {

				itemIndex++;
				numOfItems = job.returnNmbr(item.rName());
			}
			coord = item.rCoordinate();
		}
		return coord;

	}

	public Coordinate getCoordinate() {
		return coord;
	}

	public boolean isDropOff() {
		return itemIndex == job.returnItems().size();
	}

	public String getJobName() {
		return job.returnN();
	}

	public int getNumOfItems() {
		return numOfItems;
	}

	public float getReward() {
		return item.rValue();
	}

	// TODO not implemented yet
	// needs to have a message sent the robot when the cancelCurrentJob variable
	// is true
	// then the next path should be sent
	public void cancelJob() {
		jobIndex++;
		itemIndex = 0;
		cancelCurrentJob = true;
	}
	
	//runs through all the jobs getting the next closest job each time
	//This should only be run when the job is requested by the robot and NOT
	//for all the jobs otherwise it will take about 20 mins to sort all the items
	public Job TSsort(Job job){
		List<Item> items = job.returnItems();
		List<Item> newItems = new ArrayList<Item>();
		Coordinate current = getCoordinate();
		
		while(!items.isEmpty()){
			Item temp = getNextClosest(items, current);
			items.remove(temp);
			newItems.add(temp);
			current = temp.rCoordinate();
		}
		
		job.setItems(newItems);
		return job;
	}

	//this returns the next closet item in the list
	private Item getNextClosest(List<Item> itemList, Coordinate currentCoordinate){
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
	private double lineDistanceTrig(Coordinate item1, Item item2){
		return Math.sqrt(Math.pow((item2.rCoordinate().getX() - item1.getY()), 2) + Math.pow((item2.rCoordinate().getY() - item1.getY()), 2));
	}
	
	//uses the astar algorithm to get the distance between the two items
	//this is less efficient but will be much more reliable as it
	//will take into account any obstacles
	private double lineDistanceAStar(Coordinate item1, Item item2){
		SearchCell start = new SearchCell(item1);
		SearchCell goal = new SearchCell(item2.rCoordinate());
		PathFinding graph = new PathFinding(start, goal);
		return (double) graph.aStar().size();
	}
}
