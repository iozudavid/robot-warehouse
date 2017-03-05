package warehouse.jobInput;

import java.util.List;

import warehouse.Coordinate;

public class JobAssignment {

	static List<Job> jobs;
	private int job;
	private int item;
	// temporary
	Coordinate dropOff = new Coordinate(3, 3);

	public JobAssignment() {
		jobs = SortJobs.sortByReward(Reading.returnJobs());
		job = 0;
		item = 0;
	}

	public Coordinate getCoordinate() {
		if (dropOff()) {
			item = 0;
			job++;
			// after picking all of the items we go to the dropOff point,
			// normally the closest one, but here is a temporary one
			return dropOff;
		}
		return jobs.get(job).returnItems().get(item++).rCoordinate();
	}

	public boolean startPosition() {
		return item == 0;
	}

	public boolean dropOff() {
		return item == jobs.get(job).returnItems().size();
	}

	// public static void main(String[] args) {

	// read from files
	// Reading.readItem();
	// Reading.readJobs();
	// jobs = SortJobs.sortByReward(Reading.returnJobs());

	// send coordinates
	// wait
	// send some more

	// }

}