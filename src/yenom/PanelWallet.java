package yenom;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.*;
import database.*;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;

public class PanelWallet extends JPanel {

	private static final long serialVersionUID = 1L;
	private JList<WalletModel> listWallet;
	private JPanel selectedColorPanel;
	private JTextField txtWalletName;
	private JScrollPane scrollPane;

	private Color selectedColor = Color.white;
	private WalletModel selectedWM = null;

	/**
	 * Create the panel.
	 */
	public PanelWallet() {
		setBounds(6, 0, 862, 564);
		setLayout(null);

		listWallet = new JList<>();
		listWallet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// set WalletRender for custom widget
		listWallet.setCellRenderer(new WalletRenderer());
		listWallet.setListData(getWallets());

		scrollPane = new JScrollPane(listWallet); // load wallet list
		scrollPane.setBounds(6, 6, 430, 560);
		add(scrollPane);

		JLabel lblNewLabel = new JLabel("Manage Wallet");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 24));
		lblNewLabel.setBounds(531, 6, 237, 33);
		add(lblNewLabel);

		JLabel lblTextField = new JLabel("Wallet Name *");
		lblTextField.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
		lblTextField.setBounds(505, 148, 114, 16);
		add(lblTextField);

		txtWalletName = new JTextField("");
		txtWalletName.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
		txtWalletName.setHorizontalAlignment(SwingConstants.LEFT);
		txtWalletName.setBounds(503, 176, 292, 64);
		add(txtWalletName);
		txtWalletName.setColumns(10);

		JLabel lblTextfield2 = new JLabel("Wallet Color");
		lblTextfield2.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
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

		listWallet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = listWallet.locationToIndex(e.getPoint());
				selectedWM = listWallet.getModel().getElementAt(index);
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
				String name = txtWalletName.getText();
				int color = selectedColor.getRGB();
				updateWallet(selectedWM, name, color);
			}
		});

		btnDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteWallet(selectedWM);
			}
		});

	}

	private WalletModel[] getWallets() {
		String sql = "SELECT * FROM wallet";
		try {
			Connection connection = DbHelper.connection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			List<WalletModel> wallets = new ArrayList<>();
			while (result.next()) {
				final int id = result.getInt("id");
				final String name = result.getString("name");
				final int color = result.getInt("color");
				final int tot_income = result.getInt("total_income");
				final int tot_expense = result.getInt("total_expense");
				wallets.add(new WalletModel(id, name, color, tot_income, tot_expense));
			}

			return wallets.toArray(new WalletModel[0]);
		} catch (SQLException e) {
			DbHelper.printSQLException(e);
		}
		return new WalletModel[0];
	}

	private void addWallet(String name, int color) {
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter wallet name", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String sql = "INSERT INTO wallet (name, color, total_income, total_expense) VALUES (?, ?, ?, ?)";
		try {

			Connection connection = DbHelper.connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setInt(2, color);
			statement.setInt(3, 0);
			statement.setInt(4, 0);
			statement.executeUpdate();
		} catch (SQLException ee) {
			DbHelper.printSQLException(ee);
		}

		// refresh the wallet list
		listWallet.setListData(getWallets());

		// Clear wallet name text field
		txtWalletName.setText("");
		// Clear wallet color panel
		selectedColor = Color.white;
		selectedColorPanel.setBackground(Color.white);
	}

	private void updateWallet(WalletModel wm, String name, int color) {
		if (wm == null) {
			JOptionPane.showMessageDialog(this, "Please select wallet", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String sql = "UPDATE wallet SET name = ?, color = ? WHERE id = ?";
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
		listWallet.setListData(getWallets());
		// Clear wallet name text field
		txtWalletName.setText("");
		// Clear wallet color panel
		selectedColor = Color.white;
		selectedColorPanel.setBackground(Color.white);

	}

	private void deleteWallet(WalletModel wm) {
		if (wm == null) {
			JOptionPane.showMessageDialog(this, "Please select wallet", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String sql = "DELETE FROM wallet WHERE id = ?";
		try {
			Connection connection = DbHelper.connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, wm.getId());
			statement.executeUpdate();

		} catch (SQLException ee) {
			DbHelper.printSQLException(ee);
		}
		// refresh the wallet list
		listWallet.setListData(getWallets());

		// Clear wallet name text field
		txtWalletName.setText("");
		// Clear wallet color panel
		selectedColor = Color.white;
		selectedColorPanel.setBackground(Color.white);
	}

	public class WalletRenderer extends JPanel implements ListCellRenderer<WalletModel> {

		private static final long serialVersionUID = 1L;

		private JLabel lblIcon = new JLabel();
		private JLabel lblName = new JLabel();

		public WalletRenderer() {
			setLayout(new BorderLayout(0, 0));

			JPanel panel = new JPanel(new GridLayout(0, 1));
			panel.add(lblName);
			add(lblIcon, BorderLayout.WEST);
			add(panel, BorderLayout.CENTER);

		}

		@Override
		public Component getListCellRendererComponent(JList<? extends WalletModel> list, WalletModel value, int index,
				boolean isSelected, boolean cellHasFocus) {
			lblIcon.setIcon(new ImageIcon(MyIcons.logo_wallets_64));
			lblName.setFont(new Font("JetBrains Mono", Font.PLAIN, 24));
			lblName.setText(value.getName());

			lblName.setOpaque(true);
			lblIcon.setOpaque(true);

			if (isSelected) {
				lblName.setBackground(MyColors.primaryColor());
				lblIcon.setBackground(MyColors.primaryColor());
				setBackground(MyColors.primaryColor());
			} else {
				lblName.setBackground(Color.white);
				lblIcon.setBackground(Color.white);
				setBackground(Color.white);
			}

			return this;
		}

	}
}
