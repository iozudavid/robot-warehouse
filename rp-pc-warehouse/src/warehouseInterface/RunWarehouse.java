package warehouseInterface;

import java.awt.EventQueue;

public class RunWarehouse {
	public static void runWarehouseInterface(){

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("exception is starting of the warehouse interface");
				}
			}
		});
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.println("exception is starting of the warehouse interface");
		}
	}
}
