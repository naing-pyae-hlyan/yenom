package yenom;

import javax.swing.JPanel;

import javax.swing.SwingConstants;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import utils.*;
import database.DatabaseHelper;

public class PanelDashboard extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelDashboard() {
		setBounds(6, 0, 862, 572);
		setLayout(null);

		JLabel lblDashboard = new JLabel("Dashboard");
		lblDashboard.setHorizontalAlignment(SwingConstants.LEFT);
		lblDashboard.setBounds(6, 0, 850, 42);
		lblDashboard.setFont(new Font("JetBrains Mono", Font.PLAIN, 24));
		add(lblDashboard);

		JButton btnNewButton = new JButton("Transaction");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseHelper dbHelper = new DatabaseHelper();
			}
		});
		btnNewButton.setBounds(664, 489, 167, 61);
		btnNewButton.setIcon(new ImageIcon(MyIcons.logo_add));
		btnNewButton.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		add(btnNewButton);
	}
}
