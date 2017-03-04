package warehouse.jobInput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Reading {
	static BufferedReader inputFile = null;
	static PrintWriter outputFile = null;
	static String line = "";
	static int n = 0;
	static String[] nickAr = new String[100];
	static String[] passAr = new String[100];
	String K;
	String V;
	static List<Job> list = new ArrayList<Job>();

	// static Hashtable<String, Item> Items = new Hashtable<String, Item>();

	private static ConcurrentMap<String, Item> queueTable = new ConcurrentHashMap<String, Item>();

	// Returns null if the nickname is not in the table:
	public static void readStuff() {
		try {
			list.sort(new Comparator<Object>() {

				@Override
				public int compare(Object o1, Object o2) {
					return 0;
				}
				
			});
			inputFile = new BufferedReader(new FileReader("Whatev.txt"));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line = inputFile.readLine()) != null) {
				String[] splitStr = line.split("\\s+");
				Item item = new Item(Float.valueOf(splitStr[1]), Float.valueOf(splitStr[2]),
						Integer.valueOf(splitStr[3]), Integer.valueOf(splitStr[4]));
				queueTable.put(splitStr[0], item);
			}
			inputFile.close();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}

	}

	public static void readJobs() {
		try {
			inputFile = new BufferedReader(new FileReader("Whatev.txt"));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line = inputFile.readLine()) != null) {
				String[] splitStr = line.split("\\s+");
				int n = splitStr.length - 1;
				Job job = new Job(splitStr[0]);
				for (int i = 0; i < n; i++) {
					job.addItem(queueTable.get(splitStr[i]));
					i++;
				}
				// Item item = new Item(Float.valueOf(splitStr[1]),
				// Float.valueOf(splitStr[2]),Integer.valueOf(splitStr[3]),Integer.valueOf(splitStr[4]));
				// queueTable.put(splitStr[0], item);
				list.add(job);
			}
			inputFile.close();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	public List<Job> returnJobs() {
		return list;
	}
}
