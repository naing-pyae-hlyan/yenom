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
import javax.swing.ButtonGroup;
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
import javax.swing.JRadioButton;

import utils.*;
import database.*;
import renderer.*;

public class PanelCategory extends BaseJPanel {

	private static final long serialVersionUID = 1L;

	private JList<CategoryModel> listViewCategory;

	private JTextField txtCategoryName;
	private JScrollPane scrollPane;

	private CircularPanel circularSelectedColorPanel;
	private JLabel lblSelectedColor;

	private JRadioButton radioBtnExpense;
	private JRadioButton radioBtnIncome;

	private Color selectedColor = Color.white;
	private CategoryModel selectedCM = null;

	private EIEnum eiEnum = EIEnum.expense;

	/**
	 * Create the panel.
	 */
	public PanelCategory() {

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

		listViewCategory = new JList<>();
		listViewCategory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listViewCategory.setCellRenderer(new CategoryRenderer());
		listViewCategory.setListData(DataController.categories());

		scrollPane = new JScrollPane(listViewCategory);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(dimension);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lPanel.add(scrollPane);

		// -------------------------------------------------------------

		// Right Part
		JLabel lblTitle = new JLabel("Manage Category");
		lblTitle.setFont(new Font("Default", Font.PLAIN, 24));
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		rPanel.add(lblTitle);
		rPanel.add(Box.createVerticalStrut(16));

		//
		JPanel namePanel = new JPanel(new GridLayout(1, 0));
		namePanel.setBorder(new TitledBorder("Enter Category Name *"));
		namePanel.setMaximumSize(new Dimension(halfOfWidth - 24, 60));

		// Wallet Name Input
		txtCategoryName = new JTextField(20);
		txtCategoryName.setFont(new Font("Default", Font.PLAIN, 13));
		txtCategoryName.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		namePanel.add(txtCategoryName, BorderLayout.CENTER);
		rPanel.add(namePanel);
		rPanel.add(Box.createVerticalStrut(16));

		// -------------------------------------------------------------

		//
		JPanel colorPanel = new JPanel(new GridLayout(1, 0));
		colorPanel.setBorder(new TitledBorder("Select Category Color"));
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
		rPanel.add(Box.createVerticalStrut(16));

		// -------------------------------------------------------------

		//
		JPanel radiosPanel = new JPanel(new BorderLayout());
		radiosPanel.setBorder(new EmptyBorder(5, 0, 5, 5));
		radiosPanel.setMaximumSize(new Dimension(halfOfWidth - 24, 60));

		radioBtnExpense = new JRadioButton("Expense");
		radioBtnExpense.setActionCommand("expense");
		radioBtnExpense.setSelected(true);
		radiosPanel.add(radioBtnExpense, BorderLayout.NORTH);

		radioBtnIncome = new JRadioButton("Income");
		radioBtnIncome.setActionCommand("income");
		radioBtnIncome.setSelected(false);
		radiosPanel.add(radioBtnIncome, BorderLayout.SOUTH);

		ButtonGroup bg = new ButtonGroup();
		bg.add(radioBtnExpense);
		bg.add(radioBtnIncome);

		rPanel.add(radiosPanel);
		rPanel.add(Box.createVerticalStrut(16));
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

		listViewCategory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (listViewCategory.isSelectionEmpty()) {
					return;
				}

				int index = listViewCategory.locationToIndex(e.getPoint());

				if (index < 0) {
					return;
				}

				selectedCM = listViewCategory.getModel().getElementAt(index);
				if (selectedCM != null) {
					txtCategoryName.setText(selectedCM.getName());
					selectedColor = new Color(selectedCM.getColor());
					lblSelectedColor.setText(MyColors.colorToRGBString(selectedColor));
					circularSelectedColorPanel.setBackgroundColor(selectedColor);

					final boolean isIncome = selectedCM.isIncome();
					eiEnum = isIncome ? EIEnum.income : EIEnum.expense;
					radioBtnIncome.setSelected(isIncome);
					radioBtnExpense.setSelected(!isIncome);
				}
			}
		});

		btnColorChooserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(btnColorChooserButton, "Choose", getBackground());
				if (color != null) {
					selectedColor = color;
					lblSelectedColor.setText(MyColors.colorToRGBString(color));
					circularSelectedColorPanel.setBackgroundColor(color);
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
				boolean isIncome = eiEnum == EIEnum.income ? true : false;
				addCategory(name, color, isIncome);
			}
		});

		btnUpdateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listViewCategory.isSelectionEmpty()) {
					return;
				}

				String name = txtCategoryName.getText();
				int color = selectedColor.getRGB();
				boolean isIncome = eiEnum == EIEnum.income ? true : false;
				updateCategory(selectedCM, name, color, isIncome);
			}
		});

		btnDeleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listViewCategory.isSelectionEmpty()) {
					return;
				}

				deleteCategory(selectedCM);
			}
		});
	}

	private void addCategory(String name, int color, boolean isIncome) {
		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter category name!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (selectedCM != null && new String(name).equals(selectedCM.getName())) {
			JOptionPane.showMessageDialog(this, "Please enter new category name!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (int i = 0, l = listViewCategory.getModel().getSize(); i < l; i++) {
			CategoryModel categoryModel = listViewCategory.getModel().getElementAt(i);
			if (new String(name).equals(categoryModel.getName())) {
				JOptionPane.showMessageDialog(this, "Please enter new category name!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		String sql = "INSERT INTO category (c_name, c_color, is_income) VALUES (?, ?, ?)";
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
		listViewCategory.setListData(DataController.categories());

		// Clear category name text field
		txtCategoryName.setText("");
		selectedColor = Color.white;
		lblSelectedColor.setText(MyColors.colorToRGBString(Color.white));
		circularSelectedColorPanel.setBackgroundColor(Color.white);
	}

	private void updateCategory(CategoryModel wm, String name, int color, boolean isIncome) {
		if (wm == null) {
			JOptionPane.showMessageDialog(this, "Please select category!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String sql = "UPDATE category SET c_name = ?, c_color = ?, is_income = ? WHERE c_id = ?";
		try {
			Connection connection = DbHelper.connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			statement.setInt(2, color);
			statement.setBoolean(3, isIncome);
			statement.setInt(4, wm.getId());
			statement.executeUpdate();

		} catch (SQLException e) {
			DbHelper.printSQLException(e);
		}
		// refresh the wallet list
		listViewCategory.setListData(DataController.categories());
		// Clear wallet name text field
		txtCategoryName.setText("");
		// Clear wallet color panel
		selectedColor = Color.white;
		lblSelectedColor.setText(MyColors.colorToRGBString(Color.white));
		circularSelectedColorPanel.setBackgroundColor(Color.white);

	}

	private void deleteCategory(CategoryModel cm) {
		if (cm == null) {
			JOptionPane.showMessageDialog(this, "Please select category!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String sql = "DELETE FROM category WHERE c_id = ?";
		try {
			Connection connection = DbHelper.connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, cm.getId());
			statement.executeUpdate();

		} catch (SQLException ee) {
			DbHelper.printSQLException(ee);
		}
		// refresh the wallet list
		listViewCategory.setListData(DataController.categories());

		// Clear wallet name text field
		txtCategoryName.setText("");
		// Clear wallet color panel
		selectedColor = Color.white;
		lblSelectedColor.setText(MyColors.colorToRGBString(Color.white));
		circularSelectedColorPanel.setBackgroundColor(Color.white);
	}

	enum EIEnum {
		expense, income
	}
}
