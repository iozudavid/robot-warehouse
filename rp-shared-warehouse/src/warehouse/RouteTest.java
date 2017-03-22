

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class RouteTest {

	public int expected;
	public SearchCell start;
	public SearchCell goal;

	@Parameters
	public static Collection<Object[]> getTestParameters() {
		return Arrays.asList(
				new Object[][] { { 3, new SearchCell(new Coordinate(0, 0)), new SearchCell(new Coordinate(1, 2)) },
						{ 11, new SearchCell(new Coordinate(0, 0)), new SearchCell(new Coordinate(7, 4)) },
						{ 0, new SearchCell(new Coordinate(0, 0)), new SearchCell(new Coordinate(2, 2)) },
						{ 0, new SearchCell(new Coordinate(0, 0)), new SearchCell(new Coordinate(0, 0)) },
						{ 0, new SearchCell(new Coordinate(0, 0)), new SearchCell(new Coordinate(12, 12)) }

				});
	}

	public RouteTest(int expected, SearchCell start, SearchCell goal) {
		this.expected = expected;
		this.start = start;
		this.goal = goal;
	}

	@Test
	public void route() {
		PathFindingForTest p = new PathFindingForTest(start, goal);
		assertEquals(expected, p.aStar().size());
	}

}
