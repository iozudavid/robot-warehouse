package warehouseInterfaceTesting;

import static org.junit.Assert.*;

import org.junit.Test;

import warehouseInterface.Window;

public class WarehouseInterfaceTest {
	
	@Test
	public void robotDataTest() {
		assert(Window.robotData.get(0).get(0).getText().equals("Robot: A"));
	}
	
	@Test
	public void colourTest() {
		Window win = new Window();
		assert(win.frame.getBackground().getRed() == 238);
		assert(win.frame.getBackground().getBlue() == 238);
		assert(win.frame.getBackground().getGreen() == 238);
	}
	
	@Test
	public void titleTest() {
		Window win = new Window();
		assert(win.frame.getTitle().equals("Warehouse Simulator"));
	}

	@Test
	public void getLayoutTest() {
		Window win = new Window();
		assert(win.frame.getLayout().toString().equals("java.awt.BorderLayout[hgap=0,vgap=0]"));
	}
	
	@Test
	public void componentNumberTest() {
		Window window = new Window();
		assert(window.frame.getComponentCount() == 1);
		assert(window.frame.getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChildrenCount() == 2);
		assert(window.frame.getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChild(1).getAccessibleContext().getAccessibleChildrenCount() == 1);
		assert(window.frame.getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChild(1).getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChildrenCount() == 3);
		assert(window.frame.getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChild(0).getAccessibleContext().getAccessibleChildrenCount() == 0);
	}
	
}
