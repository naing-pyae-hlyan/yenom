package utils;

import java.awt.Dimension;
import javax.swing.JPanel;

public abstract class BaseJPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public void createUi(Dimension size) {
		repaint();
		setVisible(true);
		setBounds(8, 4, size.width, size.height);
	}

	public void disposeUi() {
		setVisible(false);
		removeAll();
	}

}
