package yenom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import utils.*;
import database.*;
import renderer.*;

public class PanelTransaction extends BaseJPanel {

	private static final long serialVersionUID = 1L;

	private JPanel lPanel;
	private JPanel rPanel;

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

		final int halfOfWidth = (int) (size.width / 2);
		final Dimension dimension = new Dimension(halfOfWidth, size.height);

		lPanel = new JPanel();
		lPanel.setSize(dimension);
		lPanel.setPreferredSize(dimension);
		rPanel = new JPanel();
		rPanel.setSize(dimension);
		rPanel.setPreferredSize(dimension);
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));

		listViewTrans = new JList<>();
		listViewTrans.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// set TransRender for custom ui.
		listViewTrans.setCellRenderer(new TransactionRenderer());
		listViewTrans.setListData(DataController.transactions());

		scrollPane = new JScrollPane(listViewTrans); // load wallet list
		scrollPane.setSize(dimension);
		scrollPane.setPreferredSize(dimension);
		lPanel.add(scrollPane);

		// Right Part
		JLabel lblTitle = new JLabel("Manage Transaction");
		lblTitle.setFont(new Font("Default", Font.PLAIN, 24));
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		rPanel.add(lblTitle);
		rPanel.add(Box.createVerticalStrut(16));

		//
		JPanel walletPanel = new JPanel();
		walletPanel.setBorder(new TitledBorder(new EmptyBorder(5, 0, 5, 5), "Select Wallet"));
		walletPanel.setLayout(new GridLayout(1, 1));
		walletPanel.setMaximumSize(new Dimension(halfOfWidth - 24, 60));

		// Wallet Dropdown
		WalletModel[] wallets = DataController.getWallets().toArray(new WalletModel[0]);
		comboWallet = new JComboBox<WalletModel>(wallets);
		walletPanel.add(comboWallet);
		rPanel.add(walletPanel);
		rPanel.add(Box.createVerticalStrut(16));

		// -------------------------------------------------------------

		//
		JPanel categoryPanel = new JPanel();
		categoryPanel.setBorder(new TitledBorder(new EmptyBorder(5, 0, 5, 5), "Select Category"));
		categoryPanel.setLayout(new GridLayout(1, 1));
		categoryPanel.setMaximumSize(new Dimension(halfOfWidth - 24, 60));

		// Category Dropdown
		CategoryModel[] categories = DataController.categories();
		comboCategory = new JComboBox<CategoryModel>(categories);
		categoryPanel.add(comboCategory, BorderLayout.CENTER);
		rPanel.add(categoryPanel);
		rPanel.add(Box.createVerticalStrut(16));

		// -------------------------------------------------------------

		//
		JPanel amountPanel = new JPanel();
		amountPanel.setBorder(new TitledBorder(new EmptyBorder(5, 0, 5, 5), "Enter Amount *"));
		amountPanel.setLayout(new GridLayout(1, 1));
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
		JPanel descPanel = new JPanel();
		descPanel.setBorder(new TitledBorder(new EmptyBorder(5, 0, 5, 5), "Enter Description"));
		descPanel.setLayout(new GridLayout(1, 1));
		descPanel.setMaximumSize(new Dimension(halfOfWidth - 24, 60));

		txtDescription = new JTextField(20);
		txtDescription.setFont(new Font("Default", Font.PLAIN, 13));
		txtDescription.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		descPanel.add(txtDescription, BorderLayout.CENTER);
		rPanel.add(descPanel);
		rPanel.add(Box.createVerticalStrut(16));

		// -------------------------------------------------------------

		//
		JPanel btnsPanel = new JPanel();
		btnsPanel.setBorder(new EmptyBorder(5, 0, 5, 5));
		btnsPanel.setLayout(new GridLayout(1, 3));
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
//			JOptionPane.showMessageDialog(new JPanel(), "Please enter amount!", "Error", JOptionPane.ERROR_MESSAGE);
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
