package motion;
import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.RangeFinder;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import networking.RobotClient;
import rp.config.WheeledRobotConfiguration;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;
import warehouse.Coordinate;
import warehouse.Path;

public class Controller extends RobotProgrammingDemo{
	
	private final double cellSize = 0.3;
	private WheeledRobotSystem robot;
	private RangeFinder ranger;
	private Coordinate currentPosition;
	private String currentHeading; // plusX, minusX, plusY, minusY
	private String currentJob;
	private boolean executing;
	private Path currentPath;
	private DifferentialPilot pilot;
	private RobotClient r;
	private LightSensor lightleft;
	private LightSensor lightright;
	private int leftValue;
	private int rightValue;
	
	public Controller(WheeledRobotConfiguration _robot, SensorPort _port, Coordinate  startCoord, SensorPort _port2, SensorPort _port3){
		
		robot = new WheeledRobotSystem(_robot);
		//ranger = new UltrasonicSensor(_port);
		pilot = robot.getPilot();
		currentPosition = startCoord;
		currentHeading = "plusX";
		pilot.setTravelSpeed(0.15f);
		lightleft=new LightSensor(_port2);
		lightleft.setFloodlight(true);
		lightright=new LightSensor(_port3);
		lightright.setFloodlight(true);
	}
	
	public void updatePosition(Coordinate cord){
		
		currentPosition = cord;
		//r.sendCoordinate(cord);
	}
	
	public void updateHeading(String heading){
		
		while(!heading.equals(currentHeading)){	
			
			
			switch (currentHeading) {
			case "plusX":
				pilot.rotate(90);
				currentHeading = "plusY";
				break;
			case "plusY":
				//pilot.rotateLeft();
				
				pilot.rotate(90);
				currentHeading = "minusX";
				break;
			case "minusX":
				//pilot.rotateLeft();
				pilot.rotate(90);
				currentHeading = "minusY";
				break;
			case "minusY":
				//pilot.rotateLeft();
				pilot.rotate(90);
				currentHeading = "plusX";
				break;

			default:
				break;
			}
		}		
		
	}
	
	public void setNewPath(Path newPath){
		
		currentPath = newPath;
		executing = true;
	
	}
	
	public void moveForward(){
		
		
		pilot.forward();
		pilot.setTravelSpeed(0.15f);
		while(true){
			System.out.println(lightright.readValue());
			System.out.println(lightright.readValue());
			if(Math.abs(leftValue-lightleft.readValue())>5){
		
				pilot.rotateLeft();
				pilot.setTravelSpeed(pilot.getMaxTravelSpeed()*0.5f);
			}
			
			if(Math.abs(rightValue-lightright.readValue())>5){
		
				pilot.rotateRight();
				pilot.setTravelSpeed(pilot.getMaxTravelSpeed()*0.5f);
			}
			if(rightValue-lightright.readValue()>5 && leftValue-lightleft.readValue()>5){
				pilot.travel(0.07f);
			
				break;
			}
			if(rightValue-lightright.readValue()<5 && leftValue-lightleft.readValue()<5){
				
				pilot.setTravelSpeed(0.15f);
				pilot.forward();
			}
		}
		
	}
	
	public int getX(){
		
		return currentPosition.getX();
	}
	
	public int getY(){
		
		return currentPosition.getY();
	}
	
	
	public void followPath(Path path, String itemType, int itemNum){
		
		ArrayList<String> directions = new ArrayList<String>();
		
		System.out.println("Executing job " + "NAME");
		while(!path.reachedEnd()){
			
			int x = this.getX();
			int y = this.getY();
			
			Coordinate next = path.getNextCoord();
			
			int gx = next.getX();
			int gy = next.getY();
			
			if(x < gx){
				
				updateHeading("plusX");
				moveForward();
				
				
			}else if(x>gx){
	
				updateHeading("minusX");
				moveForward();
				
			}else if(y<gy){
				
				updateHeading("plusY");
				moveForward();
			}else if(y>gy){
				
				updateHeading("minusY");
				moveForward();
			}
			updatePosition(next);
		}
		
		int i=0;		
		System.out.println("Waiting for pickup. Item name: " + itemType + ".");
		
		while(i!=itemNum){
			int left = itemNum - i;
			System.out.println("Items picked up: " + i + " --- " + "Items left to pick up: " + left);
			while(Button.waitForAnyPress() != Button.ID_RIGHT){
				System.out.println("Press right button.");
			}
			i++;
		}
		
		executing = false;
	}

	//@Override
	public void run1() {
		
		r = new RobotClient();
		r.waitForConnection();
		ArrayList<Coordinate> routeList = new ArrayList<Coordinate>();
		while(true){
			
			
			for(int i = 0; i<5; i--){
				Coordinate nextCoord = r.getCoordinate();
				routeList.add(nextCoord);
			}
			
			Path route = new Path(routeList); 
			followPath(route, "a", 4);		
				
			
		}
		
		
	}
	
	@Override
	public void run() {
		
		Delay.msDelay(500);
		leftValue=lightleft.readValue();
		rightValue=lightright.readValue();
		System.out.println(leftValue);
		System.out.println(rightValue);
		ArrayList<Coordinate> routeList = new ArrayList<Coordinate>();

				
				routeList.add(new Coordinate(0, 1));
				routeList.add(new Coordinate(0, 2));
				routeList.add(new Coordinate(0, 3));
				routeList.add(new Coordinate(0, 4));
				routeList.add(new Coordinate(0, 5));
				routeList.add(new Coordinate(1, 5));
				routeList.add(new Coordinate(2, 5));
			
			
			Path route = new Path(routeList); 
			followPath(route, "a", 4);		
				
			//pilot.travel(0.3);
		
		
	}

	
}
