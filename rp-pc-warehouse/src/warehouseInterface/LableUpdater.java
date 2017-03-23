package warehouseInterface;

import java.util.ArrayList;

import jobPackage.SingleRobotJobAssignment;
import lejos.pc.comm.NXTInfo;
import mainLoop.Main;
import mainLoop.Main2;
import rp.systems.StoppableRunnable;

public class LableUpdater implements StoppableRunnable {

	private boolean m_running = true;
	private Float currentReward = 0f;
	private Float totalReward = 0f;

	@Override
	public void run() {
		try {
			NXTInfo[] robots = Main2.robots;
			String compleatedJobsSoFar = Main2.jobs.getCompleatedJobs().toString();
			while (m_running) {

				for (int i = 0; i < Window.robotControllers.size(); i++) {				
					Window.robotData.get(i).get(1)
							.setText("Position: " + Window.robotControllers.get(i).getCurrentLocation().getX() + ","
									+ Window.robotControllers.get(i).getCurrentLocation().getY());
					Window.robotData.get(i).get(2)
							.setText("Job: " + Main2.jobs.getRobotJobAssignment(robots[i].name).getJobName());
					Window.robotData.get(i).get(3).setText("Reward: " + Main2.jobs.getRobotJobAssignment(robots[i].name).getReward());
					Window.robotData.get(i).get(4)
							.setText("Next items: " + Main2.jobs.getRobotJobAssignment(robots[i].name).items().toString());

					Thread.sleep(40);
				}
				
				if (!(compleatedJobsSoFar == Main2.jobs.getCompleatedJobs().toString())){
					compleatedJobsSoFar = Main2.jobs.getCompleatedJobs().toString();
					Window.compleatedJobs.setText(Main2.jobs.getCompleatedJobs().toString());
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Thread error in the label updater");
			m_running = false;
		} catch (NullPointerException e) {
			System.out.println("Main method is not running");
			m_running = false;
		}
	}

	@Override
	public void stop() {
		m_running = false;
	}

}
