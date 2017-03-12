package warehouseInterface;

import mainLoop.Main;
import rp.systems.StoppableRunnable;

public class LableUpdater implements StoppableRunnable {

	private boolean m_running = true;
	private Float currentReward = 0f;
	private Float totalReward = 0f;

	@Override
	public void run() {
		while (m_running) {
			for (int i = 0; i < Window.robotControllers.size(); i++) {
				try {
					currentReward = Main.jobs.getReward();
					
					if (!(currentReward == Main.jobs.getReward())) {
						totalReward = totalReward + currentReward;
						currentReward = Main.jobs.getReward();
					}
					Window.robotData.get(i).get(1)
							.setText("Position: " + Window.robotControllers.get(i).getCurrentLocation().getX() + ","
									+ Window.robotControllers.get(i).getCurrentLocation().getY());
					Window.robotData.get(i).get(2).setText("Job: " + Main.jobs.getJobName());
					Window.robotData.get(i).get(3).setText("Reward: " + currentReward);
					Window.robotData.get(i).get(4).setText("Total Reward: " + totalReward);
					Thread.sleep(20);
				} catch (InterruptedException e) {
					System.out.println("Thread error in the label updater");
					m_running = false;
				} catch (NullPointerException e){
					System.out.println("main loop not started label updater cant work");
					m_running = false;
				}
			}
		}
	}

	@Override
	public void stop() {
		m_running = false;
	}

}
