package yenom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.*;
import database.*;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.JTextField;

public class PanelWallet extends JPanel {

	private static final long serialVersionUID = 1L;
	private JList<WalletModel> listWallet;
	private JTextField textField;
	private JColorChooser jcc;

	/**
	 * Create the panel.
	 */
	public PanelWallet() {
		setBounds(6, 0, 862, 572);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane(listWallet = createWallet());
		scrollPane.setBounds(6, 6, 430, 560);
		add(scrollPane);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(505, 301, 72, 72);
		btnNewButton.setIcon(new ImageIcon(MyIcons.logo_add_64));
		add(btnNewButton);

		JButton btnUpdateButton = new JButton("");
		btnUpdateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdateButton.setBounds(619, 301, 72, 72);
		btnUpdateButton.setIcon(new ImageIcon(MyIcons.logo_update_64));
		add(btnUpdateButton);

		JButton btnDeleteButton = new JButton("");
		btnDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDeleteButton.setBounds(733, 301, 72, 72);
		btnDeleteButton.setIcon(new ImageIcon(MyIcons.logo_delete_64));
		add(btnDeleteButton);
		
		textField = new JTextField("Wallet Name");
		textField.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(446, 172, 408, 59);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Manage Wallet");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 24));
		lblNewLabel.setBounds(531, 6, 237, 33);
		add(lblNewLabel);
		
	}

	private JList<WalletModel> createWallet() {
		DefaultListModel<WalletModel> list = new DefaultListModel<>();

		list.addElement(new WalletModel(1, "Test", "123,123,123", 0, 0));
		list.addElement(new WalletModel(2, "Test", "123,123,123", 0, 0));
		list.addElement(new WalletModel(3, "Test", "123,123,123", 0, 0));
		list.addElement(new WalletModel(4, "Test", "123,123,123", 0, 0));

		JList<WalletModel> wallets = new JList<WalletModel>(list);
		wallets.setCellRenderer(new WalletRenderer());

		return wallets;
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
