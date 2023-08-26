package yenom.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import database.WalletModel;
import utils.MyColors;
import utils.MyIcons;
import java.awt.FlowLayout;

public class WalletRenderer extends JPanel implements ListCellRenderer<WalletModel> {

	private static final long serialVersionUID = 1L;

	private JLabel lblName;
	private JPanel panelColor;

	/**
	 * Create the panel.
	 */
	public WalletRenderer() {
		setLayout(new BorderLayout(0, 0));

		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(MyIcons.logo_wallets_24));
		add(lblIcon, BorderLayout.WEST);

		lblName = new JLabel("");
		lblName.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		
		add(lblName, BorderLayout.CENTER);

		panelColor = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelColor.getLayout();
		flowLayout.setHgap(HEIGHT);
		add(panelColor, BorderLayout.SOUTH);

	}

	@Override
	public Component getListCellRendererComponent(JList<? extends WalletModel> list, WalletModel value, int index,
			boolean isSelected, boolean cellHasFocus) {

		lblName.setText("	" + value.getName());
		panelColor.setBackground(new Color(value.getColor()));

		lblName.setOpaque(true);

		if (isSelected) {
			lblName.setBackground(MyColors.hoverColor());
			setBackground(MyColors.hoverColor());
		} else {
			lblName.setBackground(Color.white);
			setBackground(Color.white);
		}
		
		return this;
	}

}
