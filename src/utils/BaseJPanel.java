package utils;

import javax.swing.JPanel;

public abstract class BaseJPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public void createUi(String arg) {
		repaint();
		setVisible(true);
		setBounds(6, 0, 862, 564);
		setLayout(null);
		System.out.println("createUi -> " + arg);
	}

	public void disposeUi(String arg) {
		setVisible(false);
		removeAll();
		System.out.println("disposedUi -> " + arg);
	}

}
