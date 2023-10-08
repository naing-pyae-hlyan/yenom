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

//	private List<WalletModel> walletList = new ArrayList<>();
//	private List<CategoryModel> categoryList = new ArrayList<>();
//	private int selectedWalletIndex;
//	private int selectedCategoryIndex;

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

				TransactionModel trans = new TransactionModel(-1, number, desc, current, current,
						selectedCategory.getId(), selectedWallet.getId(), selectedCategory.getName(),
						selectedWallet.getName());
				addTrans(trans);

			}
		});
	}

	private void addTrans(TransactionModel model) {
		if (model.getAmount() < 1) {
			JOptionPane.showMessageDialog(this, "Please enter amount!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// todo check selecgtedTM has data

		String sql = "INSERT INTO transaction (amount, description, category_id, category_name, wallet_id, wallet_name, created_date, updated_date) VALUE (?, ?, ?, ?, ?, ?, ?, ?)";

		try {

			Connection connection = DbHelper.connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setFloat(1, model.getAmount());
			statement.setString(2, model.getDescription());
			statement.setInt(3, model.getCategoryId());
			statement.setString(4, model.getCategoryName());
			statement.setInt(5, model.getWalletId());
			statement.setString(6, model.getWalletName());
			statement.setDate(7, model.getCreatedDate());
			statement.setDate(8, model.getUpdatedDate());
			
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
