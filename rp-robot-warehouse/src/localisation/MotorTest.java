package localisation;

import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.FileReader;
import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
//import lejos.nxt.Sound;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.RobotConfigs;
import rp.config.WheeledRobotConfiguration;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;
import rp.util.Rate;

public class MotorTest extends RobotProgrammingDemo {
	static BufferedReader inputFile1 = null;
	static String line1 = "";
	static TreeNode[][] nod = new TreeNode[15][15];
	static locCoordinate[][] cor = new locCoordinate[15][15];

	private static WheeledRobotSystem robot;
	private static LightSensor lightleft;
	private static LightSensor lightright;
	private static int leftValue;
	private static int rightValue;
	private static DifferentialPilot pilot;

	public MotorTest(WheeledRobotConfiguration _config) {
		this.robot = new WheeledRobotSystem(_config);
	}

	public static locCoordinate[][] setCord() {
		locCoordinate[][] cord = new locCoordinate[15][15];

		cord[0][0] = new locCoordinate(0, 0, 3, 3, 0, 0);
		cord[0][1] = new locCoordinate(0, 1, 3, 0, 1, 0);
		cord[0][2] = new locCoordinate(0, 2, 3, 0, 2, 0);
		cord[0][3] = new locCoordinate(0, 3, 3, 0, 3, 0);
		cord[0][4] = new locCoordinate(0, 4, 3, 0, 3, 0);
		cord[0][5] = new locCoordinate(0, 5, 2, 0, 3, 0);
		cord[0][6] = new locCoordinate(0, 6, 1, 3, 3, 0);
		cord[0][7] = new locCoordinate(0, 7, 0, 3, 3, 0);

		cord[1][0] = new locCoordinate(1, 0, 0, 3, 0, 1);
		cord[1][1] = new locCoordinate(1, 1, 5, 5, 5, 5);
		cord[1][2] = new locCoordinate(1, 2, 5, 5, 5, 5);
		cord[1][3] = new locCoordinate(1, 3, 5, 5, 5, 5);
		cord[1][4] = new locCoordinate(1, 4, 5, 5, 5, 5);
		cord[1][5] = new locCoordinate(1, 5, 5, 5, 5, 5);
		cord[1][6] = new locCoordinate(1, 6, 1, 3, 0, 1);
		cord[1][7] = new locCoordinate(1, 7, 0, 3, 1, 1);

		cord[2][0] = new locCoordinate(2, 0, 3, 3, 0, 2);
		cord[2][1] = new locCoordinate(2, 1, 3, 1, 1, 0);
		cord[2][2] = new locCoordinate(2, 2, 3, 1, 2, 0);
		cord[2][3] = new locCoordinate(2, 3, 3, 1, 3, 0);
		cord[2][4] = new locCoordinate(2, 4, 3, 1, 3, 0);
		cord[2][5] = new locCoordinate(2, 5, 2, 1, 3, 0);
		cord[2][6] = new locCoordinate(2, 6, 1, 3, 3, 2);
		cord[2][7] = new locCoordinate(2, 7, 0, 3, 3, 2);

		cord[3][0] = new locCoordinate(3, 0, 3, 3, 0, 3);
		cord[3][1] = new locCoordinate(3, 1, 3, 0, 1, 1);
		cord[3][2] = new locCoordinate(3, 2, 3, 0, 2, 1);
		cord[3][3] = new locCoordinate(3, 3, 3, 0, 3, 1);
		cord[3][4] = new locCoordinate(3, 4, 3, 0, 3, 1);
		cord[3][5] = new locCoordinate(3, 5, 2, 0, 3, 1);
		cord[3][6] = new locCoordinate(3, 6, 1, 3, 3, 3);
		cord[3][7] = new locCoordinate(3, 7, 0, 3, 3, 3);

		cord[4][0] = new locCoordinate(4, 0, 0, 3, 0, 3);
		cord[4][1] = new locCoordinate(4, 1, 5, 5, 5, 5);
		cord[4][2] = new locCoordinate(4, 2, 5, 5, 5, 5);
		cord[4][3] = new locCoordinate(4, 3, 5, 5, 5, 5);
		cord[4][4] = new locCoordinate(4, 4, 5, 5, 5, 5);
		cord[4][5] = new locCoordinate(4, 5, 5, 5, 5, 5);
		cord[4][6] = new locCoordinate(4, 6, 1, 3, 0, 3);
		cord[4][7] = new locCoordinate(4, 7, 0, 3, 1, 3);

		cord[5][0] = new locCoordinate(5, 0, 3, 3, 0, 3);
		cord[5][1] = new locCoordinate(5, 1, 3, 1, 1, 0);
		cord[5][2] = new locCoordinate(5, 2, 3, 1, 2, 0);
		cord[5][3] = new locCoordinate(5, 3, 3, 1, 3, 0);
		cord[5][4] = new locCoordinate(5, 4, 3, 1, 3, 0);
		cord[5][5] = new locCoordinate(5, 5, 2, 1, 3, 0);
		cord[5][6] = new locCoordinate(5, 6, 1, 3, 3, 3);
		cord[5][7] = new locCoordinate(5, 7, 0, 3, 3, 3);

		cord[6][0] = new locCoordinate(6, 0, 3, 3, 0, 3);
		cord[6][1] = new locCoordinate(6, 1, 3, 0, 1, 1);
		cord[6][2] = new locCoordinate(6, 2, 3, 0, 2, 1);
		cord[6][3] = new locCoordinate(6, 3, 3, 0, 3, 1);
		cord[6][4] = new locCoordinate(6, 4, 3, 0, 3, 1);
		cord[6][5] = new locCoordinate(6, 5, 2, 0, 3, 1);
		cord[6][6] = new locCoordinate(6, 6, 1, 3, 3, 3);
		cord[6][7] = new locCoordinate(6, 7, 0, 3, 3, 3);

		cord[7][0] = new locCoordinate(7, 0, 0, 3, 0, 3);
		cord[7][1] = new locCoordinate(7, 1, 5, 5, 5, 5);
		cord[7][2] = new locCoordinate(7, 2, 5, 5, 5, 5);
		cord[7][3] = new locCoordinate(7, 3, 5, 5, 5, 5);
		cord[7][4] = new locCoordinate(7, 4, 5, 5, 5, 5);
		cord[7][5] = new locCoordinate(7, 5, 5, 5, 5, 5);
		cord[7][6] = new locCoordinate(7, 6, 1, 3, 0, 3);
		cord[7][7] = new locCoordinate(7, 7, 0, 3, 1, 3);

		cord[8][0] = new locCoordinate(8, 0, 3, 3, 0, 3);
		cord[8][1] = new locCoordinate(8, 1, 3, 1, 1, 0);
		cord[8][2] = new locCoordinate(8, 2, 3, 1, 2, 0);
		cord[8][3] = new locCoordinate(8, 3, 3, 1, 3, 0);
		cord[8][4] = new locCoordinate(8, 4, 3, 1, 3, 0);
		cord[8][5] = new locCoordinate(8, 5, 2, 1, 3, 0);
		cord[8][6] = new locCoordinate(8, 6, 1, 3, 3, 3);
		cord[8][7] = new locCoordinate(8, 7, 0, 3, 3, 3);

		cord[9][0] = new locCoordinate(9, 0, 3, 2, 0, 3);
		cord[9][1] = new locCoordinate(9, 1, 3, 0, 1, 1);
		cord[9][2] = new locCoordinate(9, 2, 3, 0, 2, 1);
		cord[9][3] = new locCoordinate(9, 3, 3, 0, 3, 1);
		cord[9][4] = new locCoordinate(9, 4, 3, 0, 3, 1);
		cord[9][5] = new locCoordinate(9, 5, 2, 0, 3, 1);
		cord[9][6] = new locCoordinate(9, 6, 1, 2, 3, 3);
		cord[9][7] = new locCoordinate(9, 7, 0, 2, 3, 3);

		cord[10][0] = new locCoordinate(10, 0, 0, 1, 0, 3);
		cord[10][1] = new locCoordinate(10, 1, 5, 5, 5, 5);
		cord[10][2] = new locCoordinate(10, 2, 5, 5, 5, 5);
		cord[10][3] = new locCoordinate(10, 3, 5, 5, 5, 5);
		cord[10][4] = new locCoordinate(10, 4, 5, 5, 5, 5);
		cord[10][5] = new locCoordinate(10, 5, 5, 5, 5, 5);
		cord[10][6] = new locCoordinate(10, 6, 1, 1, 0, 3);
		cord[10][7] = new locCoordinate(10, 7, 0, 1, 1, 3);

		cord[11][0] = new locCoordinate(11, 0, 3, 0, 0, 3);
		cord[11][1] = new locCoordinate(11, 1, 3, 0, 1, 0);
		cord[11][2] = new locCoordinate(11, 2, 3, 0, 2, 0);
		cord[11][3] = new locCoordinate(11, 3, 3, 0, 3, 0);
		cord[11][4] = new locCoordinate(11, 4, 3, 0, 3, 0);
		cord[11][5] = new locCoordinate(11, 5, 2, 0, 3, 0);
		cord[11][6] = new locCoordinate(11, 6, 1, 0, 3, 3);
		cord[11][7] = new locCoordinate(11, 7, 0, 0, 3, 3);

		return cord;

	}

