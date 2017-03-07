package warehouseInterface;

import rp.systems.StoppableRunnable;
import warehouse.Coordinate;

public class LableUpdater implements StoppableRunnable {

	private boolean m_running = true;

	@Override
	public void run() {
		while (m_running) {
			for (int i = 0; i < Window.robotControllers.size(); i++) {
				Window.robotData.get(i).get(1).setText("Position: " + Window.robotControllers.get(i).getCurrentLocation().getX() + "," + Window.robotControllers.get(i).getCurrentLocation().getY());
			}
		}
	}

	@Override
	public void stop() {
		m_running = false;
	}

}
