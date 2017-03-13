package jobPackage;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

import warehouse.jobInput.Item;
import warehouse.jobInput.Job;

public class SortJobs {

	public static List<Job> sortByReward(List<Job> jobs) {
		jobs.sort(new Comparator<Job>() {

			@Override
			public int compare(Job j1, Job j2) {
				float val1 = 0;
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
					return 1;
				if (val1 > val2)
					return -1;
				return 0;
			}
		});
		try {
			PrintWriter out = new PrintWriter(new FileWriter("sortedJobs.txt"),true);
			for(Job j :jobs){
				out.println(j.returnN()+","+j.returnItems().size() + " "+ j.getReward());
			}

			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobs;
	}
}
