package warehouseInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import lejos.robotics.RangeFinder;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPilot;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MovableRobot;
import rp.systems.StoppableRunnable;

public class DispRobotController implements StoppableRunnable {

	private Queue<GridPose> path = new LinkedList<GridPose>();

	private final GridMap m_map;
	private final GridPilot m_pilot;

	private boolean m_running = true;
	private final RangeFinder m_ranger;
	private final MovableRobot m_robot;
	public GridPose currentPosition;

	public DispRobotController(MovableRobot _robot, GridMap _map, GridPose _start, RangeFinder _ranger) {
		m_map = _map;
		m_pilot = new GridPilot(_robot.getPilot(), _map, _start);
		m_ranger = _ranger;
		m_robot = _robot;
		this.currentPosition = _start;
	}

	@Override
	public void run() {
		while(m_running){
			//use Window.robotData.get(1).get(2).setText("asdasd"); to set the lables
			GridPose test;
			if (!((test = getCoordinate()) == null)){
				if (!(currentPosition.getX() - test.getX() == 0)){
					if (currentPosition.getX() - test.getX() < 0){
						m_pilot.rotateNegative();
						currentPosition = test;						
					} else {
						m_pilot.rotatePositive();
						currentPosition = test;		
					}
				} else if (!(test.getY() - currentPosition.getY() == 0)){
					System.out.println("this is going left");
					if (test.getY() - currentPosition.getY() < 0){
						m_pilot.rotateNegative();
						m_pilot.rotateNegative();
						currentPosition = test;
					}
				}
				m_pilot.moveForward();
				currentPosition = test;
			}
		}
	}

	@Override
	public void stop() {
		m_running = false;
	}

	public void addCoordinate(int x, int y) {
		path.add(new GridPose(x,y, Heading.PLUS_Y));
	}

	public GridPose getCoordinate() {
		if (!path.isEmpty()){
			return path.remove();
		}
		return null;
	}
}
