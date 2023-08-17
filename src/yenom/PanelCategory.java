package yenom;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelCategory extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelCategory() {
		setBounds(0, 0, 862, 572);
		setLayout(null);
		
		
		JLabel lblCategories = new JLabel("Categories");
		lblCategories.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategories.setBounds(0, 286, 862, 42);
		lblCategories.setFont(new Font("JetBrains Mono", Font.PLAIN, 30));
		add(lblCategories);
	}

}
 