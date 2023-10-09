package yenom;

import javax.swing.JPanel;

import javax.swing.SwingConstants;

import renderer.*;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import utils.*;
import java.awt.GridLayout;

public class PanelDashboard extends BaseJPanel {

	private static final long serialVersionUID = 1L;

	private static JButton[] arrayBtn;

	@Override
	public void disposeUi(String arg) {
		super.disposeUi(arg);
	}

	/**
	 * Create the panel.
	 */
	public PanelDashboard() {
		setLayout(new GridLayout(1, 0, 0, 0));
		createUi("");
	}

	@Override
	public void createUi(String arg) {
		super.createUi(arg);

		GridLayout gridLayout = new GridLayout(5, 3, 10, 10);
		setLayout(gridLayout);
		// add buttons to the frame
		add(new JButton("+"));
		add(new JButton("="));

		arrayBtn = new JButton[10];
		// add JButtons dynamically
		for (int i = 0; i < arrayBtn.length; i++) {
			arrayBtn[i] = new JButton(Integer.toString(i));
			add(arrayBtn[i]);
		}

	}
}
