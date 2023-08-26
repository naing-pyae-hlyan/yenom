package adapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

import utils.MyColors;

public class DrawerButtonMouseAdapter extends MouseAdapter {
	JPanel panel;

	public DrawerButtonMouseAdapter(JPanel panel) {  
		this.panel = panel;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		System.out.println("Entered");
		panel.setBackground(MyColors.hoverColor());

	}

	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.println("Exited");
		panel.setBackground(MyColors.primaryColor());

	}

	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println("Pressed");
		panel.setBackground(MyColors.secondaryColor());

	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		System.out.println("Released");
		panel.setBackground(MyColors.secondaryColor());
	}

}