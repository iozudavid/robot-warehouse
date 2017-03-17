package jobPackage;

import java.util.ArrayList;
import java.util.List;

import Variables.StartCoordinate;
import warehouse.Coordinate;
import warehouse.jobInput.Item;
import warehouse.jobInput.Job;
import warehouseInterface.Window;

public class SingleRobotJobAssignment {

	private String name;
	private int itemIndex = 0;
	private Job job;
	private Item item;
	Coordinate dropOff = new Coordinate(3, 5);
	private Coordinate coord;
	private float weightSum = 0;
	private final float maxWeight = 50;
	private int numOfItems;
	public boolean cancelCurrentJob = false;
	
	public SingleRobotJobAssignment(String name){
		coord = JobAssignment.startPositionSelector(name);
		this.name = name;
		job = JobAssignment.nextJob();
		item = job.returnItems().get(itemIndex);
		job = TSsort(job);			
	}
	
	
	public synchronized Coordinate nextCoordinate() {
		if (isDropOff()) {
			Window.logMessage("job " + job.returnN() + " has been completed going to drop off");
			numOfItems = 0;
			System.out.println("dropOff");
			itemIndex = 0;
			JobAssignment.addCompleatedJob(job);
			job = JobAssignment.nextJob();
			job = TSsort(job);
			// after picking all of the items we go to the dropOff point,
			// normally the closest one, but here is a temporary one
			coord = dropOff;
			weightSum = 0;

		} else {

			item = job.returnItems().get(itemIndex);
			Float itemsWeight = item.rWeight() * getNumOfItems();
			weightSum += itemsWeight;
			
			Window.logMessage("On route to " + item.rName());
			Window.logMessage("current weight is " + weightSum);
			
			if (weightSum > maxWeight) {
				Window.logMessage("Robot is at weight limit");
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
	
	public String name(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public List<Item> items(){
		return job.returnItems();
	}

	// TODO not implemented yet
	// needs to have a message sent the robot when the cancelCurrentJob variable
	// is true
	// then the next path should be sent
	public void cancelJob() {
		job = JobAssignment.nextJob();
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
				Item temp = JobAssignment.getNextClosest(items, current);
				items.remove(temp);
				newItems.add(temp);
				current = temp.rCoordinate();
			}
			
			job.setItems(newItems);
			return job;
		}
	
}
