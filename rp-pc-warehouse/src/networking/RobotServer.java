package networking;

import java.io.DataInputStream;
import warehouse.Coordinate;
import warehouse.Path;

import java.io.DataOutputStream;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class RobotServer {
	private NXTInfo[] nxts;
	private RobotTable robotTable;
	private Thread[][] threads;

	public RobotServer(NXTInfo[] nxts) {
		this.nxts = nxts;
		this.robotTable = new RobotTable();
	}

	//To be run to connect to nxts specified in the constructor
	//For this to work a 32 bit version of java MUST be used
	public void connectToNxts() {
		NXTComm[] connections = new NXTComm[nxts.length];
		//The running threads are stored in an array to allow for
		//later access to the threads
		threads = new Thread[nxts.length][2];

		try {
			for (int i = 0; i < nxts.length; i++) {
				robotTable.addRobot(nxts[i].name);
				connections[i] = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
				if (connections[i].open(nxts[i])) {
					//If a connection to a nxt has been opened in input and output streams are started
					DataOutputStream toRobot = new DataOutputStream(connections[i].getOutputStream());
					DataInputStream fromRobot = new DataInputStream(connections[i].getInputStream());
					
					//The threads frot that robot are created
					threads[i][0] = new RobotServerReceiver(robotTable, fromRobot,nxts[i].name);
					threads[i][1] = new RobotServerSender(robotTable.getMessages(nxts[i].name), toRobot);
					
					//The threads are started
					threads[i][0].start();
					threads[i][1].start();
					System.out.println("Receiver and Sender for " + nxts[i].name + " started");
				}
			}
		} catch (NXTCommException nxt) {
			System.out.println(nxt);
		}

	}

	//Adds message to the queue for the nxt specified
	public void sendMessage(String nxtName, String msg) {
		robotTable.addMessage(nxtName,new Message("server",msg));
	}
	
	//Adds a coordinate set to be sent to the nxt specified
	public void sendCoordinate(String nxtName,Coordinate c){
		robotTable.addMessage(nxtName, new Message("server",c));
	}
	
	public void sendPath(String nxtName,Path p){
		robotTable.addMessage(nxtName, new Message("server",p));
	}
	
	//Gets top of the message queue (in a message object)
	public Message getReceivedMessage(){
		return robotTable.takeReceivedMessage();
	}
	
	public boolean isReceivedEmpty(){
		return robotTable.isReceivedEmpty();
	}
	
	//Gets the top of the received coordinate queue
	public Message getReceivedCoordinate(){
		return robotTable.takeReceivedCoordinate();
	}
	
	public boolean isCoordinateEmpty(){
		return robotTable.isCoordinateEmpty();
	}
}
