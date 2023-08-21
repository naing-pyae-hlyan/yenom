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

import javax.swing.ButtonGroup;
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
import javax.swing.JRadioButton;

public class PanelCategory extends JPanel {

	private static final long serialVersionUID = 1L;
	private JList<CategoryModel> listCategory;
	private JPanel selectedColorPanel;
	private JTextField txtCategoryName;
	private JScrollPane scrollPane;
	private JRadioButton radioBtnExpense;
	private JRadioButton radioBtnIncome;

	private Color selectedColor = Color.white;
	private WalletModel selectedWM = null;
	private EIEnum eiEnum = EIEnum.expense;

	/**
	 * Create the panel.
	 */
	public PanelCategory() {
		setBounds(6, 0, 862, 564);
		setLayout(null);

		listCategory = new JList<>();
		listCategory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// set WalletRender for custom widget
		listCategory.setCellRenderer(new WalletRenderer());
		listCategory.setListData(getCategories());

		scrollPane = new JScrollPane(listCategory); // load wallet list
		scrollPane.setBounds(6, 6, 430, 560);
		add(scrollPane);

		JLabel lblNewLabel = new JLabel("Manage Category");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 24));
		lblNewLabel.setBounds(531, 6, 237, 33);
		add(lblNewLabel);

		JLabel lblTextField = new JLabel("Category Name *");
		lblTextField.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
		lblTextField.setBounds(503, 79, 159, 16);
		add(lblTextField);

		txtCategoryName = new JTextField("");
		txtCategoryName.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
		txtCategoryName.setHorizontalAlignment(SwingConstants.LEFT);
		txtCategoryName.setBounds(503, 107, 292, 64);
		add(txtCategoryName);
		txtCategoryName.setColumns(10);

		JLabel lblTextfield2 = new JLabel("Category Color");
		lblTextfield2.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
		lblTextfield2.setBounds(503, 183, 114, 16);
		add(lblTextfield2);

		selectedColorPanel = new JPanel();
		selectedColorPanel.setBounds(503, 211, 178, 60);
		selectedColorPanel.setBackground(Color.white);
		add(selectedColorPanel);

		JButton btnColorButton = new JButton("");
		btnColorButton.setBounds(731, 207, 64, 64);
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

		radioBtnExpense = new JRadioButton("Expense");
		radioBtnExpense.setBounds(502, 315, 141, 23);
		radioBtnExpense.setActionCommand("expense");
		radioBtnExpense.setSelected(true);
		add(radioBtnExpense);

		radioBtnIncome = new JRadioButton("Income");
		radioBtnIncome.setBounds(654, 315, 141, 23);
		radioBtnIncome.setActionCommand("income");
		radioBtnIncome.setSelected(false);
		add(radioBtnIncome);

		ButtonGroup bg = new ButtonGroup();
		bg.add(radioBtnExpense);
		bg.add(radioBtnIncome);

		listCategory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				int index = listCategory.locationToIndex(e.getPoint());
//				selectedWM = listCategory.getModel().getElementAt(index);
//				if (selectedWM != null) {
//					txtWalletName.setText(selectedWM.getName());
//					selectedColor = new Color(selectedWM.getColor());
//					selectedColorPanel.setBackground(selectedColor);
//				}
			}
		});

		btnColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(btnColorButton, "Choose", getBackground());
				if (color != null) {
					selectedColor = color;
					selectedColorPanel.setBackground(color);
				}
			}
		});

		radioBtnExpense.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eiEnum = EIEnum.expense;
			}
		});

		radioBtnIncome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eiEnum = EIEnum.income;

			}
		});

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtCategoryName.getText();
				int color = selectedColor.getRGB();
				boolean isIncome = eiEnum == EIEnum.income;
				addCategory(name, color, isIncome);
			}
		});

		btnUpdateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtCategoryName.getText();
				int color = selectedColor.getRGB();
//				updateWallet(selectedWM, name, color);
			}
		});

		btnDeleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
	}

	private CategoryModel[] getCategories() {
		String sql = "SELECT * FROM category";
		try {
			Connection connection = DbHelper.connection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			List<CategoryModel> categories = new ArrayList<>();
			while (result.next()) {
				final int id = result.getInt("id");
				final String name = result.getString("name");
				final int color = result.getInt("color");
				final boolean isIncome = result.getBoolean("is_income");
				categories.add(new CategoryModel(id, name, color, isIncome));

			}

			return categories.toArray(new CategoryModel[0]);
		} catch (SQLException e) {
			DbHelper.printSQLException(e);
		}

		return new CategoryModel[0];
	}

	private void addCategory(String name, int color, boolean isIncome) {
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter category name", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String sql = "INSERT INTO category (name, color, is_income) VALUES (?, ?, ?)";
		try {
			Connection connection = DbHelper.connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setInt(2, color);
			statement.setBoolean(3, isIncome);
			statement.executeUpdate();
		} catch (SQLException e) {
			DbHelper.printSQLException(e);
		}

		// refresh the category list
		listCategory.setListData(getCategories());

		// Clear category name text field
		txtCategoryName.setText("");
		selectedColor = Color.white;
		selectedColorPanel.setBackground(Color.white);

	}

	public class WalletRenderer extends JPanel implements ListCellRenderer<CategoryModel> {

		private static final long serialVersionUID = 1L;

		private JLabel lblIcon = new JLabel();
		private JLabel lblSpacer = new JLabel();
		private JLabel lblName = new JLabel();

		public WalletRenderer() {
			setLayout(new BorderLayout(0, 0));

			JPanel panel = new JPanel(new GridLayout(0, 1));
			panel.add(lblName);

			add(lblIcon, BorderLayout.WEST);
			add(lblSpacer);
			add(panel, BorderLayout.CENTER);

		}

		@Override
		public Component getListCellRendererComponent(JList<? extends CategoryModel> list, CategoryModel value,
				int index, boolean isSelected, boolean cellHasFocus) {
			lblIcon.setIcon(new ImageIcon(MyIcons.logo_categories_64));
			lblSpacer.setText("   ");
			lblName.setFont(new Font("JetBrains Mono", Font.PLAIN, 24));
			lblName.setText(value.getName());

			lblName.setOpaque(true);
			lblSpacer.setOpaque(true);
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

	enum EIEnum {
		expense, income
	}
}
