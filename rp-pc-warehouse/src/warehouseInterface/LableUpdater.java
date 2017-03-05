package warehouseInterface;

import rp.systems.StoppableRunnable;
import warehouse.Coordinate;

public class LableUpdater implements StoppableRunnable {

	private boolean m_running = true;

	@Override
	public void run() {
		while (m_running) {
			for (int i = 0; i < Window.robotControllers.size(); i++) {
				Coordinate x = Window.robotControllers.get(i).next;
				if (!(Window.robotControllers.get(i).next == null)) {
					Coordinate current = Window.robotControllers.get(0).next;
					Window.robotData.get(i).get(1).setText("Position: " + current.getX() + "," + current.getY());
				}
			}
		}

	}

	@Override
	public void stop() {
		m_running = false;
	}

}
