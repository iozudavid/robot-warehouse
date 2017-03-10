package jobPackage;


import java.util.List;

import warehouse.Coordinate;
import warehouse.jobInput.Item;
import warehouse.jobInput.Job;

public class JobAssignment {

	static List<Job> jobs;
	private int job;
	private int item;
	// temporary
	//arvydas you should also read this stuff from the file
	Coordinate dropOff = new Coordinate(3, 3);
	private Coordinate coord = new Coordinate(0, 0);
	private float weightSum = 0;
	private final float maxWeight = 50;

	public JobAssignment() {

		// getting data from files
		Reading.readItem();
		Reading.readJobs();
		jobs = SortJobs.sortByReward(Reading.returnJobs());
		job = 0;
		item = 0;
	}

	public Coordinate nextCoordinate() {
		Item i = jobs.get(job).returnItems().get(item++);
		weightSum += i.rWeight();
		
		if (isDropOff()) {
			item = 0;
			job++;
			// after picking all of the items we go to the dropOff point,
			// normally the closest one, but here is a temporary one
			coord = dropOff;
			weightSum = 0;
		}
		else if (weightSum > maxWeight){
			coord = dropOff;
			weightSum = 0;
			item--;
		}
			else
			coord = i.rCoordinate();
		return coord;
	}

	public Coordinate getCoordinate() {
		return coord;
	}

	public boolean startPosition() {
		return item == 0;
	}

	public boolean isDropOff() {
		return item == jobs.get(job).returnItems().size();
	}

	public String getJobName() {
		return jobs.get(job).returnN();
	}

	public int getNumOfItems() {
		String itemName = jobs.get(job).returnItems().get(item - 1).rName();
		return jobs.get(job).returnNmbr(itemName);
	}

	public float getReward() {
		Item i = jobs.get(job).returnItems().get(item - 1);
		return i.rValue();
	}

}
