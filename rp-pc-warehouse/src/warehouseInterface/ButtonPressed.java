package warehouseInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPressed implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().substring(8).equals("A")){
			System.out.println(e.getActionCommand().substring(8));
		} else if (e.getActionCommand().substring(8).equals("B")){
			System.out.println(e.getActionCommand().substring(8));
		} else if (e.getActionCommand().substring(8).equals("C")){
			System.out.println(e.getActionCommand().substring(8));
		}
	}
	
}
