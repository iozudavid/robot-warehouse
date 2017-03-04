package networking;

import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class networkingTest {

	public static void main(String[] args) {

		String robotName = "NXT";
		String robotAddress = "0016530C73B0";

		NXTInfo[] robots = { new NXTInfo(NXTCommFactory.BLUETOOTH, robotName, robotAddress) };

		RobotServer rs = new RobotServer(robots);
		rs.connectToNxts();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		rs.sendMessage(robotName,"111");
		System.out.println("Message sent");

	}

}
