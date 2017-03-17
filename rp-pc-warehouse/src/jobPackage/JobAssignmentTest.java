package jobPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

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
		job = new JobAssignment();
	}

	//this tests will fail if new files are used like when we have to the demo so a more robust
	//solution will be needs TODO
	
	@Test
	public void test1() {
		//this represents the start location so will fail if the start location is changed
		Coordinate c = new Coordinate(0, 0);
		Coordinate c1 = job.RobotA.getCoordinate();
		assertTrue(c1.getX() == c.getX() && c.getY() == c1.getY());
	}

	@Test
	public void test2() {
		Coordinate c = job.RobotA.nextCoordinate();
		assertTrue(c.getX() == 8 && c.getY() == 4 && 1 == job.RobotA.getNumOfItems());
	}

	@Test
	public void test3() {
		Coordinate c = job.RobotA.nextCoordinate();
		assertTrue(c.getX() == 9 && c.getY() == 5 && 1 == job.RobotA.getNumOfItems());
	}

	@Test
	public void test4() {
		Coordinate c = job.RobotA.nextCoordinate();
		assertTrue(c.getX() == 3 && c.getY() == 5 && 0 == job.RobotA.getNumOfItems());
	}

	@Test
	public void test5() {
		Coordinate c = job.RobotA.nextCoordinate();
		assertTrue(c.getX() == 8 && c.getY() == 4 && 3 == job.RobotA.getNumOfItems());
	}
	
	@Test
	public void test6() {
		Coordinate c = job.RobotA.nextCoordinate();
		assertTrue(c.getX() == 8 && c.getY() == 3 && 0 == job.RobotA.getNumOfItems());
	}
	
	@Test
	public void TStest1(){
		JobAssignment TSTestClass = new JobAssignment();
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(2, 4), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(3, 3), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(5, 6), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job4"));
		testJob.setItems(itemList);
		Job returnJob = TSTestClass.RobotA.TSsort(testJob);
		assertTrue(returnJob.returnItems().toString().equals("[job4, job1, job2, job3]"));
	}
	
	@Test
	public void TStest2(){
		JobAssignment TSTestClass = new JobAssignment();
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
		Job returnJob = TSTestClass.RobotA.TSsort(testJob);
		assertTrue(returnJob.returnItems().toString().equals("[job1, job2, job3, job4, job5, job6, job7]"));
	}
	
	@Test
	public void TStest3(){
		JobAssignment TSTestClass = new JobAssignment();
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
		Job returnJob = TSTestClass.RobotA.TSsort(testJob);
		assertTrue(returnJob.returnItems().toString().equals("[job3, job7, job5, job4, job1, job2, job6]"));
	}
	
	@Test
	public void TStest4(){
		JobAssignment TSTestClass = new JobAssignment();
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
		Job returnJob = TSTestClass.RobotA.TSsort(testJob);
		assertTrue(returnJob.returnItems().toString().equals("[job7, job6, job5, job4, job3, job2, job1]"));
	}
	
	@Test
	public void TStest5(){
		JobAssignment TSTestClass = new JobAssignment();
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 0), "job7"));
		testJob.setItems(itemList);
		Job returnJob = TSTestClass.RobotA.TSsort(testJob);
		assertTrue(returnJob.returnItems().toString().equals("[job7]"));
	}
	
	@Test
	public void TStest6(){
		JobAssignment TSTestClass = new JobAssignment();
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
		Job returnJob = TSTestClass.RobotA.TSsort(testJob);
		assertTrue(returnJob.returnItems().toString().equals("[job1, job2, job3, job4, job5, job6, job7, job8, job9, job10]"));
	}
	
	//check that no coordinates have been changed
	@Test
	public void TStest7(){
		JobAssignment TSTestClass = new JobAssignment();
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 3), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 4), "job4"));
		testJob.setItems(itemList);
		Job returnJob = TSTestClass.RobotA.TSsort(testJob);
		List<Item> returnItems = returnJob.returnItems();
		
		for (int i = 0; i < itemList.size() ; i++){
			assertTrue(itemList.get(i).rCoordinate().getX() == returnItems.get(i).rCoordinate().getX());
			assertTrue(itemList.get(i).rCoordinate().getY() == returnItems.get(i).rCoordinate().getY());
		}
	}
	
	@Test
	public void TStest8(){
		JobAssignment TSTestClass = new JobAssignment();
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 3), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 4), "job4"));
		testJob.setItems(itemList);
		Job returnJob = TSTestClass.RobotA.TSsort(testJob);
		List<Item> returnItems = returnJob.returnItems();
		
		for (int i = 0; i < itemList.size() ; i++){
			assertTrue(itemList.get(i).rName().equals(returnItems.get(i).rName()));
			assertTrue(itemList.get(i).rName().equals(returnItems.get(i).rName()));
		}
	}
	
	@Test
	public void TStest9(){
		JobAssignment TSTestClass = new JobAssignment();
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 3), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 4), "job4"));
		testJob.setItems(itemList);
		Job returnJob = TSTestClass.RobotA.TSsort(testJob);
		List<Item> returnItems = returnJob.returnItems();
		
		for (int i = 0; i < itemList.size() ; i++){
			assertTrue(itemList.get(i).rValue() == returnItems.get(i).rValue());
			assertTrue(itemList.get(i).rValue() == returnItems.get(i).rValue());
		}
	}
	
	@Test
	public void TStest10(){
		JobAssignment TSTestClass = new JobAssignment();
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 3), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 4), "job4"));
		testJob.setItems(itemList);
		Job returnJob = TSTestClass.RobotA.TSsort(testJob);
		List<Item> returnItems = returnJob.returnItems();
		
		for (int i = 0; i < itemList.size() ; i++){
			assertTrue(itemList.get(i).rWeight() == returnItems.get(i).rWeight());
			assertTrue(itemList.get(i).rWeight() == returnItems.get(i).rWeight());
		}
	}
	
	@Test
	public void TStest11(){
		JobAssignment TSTestClass = new JobAssignment();
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 2), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 3), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 4), "job4"));
		testJob.setItems(itemList);
		Job returnJob = TSTestClass.RobotA.TSsort(testJob);
		List<Item> returnItems = returnJob.returnItems();
		
		for (int i = 0; i < itemList.size() ; i++){
			assertFalse(itemList.get(i).rWeight() == returnItems.get(i).rValue());
			assertTrue(itemList.get(i).rWeight() == returnItems.get(i).rValue());
		}
	}
	
	@Test
	public void muliRobotTest(){
		JobAssignment JATestClass = new JobAssignment();
		System.out.println(JATestClass.RobotA.getJobName());
		System.out.println(JATestClass.RobotB.getJobName());
		System.out.println(JATestClass.RobotC.getJobName());
		
		assertTrue(JATestClass.RobotA.getCoordinate().getX() == 0);
		assertTrue(JATestClass.RobotA.getCoordinate().getY() == 0);
		
		assertTrue(JATestClass.RobotB.getCoordinate().getX() == 1);
		assertTrue(JATestClass.RobotB.getCoordinate().getY() == 0);

		assertTrue(JATestClass.RobotC.getCoordinate().getX() == 2);
		assertTrue(JATestClass.RobotC.getCoordinate().getY() == 0);
		
		System.out.println(JATestClass.RobotA.getCoordinate().getX() + "," + JATestClass.RobotA.getCoordinate().getY());
		System.out.println(JATestClass.RobotB.getCoordinate().getX() + "," + JATestClass.RobotB.getCoordinate().getY());
		System.out.println(JATestClass.RobotC.getCoordinate().getX() + "," + JATestClass.RobotC.getCoordinate().getY());
		
		
		System.out.println(JATestClass.RobotA.nextCoordinate().getX() + "," + JATestClass.RobotA.nextCoordinate().getY());
		System.out.println(JATestClass.RobotA.getJobName());
		System.out.println(JATestClass.RobotA.getNumOfItems());
		
		System.out.println(JATestClass.RobotB.nextCoordinate().getX() + "," + JATestClass.RobotB.nextCoordinate().getY());
		System.out.println(JATestClass.RobotB.getJobName());
		System.out.println(JATestClass.RobotB.getNumOfItems());
		
		System.out.println(JATestClass.RobotC.nextCoordinate().getX() + "," + JATestClass.RobotC.nextCoordinate().getY());
		System.out.println(JATestClass.RobotC.getJobName());
		System.out.println(JATestClass.RobotC.getNumOfItems());
		
		
		System.out.println(JATestClass.RobotA.getNumOfItems());
		System.out.println(JATestClass.RobotB.getNumOfItems());
		System.out.println(JATestClass.RobotC.getNumOfItems());
	}
}
