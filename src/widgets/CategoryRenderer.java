package widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

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

	private JLabel lblName;
	private JPanel panelColor;
	private JLabel lblChoGyi;

	/**
	 * Create the panel.
	 */
	public CategoryRenderer() {
		setLayout(new BorderLayout(0, 0));

		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(MyIcons.logo_categories_24));
		add(lblIcon, BorderLayout.WEST);

		lblName = new JLabel("");
		lblName.setFont(new Font("Default", Font.PLAIN, 16));

		add(lblName, BorderLayout.CENTER);

		panelColor = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelColor.getLayout();
		flowLayout.setHgap(HEIGHT);
		add(panelColor, BorderLayout.SOUTH);

		lblChoGyi = new JLabel("");
		lblChoGyi.setFont(new Font("Default", Font.PLAIN, 16));
		add(lblChoGyi, BorderLayout.NORTH);

	}

	@Override
	public Component getListCellRendererComponent(JList<? extends CategoryModel> list, CategoryModel value, int index,
			boolean isSelected, boolean cellHasFocus) {

		lblChoGyi.setText(value.isIncome() ? "Income" : "Expense");
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
