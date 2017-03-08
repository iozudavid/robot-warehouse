package warehouseInterface;

import java.util.LinkedList;
import java.util.Queue;

import lejos.robotics.RangeFinder;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPilot;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MovableRobot;
import rp.systems.StoppableRunnable;
import rp.util.Rate;
import warehouse.Coordinate;

//these may be needed in a demo of the system
import warehouse.Path;
import warehouse.PathFinding;
import warehouse.SearchCell;

public class DispRobotController implements StoppableRunnable {

	private final GridMap m_map;
	private final GridPilot m_pilot;

	private final RangeFinder m_ranger;
	private final MovableRobot m_robot;
	public Coordinate next;
	public Coordinate startCoordinate;
	private Queue<Coordinate> route;


	public DispRobotController(MovableRobot _robot, GridMap _map, GridPose _start, RangeFinder _ranger,
			Coordinate _startCoordinate) {
		m_map = _map;
		m_pilot = new GridPilot(_robot.getPilot(), _map, _start);
		m_ranger = _ranger;
		m_robot = _robot;
		this.startCoordinate = _startCoordinate;
		m_pilot.setTurnSpeed(150f);
		m_pilot.setTravelSpeed(0.2f);
		route = new LinkedList<Coordinate>();
	}

	private boolean enoughSpace() {
		return m_ranger.getRange() > m_map.getCellSize() + m_robot.getRobotLength() / 2f;
	}

	private boolean moveAheadClear() {
		GridPose current = m_pilot.getGridPose();
		GridPose moved = current.clone();
		moved.moveUpdate();
		return m_map.isValidTransition(current.getPosition(), moved.getPosition()) && enoughSpace();
	}

	public void followPath() {
		Rate r = new Rate(4);
		//the rate is needed so that this thread does not take all the cpu time
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("thread error in displaying robot controller");
			}
			
			//goes through the queue emptying it as it goes and then moving the robot to that
			
			if (!route.isEmpty()) {
				int x = m_pilot.getGridPose().getX();
				int y = m_pilot.getGridPose().getY();

				next = route.remove();

				int gx = next.getX();
				int gy = next.getY();

				if (m_pilot.getGridPose().getHeading() == Heading.PLUS_Y) {
					if (x < gx) {
						m_pilot.rotateNegative();
						m_pilot.moveForward();
					} else if (x > gx) {
						m_pilot.rotatePositive();
						m_pilot.moveForward();
					} else if (y < gy) {
						m_pilot.moveForward();
					} else if (y > gy) {
						m_pilot.rotateNegative();
						m_pilot.rotateNegative();
						m_pilot.moveForward();
					}
				} else if (m_pilot.getGridPose().getHeading() == Heading.MINUS_X) {
					if (x < gx) {
						m_pilot.rotateNegative();
						m_pilot.rotateNegative();
						m_pilot.moveForward();
					} else if (x > gx) {
						m_pilot.moveForward();
					} else if (y < gy) {
						m_pilot.rotateNegative();
						m_pilot.moveForward();
					} else if (y > gy) {
						m_pilot.rotatePositive();
						m_pilot.moveForward();
					}
				} else if (m_pilot.getGridPose().getHeading() == Heading.PLUS_X) {
					if (x < gx) {
						m_pilot.moveForward();
					} else if (x > gx) {
						m_pilot.rotatePositive();
						m_pilot.rotatePositive();
						m_pilot.moveForward();
					} else if (y < gy) {
						m_pilot.rotatePositive();
						m_pilot.moveForward();
					} else if (y > gy) {
						m_pilot.rotateNegative();
						m_pilot.moveForward();
					}
				} else if (m_pilot.getGridPose().getHeading() == Heading.MINUS_Y) {
					if (x < gx) {
						m_pilot.rotatePositive();
						m_pilot.moveForward();
					} else if (x > gx) {
						m_pilot.rotateNegative();
						m_pilot.moveForward();
					} else if (y < gy) {
						m_pilot.rotatePositive();
						m_pilot.rotatePositive();
						m_pilot.moveForward();
					} else if (y > gy) {
						m_pilot.moveForward();
					}
				}
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					System.out.println("thread interupted while waiting for more coordinates");
				}
			}

		}
	}

	@Override
	public void run() {

		//can use this for a demo of how to works
		/*
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		PathFinding find = new PathFinding(new SearchCell(startCoordinate), new SearchCell(new Coordinate(10, 3)));
		list = find.aStar()
		path = new Path(list);
		while (m_running) {
			followPath(path);
		}
		*/
		//Rate r = new Rate(3);
		while(true){
			followPath();
		}
	}

	@Override
	public void stop() {

	}

	public void addToQueue(Coordinate nextLocation) {
		route.add(nextLocation);
	}
	
	public Coordinate getCurrentLocation(){
		int x = m_pilot.getGridPose().getX();
		int y = m_pilot.getGridPose().getY();
		
		Coordinate currentCoordinate = new Coordinate(x, y);
		return currentCoordinate;
	}
}
