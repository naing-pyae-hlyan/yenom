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

public class PanelTransaction extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelTransaction() {
		setBounds(6, 0, 862, 572);
		setLayout(null); 

		JLabel lblTransaction = new JLabel("Transactions");
		lblTransaction.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransaction.setBounds(6, 286, 862, 42);
		lblTransaction.setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
		add(lblTransaction);

		JButton btnNewButton = new JButton("Transaction");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(664, 489, 167, 61);
		btnNewButton.setIcon(new ImageIcon(MyIcons.logo_add));
		btnNewButton.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		add(btnNewButton);
	}

}
