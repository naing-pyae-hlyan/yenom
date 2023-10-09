package yenom;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
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
	public void disposeUi(String arg) {
		super.disposeUi(arg);
	}

	@Override
	public void createUi(String arg) {
		super.createUi(arg);

		listViewTrans = new JList<>();
		listViewTrans.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// set TransRender for custom ui.
		listViewTrans.setCellRenderer(new TransactionRenderer());
		listViewTrans.setListData(DataController.transactions());

		scrollPane = new JScrollPane(listViewTrans); // load wallet list
		scrollPane.setBounds(6, 6, 430, 560);
		add(scrollPane);

		JLabel lblTitle = new JLabel("Manage Transaction");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Default", Font.PLAIN, 24));
		lblTitle.setBounds(531, 6, 237, 33);
		add(lblTitle);

		JLabel lblWallet = new JLabel("Wallet");
		lblWallet.setFont(new Font("Default", Font.PLAIN, 13));
		lblWallet.setBounds(505, 65, 114, 16);
		add(lblWallet);

		WalletModel[] wallets = DataController.getWallets().toArray(new WalletModel[0]);
		comboWallet = new JComboBox<WalletModel>(wallets);
		comboWallet.setBounds(503, 66, 292, 64);
		add(comboWallet);

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Default", Font.PLAIN, 13));
		lblCategory.setBounds(505, 141, 114, 16);
		add(lblCategory);

		// TODO need to refresh combo list when new item[wallet or category] is
		// added.
		CategoryModel[] categories = DataController.categories();
		comboCategory = new JComboBox<CategoryModel>(categories);
		comboCategory.setBounds(503, 142, 292, 64);
		add(comboCategory);

		JLabel lblAmount = new JLabel("Amount *");
		lblAmount.setFont(new Font("Default", Font.PLAIN, 13));
		lblAmount.setBounds(505, 217, 114, 16);
		add(lblAmount);

		txtAmount = new JTextField("");
		txtAmount.setFont(new Font("Default", Font.PLAIN, 13));
		txtAmount.setHorizontalAlignment(SwingConstants.LEFT);
		txtAmount.setBounds(503, 234, 292, 64);
		txtAmount.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
		add(txtAmount);

		JLabel lblDesc = new JLabel("Description");
		lblDesc.setFont(new Font("Default", Font.PLAIN, 13));
		lblDesc.setBounds(505, 309, 114, 16);
		add(lblDesc);

		txtDescription = new JTextField("");
		txtDescription.setFont(new Font("Default", Font.PLAIN, 13));
		txtDescription.setHorizontalAlignment(SwingConstants.LEFT);
		txtDescription.setBounds(503, 326, 292, 64);
		txtDescription.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
		add(txtDescription);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBounds(505, 414, 64, 64);
		btnNewButton.setIcon(new ImageIcon(MyIcons.logo_add_48));
		add(btnNewButton);

		JButton btnUpdateButton = new JButton("");
		btnUpdateButton.setBounds(619, 414, 64, 64);
		btnUpdateButton.setIcon(new ImageIcon(MyIcons.logo_update_48));
		add(btnUpdateButton);

		JButton btnDeleteButton = new JButton("");
		btnDeleteButton.setBounds(733, 414, 64, 64);
		btnDeleteButton.setIcon(new ImageIcon(MyIcons.logo_delete_48));
		add(btnDeleteButton);

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
			JOptionPane.showMessageDialog(new JPanel(), "Please enter amount!", "Error", JOptionPane.ERROR_MESSAGE);
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

			// SUM and update the current total_income and total_expense values
			String columnName;
			if (isIncome) {
				columnName = "total_income";
			} else {
				columnName = "total_expense";
			}

			String calcAndUpdateSQL = "UPDATE wallet SET " + columnName + " = " + columnName + " + ? WHERE w_id = ?";
			PreparedStatement calcAndUpdateStatement = connection.prepareStatement(calcAndUpdateSQL);
			calcAndUpdateStatement.setFloat(1, tm.getAmount());
			calcAndUpdateStatement.setInt(2, tm.getWalletModel().getId());
			calcAndUpdateStatement.executeUpdate();

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

		String sql = "UPDATE transaction SET "
				+ "amount = ?, description = ?, category_id = ?, wallet_id = ?, updated_date = ?  WHERE t_id = ?";
		try {
			Connection connection = DbHelper.connection();
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

			// Update the current total_income and total_expense values
			String columnName;
			if (tm.getCategoryModel().isIncome()) {
				columnName = "total_income";
			} else {
				columnName = "total_expense";
			}

			// Retrieve the trans amount before delete
			final String retrieveAmountSQL = "SELECT " + columnName + " FROM wallet WHERE w_id = ?";
			PreparedStatement retrieveStatement = connection.prepareStatement(retrieveAmountSQL);
			retrieveStatement.setInt(1, tm.getWalletModel().getId());
			ResultSet retrieveResult = retrieveStatement.executeQuery();
			float currentAmount = 0;
			if (retrieveResult.next()) {
				currentAmount = retrieveResult.getFloat(columnName);
			}

			if (currentAmount > 0) {
				final String calcAndDeleteSQL = "UPDATE wallet SET " + columnName + " = " + columnName
						+ " - ? WHERE w_id = ?";
				PreparedStatement calcAndDeleteStatement = connection.prepareStatement(calcAndDeleteSQL);
				calcAndDeleteStatement.setFloat(1, tm.getAmount());
				calcAndDeleteStatement.setInt(2, tm.getWalletModel().getId());
				calcAndDeleteStatement.executeUpdate();
			} else if (currentAmount < 0) {
				final String setDefaultZeroSQL = "UPDATE wallet SET " + columnName + " = ? WHERE w_id = ?";
				PreparedStatement setDefaultZeroStatement = connection.prepareStatement(setDefaultZeroSQL);
				setDefaultZeroStatement.setFloat(1, 0);
				setDefaultZeroStatement.setInt(2, tm.getWalletModel().getId());
				setDefaultZeroStatement.executeUpdate();
			}

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
