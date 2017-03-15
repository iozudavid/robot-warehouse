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

	static JobAssignment job;

	@BeforeClass
	public static void before() {
		job = new JobAssignment();
	}

	@Test
	public void test1() {
		//this represents the start location so will fail if the start location is changed
		Coordinate c = new Coordinate(0, 0);
		Coordinate c1 = job.getCoordinate();
		assertTrue(c1.getX() == c.getX() && c.getY() == c1.getY());
	}

	@Test
	public void test2() {
		Coordinate c = job.nextCoordinate();
		assertTrue(c.getX() == 8 && c.getY() == 4 && 1 == job.getNumOfItems());
	}

	@Test
	public void test3() {
		Coordinate c = job.nextCoordinate();
		assertTrue(c.getX() == 9 && c.getY() == 5 && 1 == job.getNumOfItems());
	}

	@Test
	public void test4() {
		Coordinate c = job.nextCoordinate();
		assertTrue(c.getX() == 3 && c.getY() == 5 && 0 == job.getNumOfItems());
	}

	@Test
	public void test5() {
		Coordinate c = job.nextCoordinate();
		assertTrue(c.getX() == 3 && c.getY() == 5 && 1 == job.getNumOfItems());
	}
	
	@Test
	public void test6() {
		Coordinate c = job.nextCoordinate();
		assertTrue(c.getX() == 5 && c.getY() == 5 && 3 == job.getNumOfItems());
	}
	
	/*
	@Test
	public void TStest(){
		JobAssignment TSTestClass = new JobAssignment();
		Job testJob = new Job("1000");
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(new Item(1f, 1f, new Coordinate(2, 4), "job1"));
		itemList.add(new Item(1f, 1f, new Coordinate(3, 3), "job2"));
		itemList.add(new Item(1f, 1f, new Coordinate(5, 6), "job3"));
		itemList.add(new Item(1f, 1f, new Coordinate(0, 1), "job4"));
		testJob.setItems(itemList);
		
		System.out.println("first: " + itemList.toString());
		System.out.println("run");
		Job returnJob = TSTestClass.TSsort(testJob);
		System.out.println("run1");
		System.out.println("second: " + returnJob.returnItems().toString());
	}
	*/
	
}
