package yenom;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import utils.*;
import database.*;
import renderer.*;

public class PanelTransaction extends BaseJPanel {

	private static final long serialVersionUID = 1L;

	private JList<TransactionModel> listViewTrans;
	private JTextField txtAmount;
	private JTextField txtDescription;

	private JScrollPane scrollPane;
	private JComboBox<WalletModel> comboWallet;
	private JComboBox<CategoryModel> comboCategory;

	private TransactionModel selectedTM = null;

	/**
	 * Create the panel.
	 */
	public PanelTransaction() {
	}

	@Override
	public void disposeUi() {
		super.disposeUi();
	}

	@Override
	public void createUi(Dimension size) {
		super.createUi(size);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		final int halfOfWidth = (int) (size.width / 2);
		final Dimension dimension = new Dimension(halfOfWidth, size.height);

		JPanel lPanel = new JPanel(new BorderLayout(0, 0));
		lPanel.setLayout(new BorderLayout(0, 0));
		lPanel.setPreferredSize(dimension);

		JPanel rPanel = new JPanel();
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
		rPanel.setPreferredSize(dimension);

		listViewTrans = new JList<>();
		listViewTrans.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listViewTrans.setCellRenderer(new TransactionRenderer());
		listViewTrans.setListData(DataController.transactions());

		scrollPane = new JScrollPane(listViewTrans);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(dimension);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lPanel.add(scrollPane);

		// Right Part
		JLabel lblTitle = new JLabel("Manage Transaction");
		lblTitle.setFont(new Font("Default", Font.PLAIN, 24));
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		rPanel.add(lblTitle);
		rPanel.add(Box.createVerticalStrut(16));

		//
		JPanel walletPanel = new JPanel(new GridLayout(1, 1));
		walletPanel.setBorder(new TitledBorder(new EmptyBorder(5, 0, 5, 5), "Select Wallet"));
		walletPanel.setMaximumSize(new Dimension(halfOfWidth - 24, 60));

		// Wallet Dropdown
		WalletModel[] wallets = DataController.getWallets().toArray(new WalletModel[0]);
		comboWallet = new JComboBox<WalletModel>(wallets);
		walletPanel.add(comboWallet);
		rPanel.add(walletPanel);
		rPanel.add(Box.createVerticalStrut(16));

		// -------------------------------------------------------------

		//
		JPanel categoryPanel = new JPanel(new GridLayout(1, 1));
		categoryPanel.setBorder(new TitledBorder(new EmptyBorder(5, 0, 5, 5), "Select Category"));
		categoryPanel.setMaximumSize(new Dimension(halfOfWidth - 24, 60));

		// Category Dropdown
		CategoryModel[] categories = DataController.categories();
		comboCategory = new JComboBox<CategoryModel>(categories);
		categoryPanel.add(comboCategory, BorderLayout.CENTER);
		rPanel.add(categoryPanel);
		rPanel.add(Box.createVerticalStrut(16));

		// -------------------------------------------------------------

		//
		JPanel amountPanel = new JPanel(new GridLayout(1, 1));
		amountPanel.setBorder(new TitledBorder("Enter Amount *"));
		amountPanel.setMaximumSize(new Dimension(halfOfWidth - 24, 60));

		// Amount Input
		txtAmount = new JTextField(20);
		txtAmount.setFont(new Font("Default", Font.PLAIN, 13));
		txtAmount.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		amountPanel.add(txtAmount, BorderLayout.CENTER);
		rPanel.add(amountPanel);
		rPanel.add(Box.createVerticalStrut(16));

		// -------------------------------------------------------------

		//
		JPanel descPanel = new JPanel(new GridLayout(1, 1));
		descPanel.setBorder(new TitledBorder("Enter Description"));
		descPanel.setMaximumSize(new Dimension(halfOfWidth - 24, 60));

		txtDescription = new JTextField(20);
		txtDescription.setFont(new Font("Default", Font.PLAIN, 13));
		txtDescription.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		descPanel.add(txtDescription, BorderLayout.CENTER);
		rPanel.add(descPanel);
		rPanel.add(Box.createVerticalStrut(16));

		// -------------------------------------------------------------

		//
		JPanel btnsPanel = new JPanel(new GridLayout(1, 3));
		btnsPanel.setBorder(new EmptyBorder(5, 0, 5, 5));
		btnsPanel.setMaximumSize(new Dimension(halfOfWidth - 24, 60));

		JButton btnNewButton = new JButton("Add");
		btnsPanel.add(btnNewButton);

		JButton btnUpdateButton = new JButton("Update");
		btnsPanel.add(btnUpdateButton);

		JButton btnDeleteButton = new JButton("Delete");
		btnsPanel.add(btnDeleteButton);

		rPanel.add(btnsPanel);
		rPanel.add(Box.createVerticalGlue());

		add(lPanel);
		add(rPanel);
		// -------------------------------------------------------------

		listViewTrans.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (listViewTrans.isSelectionEmpty()) {
					return;
				}
				int index = listViewTrans.locationToIndex(e.getPoint());
				if (index < 0) {
					return;
				}

				selectedTM = listViewTrans.getModel().getElementAt(index);
				if (selectedTM != null) {
					comboWallet.getModel().setSelectedItem(selectedTM.getWalletModel());
					comboCategory.getModel().setSelectedItem(selectedTM.getCategoryModel());
					txtAmount.setText(String.valueOf(selectedTM.getAmount()));
					if (selectedTM.getDescription() != null) {
						txtDescription.setText(selectedTM.getDescription());
					}
				}
			}
		});

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final float amount = parsingStringAmountToFloat(txtAmount.getText());
				final String desc = txtDescription.getText();
				final Date current = new Date(System.currentTimeMillis());
				final WalletModel wm = (WalletModel) comboWallet.getSelectedItem();
				final CategoryModel cm = (CategoryModel) comboCategory.getSelectedItem();
				final TransactionModel trans = new TransactionModel(-1, amount, desc, current, current, wm, cm);

				addTransaction(trans, cm.isIncome());
			}
		});

		btnUpdateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listViewTrans.isSelectionEmpty()) {
					return;
				}
				final float amount = parsingStringAmountToFloat(txtAmount.getText());
				final String desc = txtDescription.getText();
				final WalletModel wm = (WalletModel) comboWallet.getSelectedItem();
				final CategoryModel cm = (CategoryModel) comboCategory.getSelectedItem();
				final TransactionModel tm = new TransactionModel(selectedTM.getId(), amount, desc, null, null, wm, cm);
				updateTransaction(tm);
			}
		});

		btnDeleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listViewTrans.isSelectionEmpty()) {
					return;
				}
				deleteTransaction(selectedTM);
			}
		});
	}

	private float parsingStringAmountToFloat(String str) {
		float number = 0;
		try {
			number = Float.parseFloat(str);
		} catch (NumberFormatException exception) {
			return number;
		}

		return number;
	}

	private void addTransaction(TransactionModel tm, boolean isIncome) {
		if (tm.getAmount() < 1) {
			JOptionPane.showMessageDialog(this, "Please enter amount!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			Connection connection = DbHelper.connection();

			// Insert the new transaction into the transaction table
			String insertTransSQL = "INSERT INTO transaction "
					+ "(amount, description, category_id, wallet_id, created_date, updated_date) "
					+ "VALUE (?, ?, ?, ?, ?, ?)";

			PreparedStatement insertStatement = connection.prepareStatement(insertTransSQL);
			insertStatement.setFloat(1, tm.getAmount());
			insertStatement.setString(2, tm.getDescription());
			insertStatement.setInt(3, tm.getCategoryModel().getId());
			insertStatement.setInt(4, tm.getWalletModel().getId());
			insertStatement.setDate(5, tm.getCreatedDate());
			insertStatement.setDate(6, tm.getUpdatedDate());
			insertStatement.executeUpdate();

			// Calc and update the current total_income and total_expense values
//			String columnName;
//			if (isIncome) {
//				columnName = "total_income";
//			} else {
//				columnName = "total_expense";
//			}
//
//			String calcAndUpdateSQL = "UPDATE wallet SET " + columnName + " = " + columnName + " + ? WHERE w_id = ?";
//			PreparedStatement calcAndUpdateStatement = connection.prepareStatement(calcAndUpdateSQL);
//			calcAndUpdateStatement.setFloat(1, tm.getAmount());
//			calcAndUpdateStatement.setInt(2, tm.getWalletModel().getId());
//			calcAndUpdateStatement.executeUpdate();

		} catch (SQLException ee) {
			DbHelper.printSQLException(ee);
		}

		// refresh the transactions list
		listViewTrans.setListData(DataController.transactions());

		// Clear amount & description text field
		txtAmount.setText("");
		txtDescription.setText("");
	}

	private void updateTransaction(TransactionModel tm) {

		if (tm == null) {
			JOptionPane.showMessageDialog(this, "Please select transatcion!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			Connection connection = DbHelper.connection();

//			final String retrieveCurrentSelectedTransAmountSQL = "SELECT amount FROM transaction WHERE t_id = ?";
//			PreparedStatement retrieveStatement = connection.prepareStatement(retrieveCurrentSelectedTransAmountSQL);
//			retrieveStatement.setInt(1, tm.getId());
//			ResultSet retrieveResult = retrieveStatement.executeQuery();
//			float currentAmount = 0;
//			if (retrieveResult.next()) {
//				currentAmount = retrieveResult.getFloat("amount");
//			}

//			if (currentAmount != tm.getAmount()) {
//			String columnName;
//			if (tm.getCategoryModel().isIncome()) {
//				columnName = "total_income";
//			} else {
//				columnName = "total_expense";
//			}
//			final String updateAmountSQL = "UPDATE wallet SET " + columnName + " = " + columnName + " - "
//					+ currentAmount + " + ?" + " WHERE w_id = ?";
//
//			PreparedStatement updateAmountStatement = connection.prepareStatement(updateAmountSQL);
//			updateAmountStatement.setFloat(1, tm.getAmount());
//			updateAmountStatement.setInt(2, tm.getWalletModel().getId());
//			updateAmountStatement.executeUpdate();
//			System.out.println(
//					tm.getWalletModel().getName() + "	" + columnName + "	" + tm.getAmount() + "	" + currentAmount);
//			}

			final String sql = "UPDATE transaction SET "
					+ "amount = ?, description = ?, category_id = ?, wallet_id = ?, updated_date = ?  WHERE t_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setFloat(1, tm.getAmount());
			statement.setString(2, tm.getDescription());
			statement.setInt(3, tm.getCategoryModel().getId());
			statement.setInt(4, tm.getWalletModel().getId());
			statement.setDate(5, new Date(System.currentTimeMillis()));
			statement.setInt(6, tm.getId());

			statement.executeUpdate();
		} catch (SQLException e) {
			DbHelper.printSQLException(e);
		}

		// refresh the wallet list
		listViewTrans.setListData(DataController.transactions());
		// Clear wallet name text field
		txtAmount.setText("");
		txtDescription.setText("");
	}

	private void deleteTransaction(TransactionModel tm) {
		if (tm == null) {
			JOptionPane.showMessageDialog(this, "Please select tranction!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			Connection connection = DbHelper.connection();
			// Delete the transaction form trans table
			final String deleteTransSQL = "DELETE FROM transaction WHERE t_id = ?";
			PreparedStatement deleteStatement = connection.prepareStatement(deleteTransSQL);
			deleteStatement.setInt(1, tm.getId());
			deleteStatement.executeUpdate();

			// Retrieve the all total amount after delete
//			String columnName;
//			if (tm.getCategoryModel().isIncome()) {
//				columnName = "total_income";
//			} else {
//				columnName = "total_expense";
//			}
//			final String retrieveAmountSQL = "SELECT " + columnName + " FROM wallet WHERE w_id = ?";
//			PreparedStatement retrieveStatement = connection.prepareStatement(retrieveAmountSQL);
//			retrieveStatement.setInt(1, tm.getWalletModel().getId());
//			ResultSet retrieveResult = retrieveStatement.executeQuery();
//			float currentAmount = 0;
//			if (retrieveResult.next()) {
//				currentAmount = retrieveResult.getFloat(columnName);
//			}
//
//			if (currentAmount > 0) {
//				final String calcAndDeleteSQL = "UPDATE wallet SET " + columnName + " = " + columnName
//						+ " - ? WHERE w_id = ?";
//				PreparedStatement calcAndDeleteStatement = connection.prepareStatement(calcAndDeleteSQL);
//				calcAndDeleteStatement.setFloat(1, tm.getAmount());
//				calcAndDeleteStatement.setInt(2, tm.getWalletModel().getId());
//				calcAndDeleteStatement.executeUpdate();
//			} else if (currentAmount < 0) {
//				final String setDefaultZeroSQL = "UPDATE wallet SET " + columnName + " = ? WHERE w_id = ?";
//				PreparedStatement setDefaultZeroStatement = connection.prepareStatement(setDefaultZeroSQL);
//				setDefaultZeroStatement.setFloat(1, 0);
//				setDefaultZeroStatement.setInt(2, tm.getWalletModel().getId());
//				setDefaultZeroStatement.executeUpdate();
//			}

		} catch (SQLException ee) {
			DbHelper.printSQLException(ee);
		}

		// refresh the transactions list
		listViewTrans.setListData(DataController.transactions());

		// Clear amount & description text field
		txtAmount.setText("");
		txtDescription.setText("");

	}
}
