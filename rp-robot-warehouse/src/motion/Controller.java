package motion;
import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
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
	private boolean receivedSTOP;
	
	
	public void setReceivedSTOP(boolean b){
		
		this.receivedSTOP = b;
	}
	
	public Controller(WheeledRobotConfiguration _robot, SensorPort _port, Coordinate  startCoord, SensorPort _port2, SensorPort _port3){
		
		robot = new WheeledRobotSystem(_robot);
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
		r.sendCoordinate(cord);
	}
	
	public void updateHeading(String heading){
		
		if((currentHeading.equals("plusX")&&heading.equals("minusY"))||
			(currentHeading.equals("minusY")&&heading.equals("minusX"))||
			(currentHeading.equals("minusX")&&heading.equals("plusY")) ||
			(currentHeading.equals("plusY")&&heading.equals("plusX"))){
			
			pilot.rotate(-90);
			currentHeading = heading;
		}else if(currentHeading.equals(heading)){
			Delay.msDelay(1800);
			
		}else{
			
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
	}
	
	public void setNewPath(Path newPath){
		
		currentPath = newPath;
		executing = true;
	
	}
	
	public void moveForward(){
		
		
		pilot.forward();
		pilot.setTravelSpeed(0.15f);
		while(true){

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
	
	
	public void followPath(Path path){
		
		
		System.out.println("Executing job");
		while(!path.reachedEnd() && !receivedSTOP){
			
			int x = this.getX();
			int y = this.getY();
			
			
			Coordinate next = path.getNextCoord();
			System.out.println(next.getX()+" "+next.getY());
			//updatePosition(next);
			
			int gx = next.getX();
			int gy = next.getY();
			
			if(next.equals(currentPosition)){
				
				Delay.msDelay(2000);
			}
			
			if(gx == x+1){//if(x < gx){
				updateHeading("plusX");
				moveForward();
				
				
			}else if( x == (gx + 1)){//}else if(x>gx){
				updateHeading("minusX");
				moveForward();
				
			}else if(y+1 == gy){//}else if(y<gy){
				updateHeading("plusY");
				moveForward();
			}else if(gy+1 == y){//}else if(y>gy){
				updateHeading("minusY");
				moveForward();
			}else{
				System.out.println("coordinate out of range");
			}
			updatePosition(next);
		}
		
		if(receivedSTOP){
			pilot.stop();
			return;
		}
		
		if(path.getNumberOFItems() == 0){
			
			System.out.println("Waiting for drop off. Press left button.");
			while(Button.waitForAnyPress() != Button.ID_LEFT){
				System.out.println("Press left button.");
			}
		}else{
		
			int i=0;		
			System.out.println("Waiting for pickup.");
			
			while(i<path.getNumberOFItems()){
				int left = path.getNumberOFItems() - i;
				System.out.println("Items picked up: " + i + " --- " + "Items left to pick up: " + left);
				while(Button.waitForAnyPress() != Button.ID_RIGHT){
					System.out.println("Press right button.");
				}
				i++;
			}
		}
		LCD.clear();
		r.sendMessage("ITEMPICKUP");
		executing = false;
	}

	@Override
	public void run() {
		
		r = new RobotClient();
		r.waitForConnection();
		
		MessageReader reader = new MessageReader(this, r);
		reader.start();
		
		Delay.msDelay(500);
		leftValue=lightleft.readValue();
		rightValue=lightright.readValue();
		
		while(true){

			Path route = r.getPath();
			receivedSTOP = false;
			System.out.println("Path received");
	
			followPath(route);		
				
		}	
	}
		
}
