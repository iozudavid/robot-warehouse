package warehouseInterface;

import java.util.ArrayList;

import jobPackage.SingleRobotJobAssignment;
import mainLoop.Main;
import rp.systems.StoppableRunnable;

public class LableUpdater implements StoppableRunnable {

	private boolean m_running = true;
	private Float currentReward = 0f;
	private Float totalReward = 0f;

	@Override
	public void run() {
		while (m_running) {
			try {
				ArrayList<SingleRobotJobAssignment> jobsAssignments = new ArrayList<SingleRobotJobAssignment>();
				jobsAssignments.add(Main.jobs.RobotA);
				try {
					jobsAssignments.add(Main.jobs.RobotB);
					jobsAssignments.add(Main.jobs.RobotC);
				} catch (NullPointerException e) {

				}
				for (int i = 0; i < Window.robotControllers.size(); i++) {
					currentReward = jobsAssignments.get(i).getReward();

					if (!(currentReward == jobsAssignments.get(i).getReward())) {
						totalReward = totalReward + currentReward;
						currentReward = jobsAssignments.get(i).getReward();
					}
					Window.robotData.get(i).get(1)
							.setText("Position: " + Window.robotControllers.get(i).getCurrentLocation().getX() + ","
									+ Window.robotControllers.get(i).getCurrentLocation().getY());
					Window.robotData.get(i).get(2).setText("Job: " + jobsAssignments.get(i).getJobName());
					Window.robotData.get(i).get(3).setText("Reward: " + currentReward);
					Window.robotData.get(i).get(4).setText("Total Reward: " + totalReward);
					Window.robotData.get(i).get(5).setText("Items: " + jobsAssignments.get(i).items().toString());

					Thread.sleep(40);
				}
				Window.compleatedJobs.setText(Main.jobs.getCompleatedJobs().toString());
			} catch (InterruptedException e) {
				System.out.println("Thread error in the label updater");
				m_running = false;
			} catch (NullPointerException e) {
				System.out.println("Main method is not running");
				m_running = false;
			}
		}
	}

	@Override
	public void stop() {
		m_running = false;
	}

}
