package yenom;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

public class PanelDashboard extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelDashboard() {
		setBounds(0, 0, 862, 572);
		setLayout(null);
		
		JLabel lblDashboard = new JLabel("Dashboard");
		lblDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashboard.setBounds(6, 286, 862, 42);
		lblDashboard.setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
		add(lblDashboard);
	} 
}
