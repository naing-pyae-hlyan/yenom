package renderer;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import database.TransactionModel;
import utils.MyColors;

public class TransactionRenderer extends JPanel implements ListCellRenderer<TransactionModel> {

	private static final long serialVersionUID = 1L;

	private final java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("E, MMM dd, yyyy hh:mm a");

	private JLabel lblDate;
	private JLabel lblWalletName;
	private JLabel lblCategoryName;
	private JLabel lblAmount;
	private JLabel lblDescription;

	public TransactionRenderer() {
		setLayout(new BorderLayout(0, 0));

		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel child = new JPanel();
		child.setLayout(new BorderLayout(0, 0));

		lblDate = new JLabel("");
		lblDate.setFont(new Font("JetBrains Mono", Font.PLAIN, 10));
		child.add(lblDate, BorderLayout.PAGE_START);

		lblWalletName = new JLabel("");
		lblWalletName.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		child.add(lblWalletName, BorderLayout.LINE_START);

		lblCategoryName = new JLabel("");
		lblCategoryName.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		child.add(lblCategoryName, BorderLayout.CENTER);

		lblAmount = new JLabel("0.00");
		lblAmount.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		child.add(lblAmount, BorderLayout.LINE_END);

		lblDescription = new JLabel("");
		lblDescription.setFont(new Font("JetBrains Mono", Font.PLAIN, 12));
		child.add(lblDescription, BorderLayout.PAGE_END);

		add(child);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends TransactionModel> list, TransactionModel value,
			int index, boolean isSelected, boolean cellHasFocus) {

		lblDate.setText(dateFormat.format(new Date(value.getUpdatedDate().getTime())));
		lblWalletName.setText(value.getWalletModel().getName());
		lblCategoryName.setText("	->	" + value.getCategoryModel().getName());

		String amount = String.format("%.2f", value.getAmount());
		if (value.getCategoryModel().isIncome()) {
			amount = "+" + amount;
		} else {
			amount = "-" + amount;
		}

		lblAmount.setText(amount);
		Color walletColor = new Color(value.getWalletModel().getColor());
		Color categoryColor = new Color(value.getCategoryModel().getColor());

		String desc = value.getDescription();
		if (desc == null) {
			lblDescription.setText("");
		} else {
			lblDescription.setText(desc);
		}

		lblDate.setOpaque(true);
		lblWalletName.setOpaque(true);
		lblCategoryName.setOpaque(true);
		lblAmount.setOpaque(true);
		lblDescription.setOpaque(true);

		if (isSelected) {
			lblDate.setBackground(MyColors.primaryColor());
			lblDate.setForeground(Color.white);
			lblWalletName.setBackground(MyColors.primaryColor());
			lblWalletName.setForeground(Color.white);
			lblCategoryName.setBackground(MyColors.primaryColor());
			lblCategoryName.setForeground(Color.white);
			lblAmount.setBackground(MyColors.primaryColor());
			lblAmount.setForeground(Color.white);
			lblDescription.setBackground(MyColors.primaryColor());
			lblDescription.setForeground(Color.white);
			setBackground(MyColors.primaryColor());

		} else {

			lblDate.setBackground(Color.white);
			lblDate.setForeground(Color.black);
			lblWalletName.setBackground(Color.white);
			lblWalletName.setForeground(walletColor);
			lblCategoryName.setBackground(Color.white);
			lblCategoryName.setForeground(categoryColor);
			lblAmount.setBackground(Color.white);
			lblAmount.setForeground(Color.black);
			lblDescription.setBackground(Color.white);
			lblDescription.setForeground(Color.black);
			setBackground(Color.white);
		}

		return this;
	}

}
