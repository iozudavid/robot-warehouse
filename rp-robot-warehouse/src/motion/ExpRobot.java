package motion;

import Variables.StartCoordinate;
import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import warehouse.Coordinate;

public class ExpRobot {
	
	public static void main(String[] args){
		
		Button.waitForAnyPress();
		RobotProgrammingDemo robot = new Controller(RobotConfigs.EXPRESS_BOT, SensorPort.S3, StartCoordinate.STARTCOORDINATEA, SensorPort.S1, SensorPort.S4);
		robot.run();
		
	}

}
