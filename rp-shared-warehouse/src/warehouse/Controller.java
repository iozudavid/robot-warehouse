package warehouse;



import java.util.ArrayList;

import javax.swing.plaf.ButtonUI;

import lejos.nxt.Button;
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
	
	private Path path;
	private String currentJob;
	
	
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

	public void followPath(Path path){
		
		while(!path.reachedEnd()){
			
			int x = m_pilot.getGridPose().getX();
			int y = m_pilot.getGridPose().getY();
			
			Coordinate next = path.getNextCoord();
			
			int gx = next.getX();
			int gy = next.getY();
			
			if(x < gx){
				
				m_pilot.setGridPose(new GridPose(x, y, Heading.PLUS_X));
				m_pilot.moveForward();
			}else if(x>gx){
				m_pilot.setGridPose(new GridPose(x, y, Heading.MINUS_X));
				m_pilot.moveForward();
				
			}else if(y<gy){
				
				m_pilot.setGridPose(new GridPose(x, y, Heading.PLUS_Y));
				m_pilot.moveForward();
			}else if(y>gy){
				
				m_pilot.setGridPose(new GridPose(x, y, Heading.MINUS_Y));
				m_pilot.moveForward();
			}
		}
	}
	
	public void setPath(Path _newPath){
		
		this.path = _newPath;
	}
	
	@Override
	public void run() {		
		
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		
	/*	list.add(new Coordinate(1, 0));
		list.add(new Coordinate(2,0));
		list.add(new Coordinate(3, 0));
		list.add(new Coordinate(4, 0));*/
		
		PathFinding find=new PathFinding(new SearchCell(new Coordinate(0, 0)), new SearchCell(new Coordinate(7, 5)));
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