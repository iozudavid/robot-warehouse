package warehouseInterface;


import java.awt.BorderLayout;
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

import lejos.robotics.RangeFinder;
import rp.robotics.MobileRobotWrapper;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.navigation.GridPose;
import rp.robotics.navigation.Heading;
import rp.robotics.simulation.MapBasedSimulation;
import rp.robotics.simulation.MovableRobot;
import rp.robotics.simulation.SimulatedRobots;
import rp.robotics.visualisation.GridMapVisualisation;
import rp.robotics.visualisation.MapVisualisationComponent;
import warehouse.Coordinate;

public class Window {

	public JFrame frame;
	public static ArrayList<ArrayList<JLabel>> robotData = new ArrayList<ArrayList<JLabel>>();
	protected static ArrayList<DispRobotController> robotControllers = new ArrayList<DispRobotController>();
	ArrayList<Coordinate> coordinatePath;
	
	protected static String[] robotName = {"A", "B", "C", "D"};
	public static int numOfRobots = 1;

	/**
	 * Launch the application.
	 */
	
	/*
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
	
	*/

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
			robotData.get(i).add(new JLabel("Position: "));
			robotData.get(i).add(new JLabel("job: "));      //this needs to take the information for the jobs and the reward
			robotData.get(i).add(new JLabel("Reward: "));
		}
		
		
		//this puts all the labels onto the window the number of robots can be changed up to
		//4 it is not possible to add more than 3 robot descriptions
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
		Coordinate startCoordinateR1 = new Coordinate(0, 0);
		Coordinate startCoordinateR2 = new Coordinate(1, 0);
		Coordinate startCoordinateR3 = new Coordinate(2, 0);
		
		//start pose of the robot this can be changed on the above line
		GridPose gridStartR1 = new GridPose(startCoordinateR1.getX(), startCoordinateR1.getY(), Heading.PLUS_Y);
		GridPose gridStartR2 = new GridPose(startCoordinateR2.getX(), startCoordinateR1.getY(), Heading.PLUS_Y);
		GridPose gridStartR3 = new GridPose(startCoordinateR3.getX(), startCoordinateR1.getY(), Heading.PLUS_Y);
		
		ArrayList<GridPose> GridPoseStartPositions = new ArrayList<GridPose>();
		GridPoseStartPositions.add(gridStartR1);
		GridPoseStartPositions.add(gridStartR2);
		GridPoseStartPositions.add(gridStartR3);

		for (int i = 0; i < numOfRobots; i++) {
			// Starting point on the grid
			
			//start pose of the robot this can be changed on the above line

			//creates the mobileRobot
			MobileRobotWrapper<MovableRobot> wrapper = sim.addRobot(
					SimulatedRobots.makeConfiguration(false, true),
					map.toPose(GridPoseStartPositions.get(i)));

			//adds a ranger not really needed in this situation
			RangeFinder ranger = sim.getRanger(wrapper);

			//adds the robot to an array to be accessed later
			robotControllers.add(new DispRobotController(wrapper.getRobot(), map, GridPoseStartPositions.get(i), ranger, startCoordinateR1));
			
			//starts the robot and the label updater		
			new Thread(robotControllers.get(i)).start();
			new Thread(new LableUpdater()).start();;
		}

		GridMapVisualisation viz = new GridMapVisualisation(map, sim.getMap(), 250f);

		MapVisualisationComponent.populateVisualisation(viz, sim);

		// Add the visualisation to a JFrame to display it
		return viz;
	}
	
	public static void addCoordinateRobotA(Coordinate newCoordinate){
		robotControllers.get(0).addToQueue(newCoordinate);
	}
}
