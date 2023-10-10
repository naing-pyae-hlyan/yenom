package yenom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;

import utils.*;
import database.*;
import renderer.*;

public class PanelWallet extends BaseJPanel {

	private static final long serialVersionUID = 1L;

	private JList<WalletModel> listViewWallet;

	private JTextField txtWalletName;
	private JScrollPane scrollPane;

	private CircularPanel circularSelectedColorPanel;
	private JLabel lblSelectedColor;

	private Color selectedColor = Color.white;
	private WalletModel selectedWM = null;

	public PanelWallet() {
//		createUi();
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
		lPanel.setPreferredSize(dimension);

		JPanel rPanel = new JPanel();
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
		rPanel.setPreferredSize(dimension);

		listViewWallet = new JList<>();
		listViewWallet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listViewWallet.setCellRenderer(new WalletRenderer());
		listViewWallet.setListData(DataController.wallets());

		scrollPane = new JScrollPane(listViewWallet);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(dimension);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lPanel.add(scrollPane);

		// -------------------------------------------------------------

		// Right Part
		JLabel lblTitle = new JLabel("Manage Wallet");
		lblTitle.setFont(new Font("Default", Font.PLAIN, 24));
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		rPanel.add(lblTitle);
		rPanel.add(Box.createVerticalStrut(16));

		//
		JPanel namePanel = new JPanel(new GridLayout(1, 0));
		namePanel.setBorder(new TitledBorder("Enter Wallet Name *"));
		namePanel.setMaximumSize(new Dimension(halfOfWidth - 24, 60));

		// Wallet Name Input
		txtWalletName = new JTextField(20);
		txtWalletName.setFont(new Font("Default", Font.PLAIN, 13));
		txtWalletName.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		namePanel.add(txtWalletName, BorderLayout.CENTER);
		rPanel.add(namePanel);
		rPanel.add(Box.createVerticalStrut(16));

		// -------------------------------------------------------------

		//
		JPanel colorPanel = new JPanel();
		colorPanel.setBorder(new TitledBorder("Select Wallet Color"));
		colorPanel.setLayout(new GridLayout(1, 0));
		colorPanel.setMaximumSize(new Dimension(halfOfWidth - 24, 60));

		// Color picker and preview
		JButton btnColorChooserButton = new JButton();
		btnColorChooserButton.setIcon(new ImageIcon(MyIcons.logo_color_48));
		colorPanel.add(btnColorChooserButton);

		JPanel selectedColorPanel = new JPanel(new BorderLayout());
		selectedColorPanel.setBorder(new EmptyBorder(10, 10, 10, 0));

		lblSelectedColor = new JLabel(MyColors.colorToRGBString(selectedColor));
		lblSelectedColor.setFont(new Font("Default", Font.PLAIN, 13));

		circularSelectedColorPanel = new CircularPanel();
		selectedColorPanel.add(lblSelectedColor, BorderLayout.WEST);
		selectedColorPanel.add(circularSelectedColorPanel, BorderLayout.CENTER);
		colorPanel.add(selectedColorPanel);

		rPanel.add(colorPanel);
		rPanel.add(Box.createVerticalStrut(88));

		// -------------------------------------------------------------

		//
		JPanel btnsPanel = new JPanel(new GridLayout(1, 3));
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
					lblSelectedColor.setText(MyColors.colorToRGBString(selectedColor));
					circularSelectedColorPanel.setBackgroundColor(selectedColor);
				}
			}
		});

		btnColorChooserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(btnColorChooserButton, "Choose", getBackground());
				if (color != null) {
					selectedColor = color;
					lblSelectedColor.setText(MyColors.colorToRGBString(color));
					circularSelectedColorPanel.setBackgroundColor(color);
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
		lblSelectedColor.setText(MyColors.colorToRGBString(Color.white));
		circularSelectedColorPanel.setBackgroundColor(Color.white);
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
		lblSelectedColor.setText(MyColors.colorToRGBString(Color.white));
		circularSelectedColorPanel.setBackgroundColor(Color.white);

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
		lblSelectedColor.setText(MyColors.colorToRGBString(Color.white));
		circularSelectedColorPanel.setBackgroundColor(Color.white);
	}

}
