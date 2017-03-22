package jobPackage;

import java.util.List;

import warehouse.jobInput.Job;

public class CancellationTest {

	public static void main(String[] args) {
		
		int correct = 0;
		
		Reading.readItem();
		Reading.readJobs();
		SortJobs.readCancellations();
		SortJobs.createFeatures();
		
		for(Job j :SortJobs.test){
			if(SortJobs.predictCancellation(j) == SortJobs.cancelled.contains(j.returnN())){
				correct++;
			}
		}
		
		System.out.println(correct + " / " + SortJobs.test.size() +" = " + ((float)correct/SortJobs.test.size())*100 + "%");
	}

}
