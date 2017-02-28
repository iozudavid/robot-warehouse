

import javax.swing.JFrame;

import lejos.robotics.RangeFinder;
import rp.robotics.MobileRobotWrapper;
import rp.robotics.example.ExampleGridMover;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MapBasedSimulation;
import rp.robotics.simulation.MovableRobot;
import rp.robotics.simulation.SimulatedRobots;
import rp.robotics.visualisation.GridMapVisualisation;
import rp.robotics.visualisation.KillMeNow;
import rp.robotics.visualisation.MapVisualisationComponent;

public class GridMover {
	
	public void warehouseMap() {

//		GridMap map = TestMaps.warehouseMap();
		GridMap map = MapUtils.createRealWarehouse();

		// Create the simulation using the given map. This simulation can run
		// without a GUI.
		MapBasedSimulation sim = new MapBasedSimulation(map);

		// Add a robot of a given configuration to the simulation. The return
		// value is the object you can use to control the robot. //

		int robots = 1;
		for (int i = 0; i < robots; i++) {
			// Starting point on the grid
			GridPose gridStart = new GridPose(3 * i, 0, Heading.PLUS_Y);

			MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(
					SimulatedRobots.makeConfiguration(false, true),
					map.toPose(gridStart));

			RangeFinder ranger = sim.getRanger(wrapper);

			Controller controller = new Controller(wrapper.getRobot(),
					map, gridStart, ranger);

			new Thread(controller).start();
		}

		GridMapVisualisation viz = new GridMapVisualisation(map, sim.getMap());

		MapVisualisationComponent.populateVisualisation(viz, sim);

		// Add the visualisation to a JFrame to display it
		displayVisualisation(viz);
	}
	
	public static JFrame displayVisualisation(MapVisualisationComponent viz) {
		// Create a frame to contain the viewer
		JFrame frame = new JFrame("Simulation Viewer");

		// Add visualisation to frame
		frame.add(viz);
		frame.addWindowListener(new KillMeNow());

		frame.pack();
		frame.setSize(viz.getMinimumSize());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		return frame;
	}

	public static void main(String[] args) {
		GridMover demo = new GridMover();
		demo.warehouseMap();
	}

}
