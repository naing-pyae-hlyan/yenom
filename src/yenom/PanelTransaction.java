package yenom;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.*;
import widgets.*;

public class PanelTransaction extends BaseJPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelTransaction() {

	}

	@Override
	public void disposeUi() {
		setVisible(false);
	}

	@Override
	public void createUi() {
		setVisible(true);
		System.out.println("PanelTransaction createUI");
		System.out.println("PanelTransaction");
		setBounds(6, 0, 862, 564);
		setLayout(null);
	}
}
