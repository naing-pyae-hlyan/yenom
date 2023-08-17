package yenom;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelTransaction extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelTransaction() {
		setBounds(0, 0, 862, 572);
		setLayout(null); 

		JLabel lblTransaction = new JLabel("Transactions");
		lblTransaction.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransaction.setBounds(6, 286, 862, 42);
		lblTransaction.setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
		add(lblTransaction);
	}

}
