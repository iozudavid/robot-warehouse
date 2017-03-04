package warehouseInterface;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import lejos.nxt.Inbox;
import lejos.robotics.RangeFinder;
import lejos.robotics.navigation.Pose;
import rp.config.WheeledRobotConfiguration;
import rp.robotics.DifferentialDriveRobot;
import rp.robotics.MobileRobotWrapper;
import rp.robotics.control.RandomGridWalk;
import rp.robotics.control.RandomWalkController;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MapBasedSimulation;
import rp.robotics.simulation.MovableRobot;
import rp.robotics.simulation.SimulatedRobots;
import rp.robotics.testing.TestMaps;
import rp.robotics.visualisation.GridMapVisualisation;
import rp.robotics.visualisation.MapVisualisationComponent;

public class Window {

	private JFrame frame;
	public static ArrayList<ArrayList<JLabel>> robotData = new ArrayList<ArrayList<JLabel>>();
	ArrayList<DispRobotController> robotControllers = new ArrayList<DispRobotController>();
	protected static String[] robotName = {"A", "B", "C", "D"};
	public static int numOfRobots = 3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//widow set up here
		frame = new JFrame();
		frame.setBounds(100, 100, 1350, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//buttons panel contains all the information and the cancel buttons
		JPanel buttons = new JPanel();
		
		//styling for the information panel
		buttons.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons.setSize(200,800);
		buttons.setBackground(Color.LIGHT_GRAY);
	
		//box to contain the different robots individual information
		Box box = Box.createVerticalBox();
		buttons.add(box);
		
		//adds the labels for the different robots depending on how many robot there currently are
		for (int i = 0 ; i < numOfRobots ; i++){
			robotData.add(new ArrayList<JLabel>());
			robotData.get(i).add(new JLabel("Robot: " + robotName[i]));
			robotData.get(i).add(new JLabel("Position: " + numOfRobots));
			robotData.get(i).add(new JLabel("job: "));      //this needs to take the information for the jobs and the reward
			robotData.get(i).add(new JLabel("Reward: "));
		}
		
		
		//this puts all the labels onto the window the number of robots can be changed up to
		//4 it is not possable to add more than 3 robot descriptions
		int count = 0;
		for (ArrayList<JLabel> robot : robotData){
			JPanel dataHolder = new JPanel();
			dataHolder.setBorder(BorderFactory.createLineBorder(Color.black));
			
			Box boxHolder = Box.createVerticalBox();
			for (JLabel label : robot){
				boxHolder.add(label);
			}
			JButton cancelButton = new JButton("Cancel: " + robotName[count]);
			cancelButton.addActionListener(new ButtonPressed());
			boxHolder.add(cancelButton);
			count++;
			box.add(new JLabel(" "));
			box.add(new JLabel(" "));
			box.add(dataHolder);
			dataHolder.add(boxHolder);
		}

			
		//this places the map on in the panel and on the frame/window
		JLayeredPane grid = new JLayeredPane();
		grid.setLayout(new FlowLayout());
		JPanel mapPanel = new JPanel();
		mapPanel.setLayout(new BorderLayout());
		mapPanel.add(mapCreate());
		mapPanel.setPreferredSize(new Dimension(1100, 700));
		
		grid.add(mapPanel, new Integer(2),1);	
		//grid.add(new mapVisPanel(gridMap, gridMap), new Integer(1),0);

		
		
		// Add visualisation to frame

		buttons.setPreferredSize(new Dimension(150, 700));
		
		
		frame.setLayout(new FlowLayout());
		frame.getContentPane().add(buttons);
		frame.getContentPane().add(grid);
		//frame.getContentPane().add(new mapVisPanel(gridMap, gridMap));
		frame.setVisible(true);
	}
	
	//TODO add some methods for changing and adding robots with there coordinates etc
	//need to know how coordinates are going to be passed in and how to get the data of the jobs and 
	//the items for each of the rewards

	
	public JComponent mapCreate(){
		GridMap map = MapUtils.createMarkingWarehouseMap();

		MapBasedSimulation sim = new MapBasedSimulation(map);

		// Add a robot of a given configuration to the simulation. The return
		// value is the object you can use to control the robot. //

		for (int i = 0; i < numOfRobots; i++) {
			// Starting point on the grid
			
			GridPose gridStart = new GridPose(3 * i, 0, Heading.PLUS_Y);

			MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(
					SimulatedRobots.makeConfiguration(false, true),
					map.toPose(gridStart));

			RangeFinder ranger = sim.getRanger(wrapper);

			robotControllers.add(new DispRobotController(wrapper.getRobot(), map, gridStart, ranger));

			robotControllers.get(i).addCoordinate(4, 1);
			robotControllers.get(i).addCoordinate(4, 1);
			robotControllers.get(i).addCoordinate(4, 1);
			
			new Thread(robotControllers.get(i)).start();
		}

		GridMapVisualisation viz = new GridMapVisualisation(map, sim.getMap(), 250f);

		MapVisualisationComponent.populateVisualisation(viz, sim);

		// Add the visualisation to a JFrame to display it
		return viz;
	}
}
