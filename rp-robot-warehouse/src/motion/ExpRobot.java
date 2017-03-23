package motion;

import java.io.IOException;

import Variables.StartCoordinate;
import lejos.nxt.Button;
import lejos.nxt.NXT;
import lejos.nxt.SensorPort;
import lejos.nxt.remote.DeviceInfo;
import lejos.nxt.remote.NXTCommand;
import rp.config.RobotConfigs;
import rp.systems.RobotProgrammingDemo;
import warehouse.Coordinate;

public class ExpRobot {
	
	public static void main(String[] args){
		Button.waitForAnyPress();
		RobotProgrammingDemo robot = new Controller(RobotConfigs.EXPRESS_BOT, SensorPort.S3, new Coordinate(0,0), SensorPort.S1, SensorPort.S4);
		robot.run();
	}
	

}
