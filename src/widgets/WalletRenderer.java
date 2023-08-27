package widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import database.WalletModel;
import utils.MyColors;
import utils.MyIcons;
import java.awt.FlowLayout;

public class WalletRenderer extends JPanel implements ListCellRenderer<WalletModel> {

	private static final long serialVersionUID = 1L;

	private JPanel child;
	private JLabel lblName;

	/**
	 * Create the panel.
	 */
	public WalletRenderer() {
		setLayout(new BorderLayout(0, 0));

		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		child = new JPanel();
		child.setLayout(new BorderLayout(0, 0));

		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(MyIcons.logo_wallets_24));
		child.add(lblIcon, BorderLayout.WEST);

		lblName = new JLabel("");
		lblName.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));

		child.add(lblName, BorderLayout.CENTER);


		add(child);

	}

	@Override
	public Component getListCellRendererComponent(JList<? extends WalletModel> list, WalletModel value, int index,
			boolean isSelected, boolean cellHasFocus) {
//		child.setBorder(BorderFactory.createLineBorder(new Color(value.getColor()), 2));

		lblName.setText("	" + value.getName());
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
