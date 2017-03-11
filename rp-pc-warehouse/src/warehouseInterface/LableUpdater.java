package warehouseInterface;

import mainLoop.Main;
import rp.systems.StoppableRunnable;

public class LableUpdater implements StoppableRunnable {

	private boolean m_running = true;

	@Override
	public void run() {
		while (m_running) {
			for (int i = 0; i < Window.robotControllers.size(); i++) {
				Window.robotData.get(i).get(1).setText("Position: " + Window.robotControllers.get(i).getCurrentLocation().getX() + "," + Window.robotControllers.get(i).getCurrentLocation().getY());
				Window.robotData.get(i).get(2).setText("Job: " + Main.jobs.getJobName());
				Window.robotData.get(i).get(2).setText("Reward: " + Main.jobs.getReward());
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				System.out.println("Thread error in the label updater");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stop() {
		m_running = false;
	}

}
