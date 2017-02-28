

import lejos.robotics.RangeFinder;
import rp.robotics.mapping.GridMap;
import rp.robotics.navigation.GridPilot;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MovableRobot;
import rp.systems.StoppableRunnable;

public class Controller implements StoppableRunnable{
	private final GridMap m_map;
	private final GridPilot m_pilot;

	private boolean m_running = true;
	private final RangeFinder m_ranger;
	private final MovableRobot m_robot;
	
	public Controller(MovableRobot _robot, GridMap _map, GridPose _start,
			RangeFinder _ranger) {
		m_map = _map;
		m_pilot = new GridPilot(_robot.getPilot(), _map, _start);
		m_ranger = _ranger;
		m_robot = _robot;
	}
	
	private boolean enoughSpace() {
		return m_ranger.getRange() > m_map.getCellSize()
				+ m_robot.getRobotLength() / 2f;
	}
	
	private boolean moveAheadClear() {
		GridPose current = m_pilot.getGridPose();
		GridPose moved = current.clone();
		moved.moveUpdate();
		return m_map.isValidTransition(current.getPosition(),
				moved.getPosition())
				&& enoughSpace();
	}
	
	private void moveLeft(){
		
		m_pilot.rotateNegative();
		if(moveAheadClear()){
			m_pilot.moveForward();			
		}
	}
	
	private void moveRight(){
		
		m_pilot.rotatePositive();
		if(moveAheadClear()){
			m_pilot.moveForward();			
		}
	}
	
	private void moveBack(){
		
		m_pilot.rotatePositive();
		m_pilot.rotatePositive();
		if(moveAheadClear()){
			m_pilot.moveForward();			
		}
	}
	
	private void moveForward(){
		

		if(moveAheadClear()){
			m_pilot.moveForward();			
		}
	}

	@Override
	public void run() {
		
		GridPose goalPose = new GridPose(3 * (m_pilot.getGridPose().getX()+1), 4 * (m_pilot.getGridPose().getY()+1), m_pilot.getGridPose().getHeading());
		//int gx = goalPose.getX();
		//int gy = goalPose.getY();
		
		int gx = 5;
		int gy = 5;
		while(m_running){
			
			
			int x = m_pilot.getGridPose().getX();
			int y = m_pilot.getGridPose().getY();
			
			
			while(x < gx){
				
				m_pilot.setGridPose(new GridPose(x, y, Heading.PLUS_X));
				m_pilot.moveForward();
				x = m_pilot.getGridPose().getX();
			}
			while(y < gy){
				
				m_pilot.setGridPose(new GridPose(x, y, Heading.PLUS_Y));
				m_pilot.moveForward();
				y = m_pilot.getGridPose().getY();
			}			
			
		}
		
	}

	@Override
	public void stop() {
		m_running = false;
		
	}

}
