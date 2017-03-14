package jobPackage;

import java.util.List;

import Variables.StartCoordinate;
import warehouse.Coordinate;
import warehouse.jobInput.Item;
import warehouse.jobInput.Job;

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

}
