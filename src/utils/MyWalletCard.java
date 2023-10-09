package utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class MyWalletCard extends JPanel {

	/**
	 * Create the panel.
	 */
	public MyWalletCard() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize);
	}

}
