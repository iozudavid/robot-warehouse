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
		for(Item i : list){
			reward += i.rValue();
		}
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
		return reward;
	}
	
	public void setItems(List<Item> items){
		this.list = items;
	}
	
	public int getTotalNumOfItems(){
		int num = 0;
		for(Item i: list){
			num += returnNmbr(i.rName());
		}
		return num;
	}
	
	public int getTotalWeight(){
		int w = 0;
		for(Item i: list){
			w += returnNmbr(i.rName())* i.rWeight();
		}
		return w;
	}
	
	public void removeItem(Item item){
		list.remove(item);
	}
	
	public void addToHashTable(String name, int num){
		queueTable.putIfAbsent(name, num);
	}
	
	@Override
	public String toString(){
		return name;
	}

}
