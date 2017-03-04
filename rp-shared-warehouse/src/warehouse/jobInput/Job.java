package warehouse.jobInput;

import java.io.*;
import java.util.*;
import java.net.*;

// Gets messages from other clients via the server (by the
// ServerSender thread).

public class Job {
	static List<Item> list = new ArrayList<Item>();

	private String name;

	Job(String name) {
		this.name = name;

	}

	public void addItem(Item i) {
		list.add(i);
	}

	public List<Item> returnItems() {
		return list;
	}

	public String returnN() {
		return name;
	}

}