	public static void readItem1() {
		locCoordinate[][] cord = setCord();

		for (int x = 0; x < 12; x++) {
			for (int y = 0; y < 8; y++) {
				nod[x][y] = new TreeNode(cord[x][y]);
			}
		}
		nod[0][0].addChild(nod[1][0]);
		nod[0][0].addChild(nod[0][1]);
		nod[11][0].addChild(nod[10][0]);
		nod[11][0].addChild(nod[11][1]);
		nod[11][7].addChild(nod[10][7]);
		nod[11][7].addChild(nod[11][6]);
		nod[0][7].addChild(nod[0][6]);
		nod[0][7].addChild(nod[1][7]);

		nod[0][0].addChildE(nod[1][0]);
		nod[0][0].addChildN(nod[0][1]);
		nod[11][0].addChildW(nod[10][0]);
		nod[11][0].addChildN(nod[11][1]);
		nod[11][7].addChildE(nod[10][7]);
		nod[11][7].addChildS(nod[11][6]);
		nod[0][7].addChildS(nod[0][6]);
		nod[0][7].addChildW(nod[7][1]);

		for (int x = 1; x < 11; x++) {
			nod[x][0].addChild(nod[x - 1][0]);
			nod[x][0].addChild(nod[x][1]);
			nod[x][0].addChild(nod[x + 1][0]);
			nod[x][7].addChild(nod[x - 1][7]);
			nod[x][7].addChild(nod[x][6]);
			nod[x][7].addChild(nod[x + 1][7]);

			nod[x][0].addChildW(nod[x - 1][0]);
			nod[x][0].addChildN(nod[x][1]);
			nod[x][0].addChildE(nod[x + 1][0]);
			nod[x][7].addChildW(nod[x - 1][7]);
			nod[x][7].addChildS(nod[x][6]);
			nod[x][7].addChildE(nod[x + 1][7]);
		}
		for (int y = 1; y < 7; y++) {
			nod[0][y].addChild(nod[0][y - 1]);
			nod[0][y].addChild(nod[1][y]);
			nod[0][y].addChild(nod[0][y + 1]);
			nod[11][y].addChild(nod[11][y + 1]);
			nod[11][y].addChild(nod[10][y]);
			nod[11][y].addChild(nod[11][y - 1]);

			nod[0][y].addChildS(nod[0][y - 1]);
			nod[0][y].addChildE(nod[1][y]);
			nod[0][y].addChildN(nod[0][y + 1]);
			nod[11][y].addChildN(nod[11][y + 1]);
			nod[11][y].addChildW(nod[10][y]);
			nod[11][y].addChildS(nod[11][y - 1]);
		}

		for (int x = 1; x < 11; x++) {
			for (int y = 1; y < 7; y++) {
				nod[x][y].addChild(nod[x + 1][y]);
				nod[x][y].addChild(nod[x - 1][y]);
				nod[x][y].addChild(nod[x][y + 1]);
				nod[x][y].addChild(nod[x][y - 1]);

				nod[x][y].addChildE(nod[x + 1][y]);
				nod[x][y].addChildW(nod[x - 1][y]);
				nod[x][y].addChildN(nod[x][y + 1]);
				nod[x][y].addChildS(nod[x][y - 1]);

			}
		}

	}

