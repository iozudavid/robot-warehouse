package warehouseInterface;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Timer;

import lejos.geom.Line;
import lejos.robotics.navigation.Pose;
import rp.geom.GeometryUtils;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.LineMap;

public class mapVisPanel extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Line[] lines;

	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		this.add(new JLabel("asdasda"));
    }
	
	
	public mapVisPanel(GridMap gridMap, LineMap lineMap){
		lines = lineMap.getLines();
		
		new Timer(16, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		}).start();
	}
}
