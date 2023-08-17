package yenom.adapter;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public  class DrawerButtonMouseAdapter extends MouseAdapter {
	JPanel panel;

	public DrawerButtonMouseAdapter(JPanel panel) {
		this.panel = panel;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		panel.setBackground(Color.pink);
		

	}

	@Override
	public void mouseExited(MouseEvent e) {
		panel.setBackground(new Color(238, 238, 238));

	}

	@Override
	public void mousePressed(MouseEvent e) {
		panel.setBackground(new Color(248, 130, 120));

	} 

	@Override
	public void mouseReleased(MouseEvent e) {
//		panel.setBackground(Color.pink);

	}

}
