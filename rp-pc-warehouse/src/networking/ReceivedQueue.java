package networking;

import java.util.concurrent.*;

import warehouse.Coordinate;

/*Originally from martins messaging excercise*/
public class ReceivedQueue {

	private BlockingQueue<String> stringQueue = new LinkedBlockingQueue<String>();
	private BlockingQueue<Coordinate> coordinateQueue = new LinkedBlockingQueue<Coordinate>();
	
	public void offerString(String message) {
		stringQueue.offer(message);
	}

	public String takeString() {
		//Will not return until a message is in the queue
		while (true) {
			try {
				return (stringQueue.take());
			} catch (InterruptedException e) {
				System.err.println(e);
			}

		}
	}
	
	public boolean isStringEmpty() {
		return stringQueue.isEmpty();
	}
	
	public void offerCoordinate(Coordinate c) {
		coordinateQueue.offer(c);
	}

	public Coordinate takeCoordinate() {
		//Will not return until a message is in the queue
		while (true) {
			try {
				return (coordinateQueue.take());
			} catch (InterruptedException e) {
				System.err.println(e);
			}

		}
	}

	public boolean isCoordinateEmpty() {
		return coordinateQueue.isEmpty();
	}
}
