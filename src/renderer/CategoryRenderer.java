package renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import database.*;
import utils.MyColors;
import utils.MyIcons;
import java.awt.FlowLayout;

public class CategoryRenderer extends JPanel implements ListCellRenderer<CategoryModel> {

	private static final long serialVersionUID = 1L;

	private JLabel lblType;
	private JLabel lblName;

	/**
	 * Create the panel.
	 */
	public CategoryRenderer() {
		setLayout(new BorderLayout(0, 0));

		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel child = new JPanel();
		child.setLayout(new BorderLayout(0, 0));

		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(MyIcons.logo_categories_24));
		child.add(lblIcon, BorderLayout.WEST);

		lblName = new JLabel("");
		lblName.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		child.add(lblName, BorderLayout.CENTER);

		lblType = new JLabel("");
		lblType.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		child.add(lblType, BorderLayout.EAST);

		add(child);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends CategoryModel> list, CategoryModel value, int index,
			boolean isSelected, boolean cellHasFocus) {

		lblType.setText(value.isIncome() ? "Income" : "Expense");
		lblName.setText("	" + value.getName());

		lblType.setOpaque(true);
		lblName.setOpaque(true);

		if (isSelected) {
			lblName.setBackground(MyColors.hoverColor());
			lblType.setBackground(MyColors.hoverColor());
			setBackground(MyColors.hoverColor());
		} else {
			lblName.setBackground(Color.white);
			lblType.setBackground(Color.white);
			setBackground(Color.white);
		}

		return this;
	}

}
