package yenom;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

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
		add(txtAmount);

		JLabel lblDesc = new JLabel("Description");
		lblDesc.setFont(new Font("Default", Font.PLAIN, 13));
		lblDesc.setBounds(505, 309, 114, 16);
		add(lblDesc);

		txtDescription = new JTextField("");
		txtDescription.setFont(new Font("Default", Font.PLAIN, 13));
		txtDescription.setHorizontalAlignment(SwingConstants.LEFT);
		txtDescription.setBounds(503, 326, 292, 64);
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
//					comboWallet.setSelectedItem(selectedTM.getWal);
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
				String amount = txtAmount.getText();
				float number = 0;
				try {
					number = Float.parseFloat(amount);

				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(new JPanel(), "Please enter amount!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String desc = txtDescription.getText();
				Date current = new Date(System.currentTimeMillis());

				CategoryModel selectedCategory = (CategoryModel) comboCategory.getSelectedItem();
				WalletModel selectedWallet = (WalletModel) comboWallet.getSelectedItem();

				TransactionModel trans = new TransactionModel(-1, number, desc, current, current, selectedWallet,
						selectedCategory);

				addTransaction(trans, selectedCategory.isIncome());
			}
		});

		btnUpdateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateTransaction(selectedTM);
			}
		});

		btnDeleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteTransaction(selectedTM);
			}
		});
	}

	private void addTransaction(TransactionModel model, boolean isIncome) {
		if (model.getAmount() < 1) {
			JOptionPane.showMessageDialog(this, "Please enter amount!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// todo check selecgtedTM has data

		String sql = "INSERT INTO transaction "
				+ "(amount, description, category_id, wallet_id, created_date, updated_date) "
				+ "VALUE (?, ?, ?, ?, ?, ?, ?, ?)";

		try {

			Connection connection = DbHelper.connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setFloat(1, model.getAmount());
			statement.setString(2, model.getDescription());
			statement.setInt(3, model.getCategoryModel().getId());
			statement.setInt(4, model.getWalletModel().getId());
			statement.setDate(5, model.getCreatedDate());
			statement.setDate(6, model.getUpdatedDate());

			statement.executeUpdate();
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
		System.out.println(tm.getDescription());

		String sql = "UPDATE transaction SET "
				+ "amount = ?, description = ?, category_id = ?, wallet_id = ?, updated_date = ?  WHERE t_id = ?";
		try {
			Connection connection = DbHelper.connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setFloat(1, tm.getAmount());
			statement.setString(2, tm.getDescription());
			statement.setInt(3, tm.getCategoryModel().getId());
			statement.setInt(4, tm.getWalletModel().getId());
			final Date currentUpdatedDate = new Date(System.currentTimeMillis());
			statement.setDate(5, currentUpdatedDate);
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

		String sql = "DELETE FROM transaction WHERE id = ?";

		try {
			Connection connection = DbHelper.connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, tm.getId());
			statement.executeUpdate();

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