	public void run() {

	}

	public static void btn() {
		Button.waitForAnyPress();
	}

	public static void chckB(ArrayList<CordFacing> l) {
		if (l.size() == 1) {
			System.out.println(l.get(0).returnT().returnV().returnXY());
			btn();
		}
		if (l.size() == 0) {
			System.out.println("Tuscias");
			btn();
		}
	}

	public static void moveForward() {

		pilot.forward();
		pilot.setTravelSpeed(0.15f);
		while (true) {
			if (Math.abs(leftValue - lightleft.readValue()) > 5) {

				pilot.rotateLeft();
				pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.5f);
			}

			if (Math.abs(rightValue - lightright.readValue()) > 5) {

				pilot.rotateRight();
				pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.5f);
			}
			if (rightValue - lightright.readValue() > 5
					&& leftValue - lightleft.readValue() > 5) {
				pilot.travel(0.07f);

				break;
			}
			if (rightValue - lightright.readValue() < 5
					&& leftValue - lightleft.readValue() < 5) {

				pilot.setTravelSpeed(0.15f);
				pilot.forward();
			}
		}

	}

	public static void chckFinish(ArrayList<CordFacing> l) {
		if (l.size() == 1) {
			System.out.println("----------");
			System.out.println(l.get(0).returnT().returnV().returnXY());
			btn();
		}
		if (l.size() == 0) {
			System.out.println("Tuscias");
			btn();
		}
	}

	public static void main(String[] args) {

		btn();
		RobotProgrammingDemo demo = new MotorTest(RobotConfigs.EXPRESS_BOT);
		DifferentialPilot pilot = robot.getPilot();
		Rate r = new Rate(2);

		OpticalDistanceSensor sensor = new OpticalDistanceSensor(SensorPort.S2);
		pilot.setTravelSpeed(0.15f);
		lightleft = new LightSensor(SensorPort.S1);
		lightleft.setFloodlight(true);
		lightright = new LightSensor(SensorPort.S4);
		lightright.setFloodlight(true);
		leftValue = lightleft.readValue();
		rightValue = lightright.readValue();
		btn();

		Integer[] nesw = new Integer[4];
		btn();
		Integer nn = 80;
		for (int y = 0; y < 4; y++) {
			// if (nn.equals(80)){
			// / continue;
			// }
			float average = 0f;
			for (int i = 0; i < 80; i++) {
				float range = sensor.getRange();

				average += range;
				Delay.msDelay(5);

			}
			average = average / nn;
			if (average < 30.0f) {
				nesw[y] = 0;
			} else if (average < 55.0f) {
				nesw[y] = 1;
			} else if (average < 80) {
				nesw[y] = 2;
			} else {
				nesw[y] = 3;

			}

			r.sleep();
			pilot.rotate(-91);
			Delay.msDelay(25);

		}
		// / nesw[0]= 2;
		// nesw[1]=1;
		// nesw[2]=3;
		// nesw[3]=0;
		System.out.println(nesw[0] + " " + nesw[1] + " " + nesw[2] + " "
				+ nesw[3]);

		btn();
		cor = setCord();
		boolean m_run = true;

		readItem1();
		// System.out.println(nod[3][7].rChildE().returnV().returnXY() +
		// " cia tas vaikas" );

		ArrayList<CordFacing> listerino = new ArrayList<CordFacing>();
		for (int x = 0; x < 12; x++) {
			for (int y = 0; y < 8; y++) {
				if (cor[x][y].chckN(nesw[0], nesw[1], nesw[2], nesw[3])) {

					listerino.add(new CordFacing(nod[x][y], 0));
				}
				if (cor[x][y].chckE(nesw[0], nesw[1], nesw[2], nesw[3])) {
					listerino.add(new CordFacing(nod[x][y], 1));
				}
				if (cor[x][y].chckS(nesw[0], nesw[1], nesw[2], nesw[3])) {
					listerino.add(new CordFacing(nod[x][y], 2));
				}
				if (cor[x][y].chckW(nesw[0], nesw[1], nesw[2], nesw[3])) {
					listerino.add(new CordFacing(nod[x][y], 3));
				}

			}
		}

		ArrayList<CordFacing> lister = new ArrayList<CordFacing>();
		
		btn();
		for (int oj = 0; oj < 100; oj++) {
			// lister = listerino;
			chckFinish(listerino);
			Float average = 0.0f;
			Delay.msDelay(100);
			for (int i = 0; i < 80; i++) {
				float range = sensor.getRange();

				average += range;
				Delay.msDelay(5);

			}
			average = average / 80;

			if (average < 30.0f) {
				lister.clear();
				lister.addAll(listerino);
				for (CordFacing f : lister) {
					if (f.chckOrientationExp()) {
						listerino.add(f.returnExpandedOrientation());
					}
				}

				for (int y = 0; y < 4; y++) {
					float average1 = 0f;
					Delay.msDelay(100);

					for (int i = 0; i < 80; i++) {
						float range = sensor.getRange();

						average1 += range;
						Delay.msDelay(5);

					}
					average1 = average1 / nn;
					if (average1 < 30.0f) {
						nesw[y] = 0;
					} else if (average1 < 45.0f) {
						nesw[y] = 1;
					} else if (average1 < 75) {
						nesw[y] = 2;
					} else {
						nesw[y] = 3;

					}

					r.sleep();
					pilot.rotate(-94);
					Delay.msDelay(100);

				}

				System.out.println(nesw[0] + " " + nesw[1] + " " + nesw[2]
						+ " " + nesw[3]);
				btn();
				for (CordFacing f : listerino) {
					System.out.println(f.returnT().returnV().returnXY()
							+ " -- " + f.returnO());
				}

				lister.clear();

				for (CordFacing f : listerino) {
					if (f.returnO().equals(0)) {
						if (f.returnT().returnV()
								.chckN(nesw[0], nesw[1], nesw[2], nesw[3])) {
							lister.add(f);
						}
					}
					if (f.returnO().equals(1)) {
						if (f.returnT().returnV()
								.chckE(nesw[0], nesw[1], nesw[2], nesw[3])) {
							lister.add(f);
						}
					}
					if (f.returnO().equals(2)) {
						if (f.returnT().returnV()
								.chckS(nesw[0], nesw[1], nesw[2], nesw[3])) {
							lister.add(f);
						}
					}
					if (f.returnO().equals(3)) {
						if (f.returnT().returnV()
								.chckW(nesw[0], nesw[1], nesw[2], nesw[3])) {
							lister.add(f);
						}
					}
				}
				if (lister.isEmpty()){
					lister.addAll(listerino);
				}
				for (CordFacing f : lister) {
					f.rotateO();
				}
				pilot.rotate(-92);
			
				chckFinish(lister);
				average = 0.f;
				for (int i = 0; i < 80; i++) {
					float range = sensor.getRange();

					average += range;
					Delay.msDelay(5);

				}
				average = average / 80;
				if (average < 30.0f) {

					for (CordFacing f : lister) {
						f.rotateO();
						f.rotateO();
					}
					pilot.rotate(-182);

					// continue;

				}
				
				
				//kodas
				m_run = true;
				while (m_run) {
					pilot.forward();
					pilot.setTravelSpeed(0.15f);

					if (Math.abs(leftValue - lightleft.readValue()) < 20) {

						pilot.rotateLeft();
						pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.5f);
					}

					if (Math.abs(rightValue - lightright.readValue()) < 20) {

						pilot.rotateRight();
						pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.5f);
					}
					if (Math.abs(rightValue - lightright.readValue()) < (20)
							&& Math.abs(leftValue - lightleft.readValue()) < (20)) {
						pilot.travel(0.08f);

						m_run = false;
						break;
					}

					if (rightValue - lightright.readValue() < 5
							&& leftValue - lightleft.readValue() < 5) {

						pilot.setTravelSpeed(0.15f);
						pilot.forward();
					}

				}
				listerino.clear();
				for (CordFacing f : lister) {
					if (f.chckOrientationExp()) {
						listerino.add(f.returnExpandedOrientation());
						System.out.println("Expandinau");
					}
				}
				for (int y = 0; y < 4; y++) {
					float average1 = 0f;
					Delay.msDelay(300);

					for (int i = 0; i < 80; i++) {
						float range = sensor.getRange();

						average1 += range;
						Delay.msDelay(5);

					}
					average1 = average1 / nn;
					if (average1 < 30.0f) {
						nesw[y] = 0;
					} else if (average1 < 45.0f) {
						nesw[y] = 1;
					} else if (average1 < 70) {
						nesw[y] = 2;
					} else {
						nesw[y] = 3;

					}

					r.sleep();
					pilot.rotate(-92);
					Delay.msDelay(100);

				}
				System.out.println(nesw[0] + " " + nesw[1] + " " + nesw[2]
						+ " " + nesw[3]);
				btn();
				lister.clear();

				for (CordFacing f : listerino) {
					if (f.returnO().equals(0)) {
						if (f.returnT().returnV()
								.chckN(nesw[0], nesw[1], nesw[2], nesw[3])) {
							lister.add(f);
						}
					}
					if (f.returnO().equals(1)) {
						if (f.returnT().returnV()
								.chckE(nesw[0], nesw[1], nesw[2], nesw[3])) {
							lister.add(f);
						}
					}
					if (f.returnO().equals(2)) {
						if (f.returnT().returnV()
								.chckS(nesw[0], nesw[1], nesw[2], nesw[3])) {
							lister.add(f);
						}
					}
					if (f.returnO().equals(3)) {
						if (f.returnT().returnV()
								.chckW(nesw[0], nesw[1], nesw[2], nesw[3])) {
							lister.add(f);
						}
					}
				}
				
				System.out.println(lister.size());
				btn();
				
				
				
				
				//kodas
			}
			m_run = true;
			while (m_run) {
				pilot.forward();
				pilot.setTravelSpeed(0.15f);

				if (Math.abs(leftValue - lightleft.readValue()) < 20) {

					pilot.rotateLeft();
					pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.5f);
				}

				if (Math.abs(rightValue - lightright.readValue()) < 20) {

					pilot.rotateRight();
					pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.5f);
				}
				if (Math.abs(rightValue - lightright.readValue()) < (20)
						&& Math.abs(leftValue - lightleft.readValue()) < (20)) {
					pilot.travel(0.08f);

					m_run = false;
					break;
				}

				if (rightValue - lightright.readValue() < 5
						&& leftValue - lightleft.readValue() < 5) {

					pilot.setTravelSpeed(0.15f);
					pilot.forward();
				}

			}
			
			if (lister.isEmpty()) {
				lister.addAll(listerino);
			}
			listerino.clear();
			for (CordFacing f : lister) {
				if (f.chckOrientationExp()) {
					listerino.add(f.returnExpandedOrientation());
					System.out.println("Expandinau");
				}
			}

			System.out.println(listerino.size());
			for (CordFacing f : listerino) {
				System.out.println(f.returnT().returnV().returnXY() + " -- "
						+ f.returnO());

			}

			lister.clear();

		}
		Button.waitForAnyPress();
		demo.run();
	}

}
