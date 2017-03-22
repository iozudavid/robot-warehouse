package warehouseInterface;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JScrollBar;

import rp.systems.StoppableRunnable;

public class LoggingInterfaceUpdater implements StoppableRunnable{

	private boolean m_running = true;
	ArrayList<JLabel> loggingLabels = new ArrayList<JLabel>();

	@Override
	public void run() {
		int count = 0;
		while(m_running){
		
			
			String stringToAdd = Window.logging_messages.poll();
			if (!(stringToAdd == null)){
				JLabel labelToAdd = new JLabel(stringToAdd);
				labelToAdd.setForeground(Color.WHITE);
				Window.loggingContainer.add(labelToAdd);
				JScrollBar vertical = Window.scrollPan.getVerticalScrollBar();
				vertical.setValue( vertical.getMaximum() );
			}
			Window.loggingContainer.revalidate();
			Window.loggingContainer.repaint();
			try {
				Thread.sleep(90);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void stop() {
		m_running = false;		
	}

}
