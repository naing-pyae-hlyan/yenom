package yenom;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;

import utils.*;
import database.*;
import renderer.*;

public class PanelWallet extends BaseJPanel {

	private static final long serialVersionUID = 1L;
	private JList<WalletModel> listViewWallet;
	private JPanel selectedColorPanel;
	private JTextField txtWalletName;
	private JScrollPane scrollPane;

	private Color selectedColor = Color.white;
	private WalletModel selectedWM = null;

	public PanelWallet() {
//		createUi();
	}

	@Override
	public void disposeUi(String arg) {
		super.disposeUi(arg);
	}

	@Override
	public void createUi(String arg) {
		super.createUi(arg);

		listViewWallet = new JList<>();
		listViewWallet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// set WalletRender for custom widget
		listViewWallet.setCellRenderer(new WalletRenderer());
		listViewWallet.setListData(DataController.wallets());

		scrollPane = new JScrollPane(listViewWallet); // load wallet list
		scrollPane.setBounds(6, 6, 430, 560);
		add(scrollPane);

		JLabel lblTitle = new JLabel("Manage Wallet");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Default", Font.PLAIN, 24));
		lblTitle.setBounds(531, 6, 237, 33);
		add(lblTitle);

		JLabel lblTextField = new JLabel("Wallet Name *");
		lblTextField.setFont(new Font("Default", Font.PLAIN, 13));
		lblTextField.setBounds(505, 148, 114, 16);
		add(lblTextField);

		txtWalletName = new JTextField("");
		txtWalletName.setFont(new Font("Default", Font.PLAIN, 13));
		txtWalletName.setHorizontalAlignment(SwingConstants.LEFT);
		txtWalletName.setBounds(503, 176, 292, 64);
		txtWalletName.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
		add(txtWalletName);

		JLabel lblTextfield2 = new JLabel("Wallet Color");
		lblTextfield2.setFont(new Font("Default", Font.PLAIN, 13));
		lblTextfield2.setBounds(505, 264, 114, 16);
		add(lblTextfield2);

		selectedColorPanel = new JPanel();
		selectedColorPanel.setBounds(505, 292, 178, 60);
		selectedColorPanel.setBackground(Color.white);
		add(selectedColorPanel);

		JButton btnColorButton = new JButton("");
		btnColorButton.setBounds(733, 292, 64, 64);
		btnColorButton.setIcon(new ImageIcon(MyIcons.logo_color_48));
		add(btnColorButton);

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

		listViewWallet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (listViewWallet.isSelectionEmpty()) {
					return;
				}

				int index = listViewWallet.locationToIndex(e.getPoint());

				if (index < 0) {
					return;
				}

				selectedWM = listViewWallet.getModel().getElementAt(index);
				if (selectedWM != null) {
					txtWalletName.setText(selectedWM.getName());
					selectedColor = new Color(selectedWM.getColor());
					selectedColorPanel.setBackground(selectedColor);
				}
			}
		});

		btnColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(btnColorButton, "Choose", getBackground());
				if (color != null) {
					selectedColor = color;
					selectedColorPanel.setBackground(color);
				}
			}
		});

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtWalletName.getText();
				int color = selectedColor.getRGB();
				addWallet(name, color);
			}
		});

		btnUpdateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listViewWallet.isSelectionEmpty()) {
					return;
				}
				String name = txtWalletName.getText();
				int color = selectedColor.getRGB();
				updateWallet(selectedWM, name, color);
			}
		});

		btnDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listViewWallet.isSelectionEmpty()) {
					return;
				}
				deleteWallet(selectedWM);
			}
		});

	}

	private void addWallet(String name, int color) {
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter wallet name!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// checked for selected value is same as new wallet
		if (selectedWM != null && new String(name).equals(selectedWM.getName())) {
			JOptionPane.showMessageDialog(this, "Please enter new wallet name!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (int i = 0, l = listViewWallet.getModel().getSize(); i < l; i++) {
			WalletModel walletModel = listViewWallet.getModel().getElementAt(i);
			if (new String(name).equals(walletModel.getName())) {
				JOptionPane.showMessageDialog(this, "Please enter new wallet name!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		String sql = "INSERT INTO wallet (w_name, w_color, total_income, total_expense) VALUES (?, ?, ?, ?)";
		try {

			Connection connection = DbHelper.connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setInt(2, color);
			statement.setFloat(3, 0);
			statement.setFloat(4, 0);
			statement.executeUpdate();
		} catch (SQLException ee) {
			DbHelper.printSQLException(ee);
		}

		// refresh the wallet list
		listViewWallet.setListData(DataController.wallets());

		// Clear wallet name text field
		txtWalletName.setText("");
		// Clear wallet color panel
		selectedColor = Color.white;
		selectedColorPanel.setBackground(Color.white);
	}

	private void updateWallet(WalletModel wm, String name, int color) {
		if (wm == null) {
			JOptionPane.showMessageDialog(this, "Please select wallet!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String sql = "UPDATE wallet SET w_name = ?, w_color = ? WHERE w_id = ?";
		try {
			Connection connection = DbHelper.connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setInt(2, color);
			statement.setInt(3, wm.getId());
			statement.executeUpdate();

		} catch (SQLException ee) {
			DbHelper.printSQLException(ee);
		}
		// refresh the wallet list
		listViewWallet.setListData(DataController.wallets());
		// Clear wallet name text field
		txtWalletName.setText("");
		// Clear wallet color panel
		selectedColor = Color.white;
		selectedColorPanel.setBackground(Color.white);

	}

	private void deleteWallet(WalletModel wm) {
		if (wm == null) {
			JOptionPane.showMessageDialog(this, "Please select wallet!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String sql = "DELETE FROM wallet WHERE w_id = ?";
		try {
			Connection connection = DbHelper.connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, wm.getId());
			statement.executeUpdate();

		} catch (SQLException ee) {
			DbHelper.printSQLException(ee);
		}
		// refresh the wallet list
		listViewWallet.setListData(DataController.wallets());

		// Clear wallet name text field
		txtWalletName.setText("");
		// Clear wallet color panel
		selectedColor = Color.white;
		selectedColorPanel.setBackground(Color.white);
	}

}
