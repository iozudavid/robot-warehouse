package jobPackage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import warehouse.Coordinate;
import warehouse.jobInput.Item;
import warehouse.jobInput.Job;
import warehouseInterface.Window;

public class SingleRobotJobAssignment {

	private String name;
	private int itemIndex = 0;
	private Job job;
	private Item item;
	private Coordinate coord;
	private float weightSum = 0;
	private final float maxWeight = 50;
	private int numOfItems;
	public boolean cancelCurrentJob = false;

	public SingleRobotJobAssignment(String name) {
		coord = JobAssignment.startPositionSelector(name);
		this.name = name;
		job = JobAssignment.nextJob(getCoordinate());
		item = job.returnItems().get(itemIndex);
	}

	public synchronized Coordinate nextCoordinate() {
		if (isDropOff()) {
			Window.logMessage("job " + job.returnN() + " has been completed");
			Window.logMessage(name + " going to drop off");
			numOfItems = 0;
			itemIndex = 0;
			JobAssignment.addCompleatedJob(job);
			JobAssignment.removeCompleatedJob(job);
			job = JobAssignment.nextJob(getCoordinate());
			coord = JobAssignment.findDropOff(getCoordinate());
			weightSum = 0;

		} else {
			item = job.returnItems().get(itemIndex);
			Float itemsWeight = item.rWeight() * getNumOfItems();
			weightSum += itemsWeight;

			Window.logMessage(name + " on route to " + item.rName());

			if (weightSum > maxWeight) {
				Window.logMessage("Robot is at weight limit");
				if (itemsWeight < maxWeight) {
					coord = JobAssignment.findDropOff(getCoordinate());
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
				Window.logMessage("current weight of " + name + " is " + weightSum);
				numOfItems = job.returnNmbr(item.rName());
				job.removeItem(item);
			}
			coord = item.rCoordinate();
		}
		return coord;

	}
	
	public void addItem(Item item, int num){
		List<Item> jobItems = job.returnItems();
		List<Item> temp = new ArrayList<Item>();
		temp.add(item);
		for (int i = 0; i<jobItems.size();i++){
			temp.add(jobItems.get(i));
		}
		job.addItem(item, num);
	}

	public Coordinate getCoordinate() {
		return coord;
	}

	public boolean isDropOff() {
		return job.returnItems().isEmpty();
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
	
	public float getWeight() {
		return item.rWeight();
	}


	public String name() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Item> items() {
		return job.returnItems();
	}

	// TODO not implemented yet
	// needs to have a message sent the robot when the cancelCurrentJob variable
	// is true
	// then the next path should be sent
	public void cancelJob() {
		job = JobAssignment.nextJob(getCoordinate());
		itemIndex = 0;
		cancelCurrentJob = true;
	}
	
	public static <T> boolean listEqualsNoOrder(List<T> l1, List<T> l2) {
	    final Set<T> s1 = new HashSet<>(l1);
	    final Set<T> s2 = new HashSet<>(l2);

	    return s1.equals(s2);
	}
}
