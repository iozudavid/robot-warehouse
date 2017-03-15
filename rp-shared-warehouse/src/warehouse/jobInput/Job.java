package warehouse.jobInput;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// Gets messages from other clients via the server (by the
// ServerSender thread).

public class Job {
	List<Item> list = new ArrayList<Item>();
	ConcurrentMap<String, Integer> queueTable = new ConcurrentHashMap<String, Integer>();
	private String name;
	private float reward = 0;

	public Job(String name) {
		this.name = name;
	}

	public void addItem(Item i, Integer n) {
		list.add(i);
		queueTable.put(i.rName(), n);
	}

	public List<Item> returnItems() {
		return list;
	}

	public String returnN() {
		return name;
	}

	public Integer returnNmbr(String s) {
		return queueTable.get(s);
	}

	public void setNumOfItems(String item, int number) {
		queueTable.replace(item, number);
	}

	public float getReward() {
		for(Item i : list){
			reward += i.rValue();
		}
		return reward;
	}
	
	public void setItems(List<Item> items){
		this.list = items;
	}
	
	@Override
	public String toString(){
		return name;
	}

}
