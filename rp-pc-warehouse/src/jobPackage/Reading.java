package jobPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import Variables.InputFiles;
import warehouse.Coordinate;
import warehouse.jobInput.Item;
import warehouse.jobInput.Job;

public class Reading {
	static BufferedReader inputFile1 = null;
	static BufferedReader inputFile2 = null;

	static PrintWriter outputFile = null;
	static String line1 = "";
	static String line2 = "";
	static int n = 0;
	static String[] nickAr = new String[100];
	static String[] passAr = new String[100];
	String K;
	String V;
	static List<Job> list = new ArrayList<Job>();
	static List<Job> training = new ArrayList<Job>();
	static ArrayList<Coordinate> dropOffs = new ArrayList<Coordinate>();

	// static Hashtable<String, Item> Items = new Hashtable<String, Item>();

	static ConcurrentMap<String, Item> queueTable = new ConcurrentHashMap<String, Item>();

	// Returns null if the nickname is not in the table:
	public static void readItem() {
		try {
			inputFile1 = new BufferedReader(new FileReader(InputFiles.ITEMSFILE));
			inputFile2 = new BufferedReader(new FileReader(InputFiles.LOCATIONSFILE));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line1 = inputFile1.readLine()) != null) {
				line2 = inputFile2.readLine();
				String[] splitItem = line1.split(",");
				String[] splitLoc = line2.split(",");
				Coordinate cord = new Coordinate(Integer.valueOf(splitLoc[0]), Integer.valueOf(splitLoc[1]));

				Item item = new Item(Float.valueOf(splitItem[1]), Float.valueOf(splitItem[2]), cord, splitItem[0]);
				queueTable.put(splitItem[0], item);
			}
			inputFile1.close();
			inputFile2.close();

		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}

	}

	public static void readJobs() {
		try {
			inputFile1 = new BufferedReader(new FileReader(InputFiles.JOBSFILE));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line1 = inputFile1.readLine()) != null) {
				String[] splitStr = line1.split(",");
				int n = splitStr.length - 1;
				Job job = new Job(splitStr[0]);
				for (int i = 1; i < n; i++) {
					job.addItem(queueTable.get(splitStr[i]), Integer.valueOf(splitStr[i + 1]));
					i++;
				}
				// Item item = new Item(Float.valueOf(splitStr[1]),
				// Float.valueOf(splitStr[2]),Integer.valueOf(splitStr[3]),Integer.valueOf(splitStr[4]));
				// queueTable.put(splitStr[0], item);
				list.add(job);
			}
			inputFile1.close();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	public static void readTrainingJobs() {
		try {
			inputFile1 = new BufferedReader(new FileReader(InputFiles.TRAININGJOBSFILE));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line1 = inputFile1.readLine()) != null) {
				String[] splitStr = line1.split(",");
				int n = splitStr.length - 1;
				Job job = new Job(splitStr[0]);
				for (int i = 1; i < n; i++) {
					job.addItem(queueTable.get(splitStr[i]), Integer.valueOf(splitStr[i + 1]));
					i++;
				}
				training.add(job);
			}
			inputFile1.close();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	public static void readDropOff() {
		dropOffs = new ArrayList<Coordinate>();
		try {
			inputFile1 = new BufferedReader(new FileReader("drops.csv"));
		} catch (IOException e) {
			System.out.println("cant read drop off file");
			System.exit(1);
		}
		try {
			while ((line1 = inputFile1.readLine()) != null) {
				line1 = line1.replaceAll("\\s+", "");
				if (!line1.equals("")) {
					String[] splitStr = line1.split(",");
					dropOffs.add(new Coordinate(Integer.parseInt(splitStr[0]), Integer.parseInt(splitStr[1])));
				}
			}
			inputFile1.close();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	public static ArrayList<Coordinate> returnDropOffs() {
		return dropOffs;
	}

	public static List<Job> returnJobs() {
		return list;
	}

	public static List<Job> returnTrainingJobs() {
		return training;
	}
}