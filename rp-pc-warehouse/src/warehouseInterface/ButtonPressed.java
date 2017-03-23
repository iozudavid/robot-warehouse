package warehouseInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mainLoop.Main;
import mainLoop.Main2;

public class ButtonPressed implements ActionListener{

	//this will be run when the different cancel buttons are pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().substring(8).equals(Main2.robots[0].name)){
			Main2.jobs.getRobotJobAssignment(Main.robots[0].name).cancelJob();
		} else if (e.getActionCommand().substring(8).equals(Main2.robots[1].name)){
			Main2.jobs.getRobotJobAssignment(Main.robots[1].name).cancelJob();
		} else if (e.getActionCommand().substring(8).equals(Main2.robots[2].name)){
			Main2.jobs.getRobotJobAssignment(Main.robots[2].name).cancelJob();
		}
	}
	
}
