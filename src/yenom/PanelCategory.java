package yenom;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import yenom.adapter.*;

public class PanelCategory extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelCategory() {
		setBounds(6, 0, 862, 572);
		setLayout(null);
		
		
		JLabel lblCategories = new JLabel("Categories");
		lblCategories.setHorizontalAlignment(SwingConstants.LEFT);
		lblCategories.setBounds(6, 0, 850, 42);
		lblCategories.setFont(new Font("JetBrains Mono", Font.PLAIN, 24));
		add(lblCategories);
		
		JButton btnNewButton = new JButton("Category");
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
 