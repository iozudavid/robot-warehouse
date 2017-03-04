package warehouse.jobInput;

import java.util.Comparator;
import java.util.List;

public class SortJobs {

	public List<Job> sortByReward(List<Job> jobs) {
		jobs.sort(new Comparator<Job>() {

			@Override
			public int compare(Job j1, Job j2) {
				float val1 = 0;
				int num1 = 0;
				int num2 = 0;
				float val2 = 0;

				// calculate the total reward for a job
				for (Item item : j1.returnItems())
					val1 += item.rValue();
				for (Item item : j2.returnItems())
					val2 += item.rValue();

				// divide by the number of items, so that the jobs that
				// potentially take less time are preferred
				// if a job takes less time, it is less likely to be cancelled
				val1 /= j1.returnItems().size();
				val2 /= j2.returnItems().size();

				if (val1 < val2)
					return -1;
				if (val1 > val2)
					return 1;
				return 0;
			}
		});
		return jobs;
	}
}
