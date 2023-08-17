package yenom;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelSetting extends JPanel {


	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelSetting() {
		setBounds(6, 0, 862, 572);
		setLayout(null);
		
		JLabel lblSetting = new JLabel("Settings");
		lblSetting.setHorizontalAlignment(SwingConstants.CENTER);
		lblSetting.setBounds(6, 286, 862, 42);
		lblSetting.setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
		add(lblSetting);


	}

}
