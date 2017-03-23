package jobPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import warehouse.Coordinate;
import warehouse.jobInput.Item;
import warehouse.jobInput.Job;

public class JobAssignmentTest {

	//need this otherwise i get weird errors from the gridmap class saying there
	//is an obstacle 
	static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(false);
    }
	
	static JobAssignment job;

	@BeforeClass
	public static void before() {
		Job job1 = new Job("job1");
		Job job2 = new Job("job2");
		Job job3 = new Job("job3");
		
		job1.addItem(new Item(2f, 2f, new Coordinate(2, 2), "item1"), 2);
		job1.addItem(new Item(2f, 3f, new Coordinate(2, 3), "item2"), 2);
		job1.addItem(new Item(2f, 4f, new Coordinate(2, 4), "item3"), 2);
		
		job2.addItem(new Item(2f, 3f, new Coordinate(2, 3), "item1"), 2);
		job2.addItem(new Item(2f, 4f, new Coordinate(2, 4), "item2"), 2);
		job2.addItem(new Item(2f, 5f, new Coordinate(2, 5), "item3"), 2);

		job3.addItem(new Item(2f, 4f, new Coordinate(2, 4), "item1"), 2);
		job3.addItem(new Item(2f, 5f, new Coordinate(2, 5), "item2"), 2);
		job3.addItem(new Item(2f, 6f, new Coordinate(2, 6), "item3"), 2);
		
		ArrayList<Job> jobs = new ArrayList<Job>();
		jobs.add(job1);
		jobs.add(job2);
		jobs.add(job3);
		
		job = new JobAssignment(jobs);
	}

	//this tests will fail if new files are used like when we have to the demo so a more robust
	//solution will be needs TODO
	
	@Test
	public void muliRobotJobAssignment() {
		//this represents the start location so will fail if the start location is changed
		NXTInfo intoObject = new NXTInfo(2, "a", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Coordinate c = job.RobotA.nextCoordinate();
		assertTrue(c.getX() == 2 && c.getY() == 4);
		
		c = job.RobotB.nextCoordinate();
		assertTrue(c.getX() == 2 && c.getY() == 3);
		
		c = job.RobotC.nextCoordinate();
		assertTrue(c.getX() == 2 && c.getY() == 2);
		
		c = job.RobotA.nextCoordinate();
		assertTrue(c.getX() == 2 && c.getY() == 5);
		
		c = job.RobotB.nextCoordinate();
		assertTrue(c.getX() == 2 && c.getY() == 4);
		
		c = job.RobotC.nextCoordinate();
		assertTrue(c.getX() == 2 && c.getY() == 3);
		
		c = job.RobotA.nextCoordinate();
		assertTrue(c.getX() == 2 && c.getY() == 6);
		
		c = job.RobotB.nextCoordinate();
		assertTrue(c.getX() == 2 && c.getY() == 5);
		
		c = job.RobotC.nextCoordinate();
		assertTrue(c.getX() == 2 && c.getY() == 4);
	}
		
	@Test
	public void TStest1(){
		NXTInfo intoObject = new NXTInfo(2, "a", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);

		//JobAssignment TSTestClass = new JobAssignment(eachJob);
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(2, 4), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(3, 3), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(5, 6), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job4"));
		testJob.setItems(itemList);
		Job job = new Job("job1");
		job.setItems(itemList);
		itemList = TSTestClass.TSsort(job, new Coordinate(0, 0));
		assertTrue(itemList.toString().equals("[job4, job1, job2, job3]"));
	}
	
	@Test
	public void TStest2(){
		NXTInfo intoObject = new NXTInfo(2, "a", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 7), "job7"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 6), "job6"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 5), "job5"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 4), "job4"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 3), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job1"));
		testJob.setItems(itemList);
		Job job = new Job("job1");
		job.setItems(itemList);
		itemList = TSTestClass.TSsort(job, new Coordinate(0, 0));
		assertTrue(itemList.toString().equals("[job1, job2, job3, job4, job5, job6, job7]"));
	}
	
	@Test
	public void TStest3(){
		NXTInfo intoObject = new NXTInfo(2, "NXT", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 7), "job7"));
		itemList.add(new Item(1f, 1f, new Coordinate(6, 6), "job6"));
		itemList.add(new Item(1f, 1f, new Coordinate(3, 5), "job5"));
		itemList.add(new Item(1f, 1f, new Coordinate(3, 4), "job4"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 3), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(5, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(3, 1), "job1"));
		testJob.setItems(itemList);
		Job job = new Job("job1");
		job.setItems(itemList);
		itemList = TSTestClass.TSsort(job, new Coordinate(0, 0));
		assertTrue(itemList.toString().equals("[job3, job7, job5, job4, job1, job2, job6]"));
	}
	
	@Test
	public void TStest4(){
		NXTInfo intoObject = new NXTInfo(2, "NXT", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 0), "job7"));
		itemList.add(new Item(1f, 1f, new Coordinate(1, 0), "job6"));
		itemList.add(new Item(1f, 1f, new Coordinate(2, 0), "job5"));
		itemList.add(new Item(1f, 1f, new Coordinate(3, 0), "job4"));
		itemList.add(new Item(1f, 1f, new Coordinate(4, 0), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(5, 0), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(6, 0), "job1"));
		testJob.setItems(itemList);
		Job job = new Job("job1");
		job.setItems(itemList);
		itemList = TSTestClass.TSsort(job, new Coordinate(0, 0));
		assertTrue(itemList.toString().equals("[job7, job6, job5, job4, job3, job2, job1]"));
	}
	
	@Test
	public void TStest5(){
		NXTInfo intoObject = new NXTInfo(2, "a", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 0), "job7"));
		testJob.setItems(itemList);
		Job job = new Job("job1");
		job.setItems(itemList);
		itemList = TSTestClass.TSsort(job, new Coordinate(0, 0));
		assertTrue(itemList.toString().equals("[job7]"));
	}
	
	@Test
	public void TStest6(){
		NXTInfo intoObject = new NXTInfo(2, "a", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(1, 0), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(2, 5), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(5, 2), "job5"));
		itemList.add(new Item(1f, 1f, new Coordinate(2, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(5, 5), "job4"));
		itemList.add(new Item(1f, 1f, new Coordinate(6, 0), "job6"));
		itemList.add(new Item(1f, 1f, new Coordinate(8, 0), "job7"));
		itemList.add(new Item(1f, 1f, new Coordinate(9, 5), "job8"));
		itemList.add(new Item(1f, 1f, new Coordinate(9, 7), "job9"));
		itemList.add(new Item(1f, 1f, new Coordinate(11, 7), "job10"));
		testJob.setItems(itemList);
		Job job = new Job("job1");
		job.setItems(itemList);
		itemList = TSTestClass.TSsort(job, new Coordinate(0, 0));
		assertTrue(itemList.toString().equals("[job1, job2, job3, job4, job5, job6, job7, job8, job9, job10]"));
	}
	
	//check that no coordinates have been changed
	@Test
	public void TStest7(){
		NXTInfo intoObject = new NXTInfo(2, "a", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 3), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 4), "job4"));
		testJob.setItems(itemList);
		Job job = new Job("job1");
		job.setItems(itemList);
		itemList = TSTestClass.TSsort(job, new Coordinate(0, 0));
		List<Item> returnItems = itemList;
		
		for (int i = 0; i < itemList.size() ; i++){
			assertTrue(itemList.get(i).rCoordinate().getX() == returnItems.get(i).rCoordinate().getX());
			assertTrue(itemList.get(i).rCoordinate().getY() == returnItems.get(i).rCoordinate().getY());
		}
	}
	
	@Test
	public void TStest8(){
		NXTInfo intoObject = new NXTInfo(2, "a", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 3), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 4), "job4"));
		testJob.setItems(itemList);
		Job job = new Job("job1");
		job.setItems(itemList);
		itemList = TSTestClass.TSsort(job, new Coordinate(0, 0));
		List<Item> returnItems = itemList;
		
		for (int i = 0; i < itemList.size() ; i++){
			assertTrue(itemList.get(i).rName().equals(returnItems.get(i).rName()));
			assertTrue(itemList.get(i).rName().equals(returnItems.get(i).rName()));
		}
	}
	
	@Test
	public void TStest9(){
		NXTInfo intoObject = new NXTInfo(2, "a", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 3), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 4), "job4"));
		testJob.setItems(itemList);
		Job job = new Job("job1");
		job.setItems(itemList);
		itemList = TSTestClass.TSsort(job, new Coordinate(0, 0));
		List<Item> returnItems = itemList;
		
		for (int i = 0; i < itemList.size() ; i++){
			assertTrue(itemList.get(i).rValue() == returnItems.get(i).rValue());
			assertTrue(itemList.get(i).rValue() == returnItems.get(i).rValue());
		}
	}
	
	@Test
	public void TStest10(){
		NXTInfo intoObject = new NXTInfo(2, "a", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 3), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 4), "job4"));
		testJob.setItems(itemList);
		Job job = new Job("job1");
		job.setItems(itemList);
		itemList = TSTestClass.TSsort(job, new Coordinate(0, 0));
		List<Item> returnItems = itemList;
		
		for (int i = 0; i < itemList.size() ; i++){
			assertTrue(itemList.get(i).rWeight() == returnItems.get(i).rWeight());
			assertTrue(itemList.get(i).rWeight() == returnItems.get(i).rWeight());
		}
	}
	
	@Test
	public void TStest11(){
		NXTInfo intoObject = new NXTInfo(2, "a", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 3), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 4), "job4"));
		testJob.setItems(itemList);
		Job job = new Job("job1");
		job.setItems(itemList);
		itemList = TSTestClass.TSsort(job, new Coordinate(0, 0));
		List<Item> returnItems = itemList;
		
		
		for (int i = 0; i < itemList.size() ; i++){
			assertTrue(itemList.get(i).rWeight() == returnItems.get(i).rValue());
			assertTrue(itemList.get(i).rWeight() == returnItems.get(i).rValue());
		}
	}
	
	@Test
	public void muliRobotTest(){
		NXTInfo intoObject1 = new NXTInfo(2, "NXT", "1231231");
		NXTInfo intoObject2 = new NXTInfo(2, "William", "1231231");
		NXTInfo intoObject3 = new NXTInfo(2, "Phil", "1231231");
		NXTInfo[] arrayTest = {intoObject1, intoObject2, intoObject3};
		JobAssignment JATestClass = new JobAssignment(arrayTest);
				
		assertTrue(JATestClass.getRobotJobAssignment("NXT").getCoordinate().getX() == 0);
		assertTrue(JATestClass.getRobotJobAssignment("NXT").getCoordinate().getY() == 0);
		
		assertTrue(JATestClass.getRobotJobAssignment("William").getCoordinate().getX() == 11);
		assertTrue(JATestClass.getRobotJobAssignment("William").getCoordinate().getY() == 7);

		assertTrue(JATestClass.getRobotJobAssignment("Phil").getCoordinate().getX() == 6);
		assertTrue(JATestClass.getRobotJobAssignment("Phil").getCoordinate().getY() == 0);
	}
	
	@Test
	public void addItemTest(){
		NXTInfo[] robots = { new NXTInfo(NXTCommFactory.BLUETOOTH, "NXT", "0016530C73B0"),
				new NXTInfo(NXTCommFactory.BLUETOOTH, "William", "00165308E546"),
				new NXTInfo(NXTCommFactory.BLUETOOTH, "Phil", "0016530A631F") };
		
		JobAssignment jobs = new JobAssignment(robots);
		
		jobs.getRobotJobAssignment(robots[0].name).nextCoordinate();
		System.out.println(jobs.getRobotJobAssignment(robots[0].name).getItemName());
		assertTrue(jobs.getRobotJobAssignment(robots[0].name).getItemName().equals("ac"));
		assertTrue(jobs.getRobotJobAssignment(robots[0].name).getNumOfItems() == 1);
		jobs.getRobotJobAssignment(robots[0].name).addItem(jobs.getRobotJobAssignment(robots[0].name).getNumOfItems());
		jobs.getRobotJobAssignment(robots[0].name).nextCoordinate();
		assertTrue(jobs.getRobotJobAssignment(robots[0].name).getItemName().equals("ac"));
		assertTrue(jobs.getRobotJobAssignment(robots[0].name).getNumOfItems() == 1);
	}
	
	@Test
	public void dropLocationTest1(){	
		NXTInfo intoObject = new NXTInfo(2, "NXT", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Coordinate test = JobAssignment.findDropOff(new Coordinate(0, 0));
		assertTrue(test.getX() == 4);
		assertTrue(test.getY() == 7);
	}
	
	@Test
	public void dropLocationTest2(){	
		NXTInfo intoObject = new NXTInfo(2, "NXT", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Coordinate test = JobAssignment.findDropOff(new Coordinate(10, 6));
		assertTrue(test.getX() == 7);
		assertTrue(test.getY() == 7);
	}
	
	@Test
	public void dropLocationTest3(){	
		NXTInfo intoObject = new NXTInfo(2, "NXT", "1231231");
		NXTInfo[] arrayTest = {intoObject};
		JobAssignment TSTestClass = new JobAssignment(arrayTest);
		
		Coordinate test = JobAssignment.findDropOff(new Coordinate(3, 6));
		assertTrue(test.getX() == 4);
		assertTrue(test.getY() == 7);
	}
}