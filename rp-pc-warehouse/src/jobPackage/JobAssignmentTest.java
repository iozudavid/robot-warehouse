package jobPackage;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import warehouse.Coordinate;

public class JobAssignmentTest {

	static JobAssignment job;

	@BeforeClass
	public static void before() {
		job = new JobAssignment();
	}

	@Test
	public void test1() {
		Coordinate c = new Coordinate(0, 5);
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

}
