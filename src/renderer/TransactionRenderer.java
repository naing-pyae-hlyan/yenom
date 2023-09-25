package renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import database.TransactionModel;
import utils.MyColors;
import utils.MyIcons;
import java.awt.FlowLayout;

public class TransactionRenderer extends JPanel implements ListCellRenderer<TransactionModel> {

	private static final long serialVersionUID = 1L;

	private final java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("E, MMM dd, yyyy");
	private final java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("hh:mm a");
	private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

	private JLabel lblDate;
	private JLabel lblTime;
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
		child.add(lblDate);

		lblTime = new JLabel("");
		lblTime.setFont(new Font("JetBrains Mono", Font.PLAIN, 10));
		child.add(lblTime);

		lblWalletName = new JLabel("");
		lblWalletName.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		child.add(lblWalletName);

		lblCategoryName = new JLabel("");
		lblCategoryName.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		child.add(lblCategoryName);

		lblAmount = new JLabel("0.00");
		lblAmount.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		child.add(lblAmount);

		lblDescription = new JLabel("");
		lblDescription.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		child.add(lblDescription);

		add(child);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends TransactionModel> list, TransactionModel value,
			int index, boolean isSelected, boolean cellHasFocus) {

		lblDate.setText(dateFormat.format(value.getDatetime()));
		lblTime.setText(timeFormat.format(value.getDatetime()));
		lblWalletName.setText(value.getWalletName());
		lblCategoryName.setText(value.getCategoryName());
		lblAmount.setText(decimalFormat.format(value.getAmount()));

		String desc = value.getDescription();
		if (desc == null) {
			lblDescription.setText("");
		} else {
			lblDescription.setText(desc);
		}

		return this;
	}

}
