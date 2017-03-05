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
import warehouse.Coordinate;
import warehouse.Path;
import warehouse.PathFinding;
import warehouse.SearchCell;

public class DispRobotController implements StoppableRunnable {

	private final GridMap m_map;
	private final GridPilot m_pilot;

	private boolean m_running = true;
	private final RangeFinder m_ranger;
	private final MovableRobot m_robot;
	public Coordinate next;
	public Coordinate startCoordinate;
		
	private Path path;
	private String currentJob;
		
		
	public DispRobotController(MovableRobot _robot, GridMap _map, GridPose _start,
			RangeFinder _ranger, Coordinate _startCoordinate) {
		m_map = _map;
		m_pilot = new GridPilot(_robot.getPilot(), _map, _start);
		m_ranger = _ranger;
		m_robot = _robot;	
		this.startCoordinate = _startCoordinate;
		m_pilot.setTurnSpeed(150f);
		m_pilot.setTravelSpeed(0.2f);
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

	public void followPath(Path path){
		
		while(!path.reachedEnd()){

			int x = m_pilot.getGridPose().getX();
			int y = m_pilot.getGridPose().getY();
			
			next = path.getNextCoord();
			
			int gx = next.getX();
			int gy = next.getY();
			
			
			if (m_pilot.getGridPose().getHeading() == Heading.PLUS_Y){
				if(x < gx){
					m_pilot.rotateNegative();
					m_pilot.moveForward();			
				}else if(x>gx){
					m_pilot.rotatePositive();
					m_pilot.moveForward();
				}else if(y<gy){
					m_pilot.moveForward();
				}else if(y>gy){
					m_pilot.rotateNegative();
					m_pilot.rotateNegative();
				}
			} else if (m_pilot.getGridPose().getHeading() == Heading.MINUS_X){
				if(x < gx){
					m_pilot.rotateNegative();
					m_pilot.rotateNegative();
					m_pilot.moveForward();			
				}else if(x>gx){
					m_pilot.moveForward();
				}else if(y<gy){
					m_pilot.rotateNegative();
					m_pilot.moveForward();
				}else if(y>gy){
					m_pilot.rotatePositive();
					m_pilot.moveForward();
				}
			} else if (m_pilot.getGridPose().getHeading() == Heading.PLUS_X){
				if(x < gx){
					m_pilot.moveForward();			
				}else if(x>gx){
					m_pilot.rotatePositive();
					m_pilot.rotatePositive();
					m_pilot.moveForward();
				}else if(y<gy){
					m_pilot.rotatePositive();
					m_pilot.moveForward();
				}else if(y>gy){
					m_pilot.rotateNegative();
					m_pilot.moveForward();
				}
			} else if (m_pilot.getGridPose().getHeading() == Heading.MINUS_Y){
				if(x < gx){
					m_pilot.rotatePositive();
					m_pilot.moveForward();			
				}else if(x>gx){
					m_pilot.rotateNegative();
					m_pilot.moveForward();
				}else if(y<gy){
					m_pilot.rotatePositive();
					m_pilot.rotatePositive();
					m_pilot.moveForward();
				}else if(y>gy){
					m_pilot.moveForward();
				}
			}
			
		
		}
	}
	
	public void setPath(Path _newPath){
		
		this.path = _newPath;
	}
	
	@Override
	public void run() {		
		
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();

		
		PathFinding find=new PathFinding(new SearchCell(startCoordinate), new SearchCell(new Coordinate(1, 3)));
		list=find.aStar();
		
		path = new Path(list);
		
		while(m_running){
			
			followPath(path);
						
		}
	}

	@Override
	public void stop() {
		m_running = false;
		
		
	}
}
