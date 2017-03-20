package jobPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Variables.InputFiles;
import warehouse.jobInput.Item;
import warehouse.jobInput.Job;

public class SortJobs {

	static List<String> cancelled = new ArrayList<>();
	private static ArrayList<ArrayList<Integer>> cancelledJobs = new ArrayList<>();
	private static ArrayList<ArrayList<Integer>> notCancelledJobs = new ArrayList<>();
	static ArrayList<ArrayList<Integer>> testCancelledJobs = new ArrayList<>();
	static ArrayList<ArrayList<Integer>> testNotCancelledJobs = new ArrayList<>();
	static ArrayList<Job> test = new ArrayList<>();
	private static int rewardRange = 5;
	private static int weightRange = 10;
	private static int numRange = 1;

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
			PrintWriter out = new PrintWriter(new FileWriter("sortedJobs.txt"), true);
			for (Job j : jobs) {
				out.println(j.returnN() + "," + j.returnItems().size() + " " + j.getReward());
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jobs;
	}

	public static void readCancellations() {

		String line = "";
		BufferedReader inputFile = null;
		try {
			inputFile = new BufferedReader(new FileReader(InputFiles.CANCELLATIONSFILE));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			int i = 0;
			while ((line = inputFile.readLine()) != null) {
				if (line.contains(",1")) {
					i++;
					// System.out.println(line.split(",")[0]);
					cancelled.add(line.split(",")[0]);
				}
			}
			// System.out.println("number of cancelled jobs: "+i);
			inputFile.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static List<Job> sortByCancellation(List<Job> jobs) {

		readCancellations();
		createFeatures();
		List<Job> finalJobs = new ArrayList<>();
		for (Job j : jobs) {
			// if job won't be cancelled we add it to the list
			if (!predictCancellation(j))
				finalJobs.add(j);
		}
		return finalJobs;
	}

	static boolean predictCancellation(Job j) {
		Float yes = 0f;
		Float no = 0f;
		int num = j.getTotalNumOfItems() / numRange;
		int reward = (int) (j.getReward() / rewardRange);
		int weight = j.getTotalWeight() / weightRange;
		int numYes = 0;
		int numNo = 0;
		int rewardYes = 0;
		int rewardNo = 0;
		int weightYes = 0;
		int weightNo = 0;

		for (ArrayList<Integer> a : cancelledJobs) {
			// finding the probability of the first feature
			if (a.get(0).equals(num))
				numYes++;

			// finding the probability of the second feature
			if (a.get(1).equals(reward))
				rewardYes++;

			// finding the probability of the third feature
			if (a.get(2).equals(weight))
				weightYes++;
		}
		// no values of zero
		if (numYes == 0)
			numYes = 2;
		if (rewardYes == 0)
			rewardYes = 2;
		if (weightYes == 0)
			weightYes = 2;

		yes = (float) (0.5 * (numYes / cancelledJobs.size()) * (rewardYes / cancelledJobs.size()) * (weightYes)
				/ cancelledJobs.size());

		// System.out.println("yes " + yes);
		for (ArrayList<Integer> a : notCancelledJobs) {
			// finding the probability of the first feature
			if (a.get(0).equals(num))
				numNo++;

			// finding the probability of the second feature
			if (a.get(1).equals(reward))
				rewardNo++;

			// finding the probability of the third feature
			if (a.get(2).equals(weight))
				weightNo++;
		}

		if (numNo == 0)
			numNo = 2;
		if (rewardNo == 0)
			rewardNo = 2;
		if (weightNo == 0)
			weightNo = 2;

		no = (float) (0.5 * (numNo / notCancelledJobs.size()) * (rewardNo / notCancelledJobs.size()) * (weightNo)
				/ notCancelledJobs.size());

		// comparing the probability of being cancelled with the probability of
		// not being cancelled
		// System.out.println("no " + no);
		if (yes > no)
			return true;
		return false;
	}

	public static void createFeatures() {
		Reading.readTrainingJobs();

		List<Job> allJobs = Reading.returnTrainingJobs();
		// randomly choose the data
		Collections.shuffle(allJobs);

		// split into two so that it can be tested later
		List<Job> training = new ArrayList<>();

		// adding the first 80% of the data to the training set
		training.addAll(allJobs.subList(0, (int) (allJobs.size() * 0.8)));
		// adding the remaining 20% of the data to the test set
		test.addAll(allJobs.subList((int) (allJobs.size() * 0.8 + 1), allJobs.size()));

		for (Job j : training) {
			// if the job is cancelled
			if (cancelled.contains(j.returnN())) {
				cancelledJobs.add(addFeatures(j));
			}
			// if the job isn't cancelled
			else {
				notCancelledJobs.add(addFeatures(j));
			}
		}

		for (Job j : test) {
			// if the job is cancelled
			if (cancelled.contains(j.returnN())) {
				testCancelledJobs.add(addFeatures(j));
			}
			// if the job isn't cancelled
			else {
				testNotCancelledJobs.add(addFeatures(j));
			}
		}
	}

	private static ArrayList<Integer> addFeatures(Job j) {
		ArrayList<Integer> features = new ArrayList<>();
		// first feature is the total number of items
		features.add(j.getTotalNumOfItems() / numRange);
		// the second one is the reward, but added as a range
		features.add((int) (j.getReward() / rewardRange));
		// the third one is the weight, but added as a range
		features.add(j.getTotalWeight() / weightRange);
		return features;
	}
}
