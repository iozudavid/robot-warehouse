package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class RobotServer {
	private NXTInfo[] nxts;
	private RobotTable robotTable;
	private boolean networksOn = false;
	private Thread[][] threads;

	public RobotServer(NXTInfo[] nxts) {
		this.nxts = nxts;
		this.robotTable = new RobotTable();
	}

	public void connectToNxts() {
		/* For this to work you MUST use a 32bit version of java */
		networksOn = true;
		NXTComm[] connections = new NXTComm[nxts.length];
		threads = new Thread[nxts.length][2];

		try {
			for (int i = 0; i < nxts.length; i++) {
				robotTable.addRobot(nxts[i].name);
				connections[i] = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
				if (connections[i].open(nxts[i])) {
					DataOutputStream toRobot = new DataOutputStream(connections[i].getOutputStream());
					DataInputStream fromRobot = new DataInputStream(connections[i].getInputStream());
					threads[i][0] = new RobotServerReceiver(robotTable, fromRobot);
					threads[i][1] = new RobotServerSender(robotTable.getMessages(nxts[i].name), toRobot);
					threads[i][0].start();
					threads[i][1].start();
					System.out.println("Receiver and Sender for " + nxts[i].name + " started");
				}
			}
		} catch (NXTCommException nxt) {
			System.err.println(nxt);
		}

	}

	public void sendMessage(String nxtName, String msg) {
		robotTable.addMessage(nxtName, msg);
	}

//	Currently a work in progress	
//	public void endNetworkThreads() {
//		try {
//			for (Thread[] threadGroup : threads) {
//				threadGroup[0].join();
//				threadGroup[1].join();
//			}
//		} catch (InterruptedException e) {
//			System.err.println(e);
//		}
//	}
}
