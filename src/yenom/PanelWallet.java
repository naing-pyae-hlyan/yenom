package yenom;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelWallet extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelWallet() {
		setBounds(0, 0, 862, 572);
		setLayout(null);
 

		JLabel lblWallet = new JLabel("Wallet");
		lblWallet.setHorizontalAlignment(SwingConstants.CENTER);
		lblWallet.setBounds(6, 286, 862, 42);
		lblWallet.setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
		add(lblWallet);
	}

}
