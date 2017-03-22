package motion;

import networking.RobotClient;

public class MessageReader extends Thread{
	
	private Controller controller;
	private RobotClient client;
	
	public MessageReader(Controller c, RobotClient r){
		
		this.controller = c;
		this.client = r;
	}
	
	public void run(){
		
		while(true){
			
			String message = client.getReceivedMessage();
			
			if(message.equals("STOP")){
				
				controller.setReceivedSTOP(true);;
			}
			
			System.out.println("From server: " + message);
			
		}
		
	}

}
