package utils;

import java.awt.*;

import java.text.DecimalFormat;

import javax.swing.border.*;

import database.*;

import javax.swing.*;

public class MyWalletCard extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public MyWalletCard(WalletModel wm, Dimension size) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		final int width = (int) (size.width / 3.3);
		final int height = (int) (width / 2);

		setPreferredSize(new Dimension(width, height));
		Border roundedBorder = new RoundBorder(16);
		setBorder(roundedBorder);

		JPanel walletPanel = new JPanel();

		walletPanel.setLayout(new BorderLayout());
		walletPanel.setBorder(BorderFactory.createEmptyBorder(24, 10, 24, 10));
		walletPanel.setBackground(new Color(wm.getColor()));

		JLabel lblWallet = new JLabel(wm.getName());
		lblWallet.setFont(new Font("Default", Font.PLAIN, 18));
		lblWallet.setForeground(Color.white);
		walletPanel.add(lblWallet, BorderLayout.WEST);

		final String formattedTotalAmount = String.format("%.2f", (wm.getTotalIncome() - wm.getTotalExpense()));
		JLabel lblAmount = new JLabel(formattedTotalAmount);
		lblAmount.setFont(new Font("Default", Font.PLAIN, 18));
		lblAmount.setForeground(Color.white);
		walletPanel.add(lblAmount, BorderLayout.EAST);

		// -------------------------------------------------------------

		JPanel totalPanel = new JPanel();

		totalPanel.setLayout(new BorderLayout());
		totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		totalPanel.setBackground(Color.darkGray);

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setBackground(Color.darkGray);

		JLabel lblIncome = new JLabel("Total Income");
		lblIncome.setFont(new Font("Default", Font.PLAIN, 13));
		lblIncome.setForeground(Color.white);
		titlePanel.add(lblIncome, BorderLayout.WEST);

		JLabel lblExpense = new JLabel("Total Expense");
		lblExpense.setFont(new Font("Default", Font.PLAIN, 13));
		lblExpense.setForeground(Color.white);
		titlePanel.add(lblExpense, BorderLayout.EAST);

		// -------------------------------------------------------------

		JPanel valuePanel = new JPanel();
		valuePanel.setLayout(new BorderLayout());
		valuePanel.setBackground(Color.darkGray);

		final String formattedTotalIncome = String.format("%.2f", wm.getTotalIncome());
		JLabel lblIncomeAmount = new JLabel(formattedTotalIncome);
		lblIncomeAmount.setFont(new Font("Default", Font.PLAIN, 13));
		lblIncomeAmount.setForeground(Color.white);
		valuePanel.add(lblIncomeAmount, BorderLayout.WEST);

		final String formattedTotalExpense = String.format("%.2f", wm.getTotalExpense());
		JLabel lblExpenseAmount = new JLabel(formattedTotalExpense);
		lblExpenseAmount.setFont(new Font("Default", Font.PLAIN, 13));
		lblExpenseAmount.setForeground(Color.red);
		valuePanel.add(lblExpenseAmount, BorderLayout.EAST);

		totalPanel.add(titlePanel, BorderLayout.NORTH);
		totalPanel.add(valuePanel, BorderLayout.SOUTH);

		// -------------------------------------------------------------

		add(walletPanel);
		add(totalPanel);
	}

}
