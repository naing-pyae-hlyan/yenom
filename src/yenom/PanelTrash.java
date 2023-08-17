package yenom;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelTrash extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelTrash() {
		setBounds(0, 0, 862, 572);
		setLayout(null);
 

		JLabel lblTrash = new JLabel("Trash");
		lblTrash.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrash.setBounds(6, 286, 862, 42);
		lblTrash.setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
		add(lblTrash);
	}

}
