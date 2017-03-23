package warehouseInterface;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import Variables.StartCoordinate;
import lejos.pc.comm.NXTInfo;
import lejos.robotics.RangeFinder;
import mainLoop.Main;
import mainLoop.Main2;
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
	protected static Queue<String> logging_messages = new LinkedList<String>();
	ArrayList<Coordinate> coordinatePath;
	protected static Box loggingContainer;
	protected static JScrollPane scrollPan;
	protected static JTextArea compleatedJobs;
	
	public static int numOfRobots = Main.robots.length;
	//final static Logger logger = Logger.getLogger(Window.class);
	//static final String path = "src/log4j.properties";

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
		//PropertyConfigurator.configure(path);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//widow set up here
		frame = new JFrame("Warehouse Simulator");
		frame.setBounds(100, 100, 1650, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//buttons panel contains all the information and the cancel buttons
		JPanel buttons = new JPanel();
		
		//styling for the information panel
		buttons.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons.setSize(400,800);
		buttons.setBackground(Color.LIGHT_GRAY);
	
		//box to contain the different robots individual information
		Box box = Box.createVerticalBox();
		buttons.add(box);
		
		//adds the labels for the different robots depending on how many robot there currently are
		for (int i = 0 ; i < numOfRobots ; i++){
			robotData.add(new ArrayList<JLabel>());
			robotData.get(i).add(new JLabel("Robot: " + Main.robots[i].name));
			robotData.get(i).add(new JLabel("Position: "));
			robotData.get(i).add(new JLabel("Job: ")); 
			robotData.get(i).add(new JLabel("Reward: "));
			robotData.get(i).add(new JLabel("Items: "));
			//logger.debug("Robot " + i + " JLabels has been created");
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
			JButton cancelButton = new JButton("Cancel: " + Main.robots[count].name);
			cancelButton.addActionListener(new ButtonPressed());
			boxHolder.add(cancelButton);
			count++;
			box.add(new JLabel(" "));
			box.add(new JLabel(" "));
			box.add(dataHolder);
			dataHolder.add(boxHolder);
			//logger.debug("Robot " + (count-1) + " labels and buttons added to the screen");
		}
				
		//added panel for the jobs that have been completed
		JPanel dataHolder = new JPanel();
		dataHolder.setBorder(BorderFactory.createLineBorder(Color.black));
		Box boxHolder = Box.createVerticalBox();
		JLabel jobsCompletedtext = new JLabel("Jobs Completed: ");
		compleatedJobs = new JTextArea("[]");
		compleatedJobs.setLineWrap(true);
		Color backgroundC = new Color(238,238,238);
		compleatedJobs.setBackground(backgroundC);
		boxHolder.add(jobsCompletedtext);
		JScrollPane scrollPane = new JScrollPane(compleatedJobs);
		scrollPane.setPreferredSize(new Dimension(180, 100));
		boxHolder.add(scrollPane);
		dataHolder.add(boxHolder);
		box.add(new JLabel(" "));
		box.add(new JLabel(" "));
		buttons.add(dataHolder);
			
		//this places the map on in the panel and on the frame/window
		JLayeredPane grid = new JLayeredPane();
		grid.setLayout(new FlowLayout());
		JPanel mapPanel = new JPanel();
		mapPanel.setLayout(new BorderLayout());
		mapPanel.add(mapCreate());
		mapPanel.setPreferredSize(new Dimension(1010, 700));
		
		grid.add(mapPanel, new Integer(2),1);	
		//grid.add(new mapVisPanel(gridMap, gridMap), new Integer(1),0);

		
		
		// Add visualisation to frame

		buttons.setPreferredSize(new Dimension(290, 700));
		
		
		frame.setLayout(new FlowLayout());
		frame.getContentPane().add(buttons);
		frame.getContentPane().add(grid);
		frame.getContentPane().add(LoggingPanel());
		frame.setVisible(true);
	}
	
	//TODO add some methods for changing and adding robots with there coordinates etc
	//need to know how coordinates are going to be passed in and how to get the data of the jobs and 
	//the items for each of the rewards

	
	public JComponent mapCreate(){
		GridMap map = MapUtils.createRealWarehouse();

		MapBasedSimulation sim = new MapBasedSimulation(map);

		// Add a robot of a given configuration to the simulation. The return
		// value is the object you can use to control the robot. //
		Coordinate startCoordinateR1 = StartCoordinate.STARTCOORDINATEA;
		Coordinate startCoordinateR2 = StartCoordinate.STARTCOORDINATEB;
		Coordinate startCoordinateR3 = StartCoordinate.STARTCOORDINATEC;
		
		//start pose of the robot this can be changed on the above line
		GridPose gridStartR1 = new GridPose(startCoordinateR1.getX(), startCoordinateR1.getY(), Heading.PLUS_X);
		GridPose gridStartR2 = new GridPose(startCoordinateR2.getX(), startCoordinateR2.getY(), Heading.PLUS_X);
		GridPose gridStartR3 = new GridPose(startCoordinateR3.getX(), startCoordinateR3.getY(), Heading.PLUS_X);
		
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
			
			//logger.debug("robot " + i + " placed on the screen and threads started");
			//starts the robot and the label updater		
			new Thread(robotControllers.get(i)).start();
			new Thread(new LableUpdater()).start();
		}

		GridMapVisualisation viz = new GridMapVisualisation(map, sim.getMap(), 250f);

		MapVisualisationComponent.populateVisualisation(viz, sim);

		// Add the visualisation to a JFrame to display it
		return viz;
	}
	
	public JPanel LoggingPanel(){
		JPanel pan = new JPanel();
		loggingContainer = Box.createVerticalBox();
		scrollPan = new JScrollPane(loggingContainer);
		pan.setPreferredSize(new Dimension(300, 690));
		pan.setSize(new Dimension(300, 800));
		pan.setBackground(Color.GRAY);
		pan.setBorder(BorderFactory.createLineBorder(Color.black));
		pan.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		JPanel titlePan = new JPanel();
		titlePan.setBackground(Color.GRAY);
		JLabel title = new JLabel("                                       Logger");

		title.setPreferredSize(new Dimension(275, 30));
		title.setForeground(Color.WHITE);
		titlePan.add(title);
		titlePan.setBorder(BorderFactory.createLineBorder(Color.black));
		titlePan.setSize(new Dimension(300, 50));
		pan.add(titlePan);
		
		scrollPan.setPreferredSize(new Dimension(300, 700));
		scrollPan.setSize(new Dimension(300, 700));
		scrollPan.getViewport().setBackground(Color.GRAY);
		JScrollBar vertical = scrollPan.getVerticalScrollBar();
		loggingContainer.setBackground(Color.gray);
		pan.add(scrollPan);
		
		new Thread(new LoggingInterfaceUpdater()).start();
		
		return pan;
	}
	
	public static void addCoordinateRobot(Coordinate newCoordinate, String id){
		try{
			getIndex(id).addToQueue(newCoordinate);
			//logger.debug("coordinate " + "(" + newCoordinate.getX() + "," + newCoordinate.getY() + ")" + " added");
			//Window.logMessage("coordinate " + "(" + newCoordinate.getX() + "," + newCoordinate.getY() + ")" + " added");
		} catch (Exception e){System.out.println("There is no robot");}
	}
	
	public static DispRobotController getIndex(String id){
		for (int i = 0; i < robotControllers.size();i++){
			if (id.equals(Main2.robots[i].name)){
				return robotControllers.get(i);
			}
		}
		return robotControllers.get(0);
	}
	
	
	public static void logMessage(String input){
		logging_messages.add(input);
	}
}
