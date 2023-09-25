package yenom;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
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
	private JPanel selectedColorPanel;
	private JTextField txtAmount;
	private JTextField txtDescription;

	private JScrollPane scrollPane;

	private TransactionModel transactionModel = null;

	private List<WalletModel> walletList = new ArrayList<>();
	private List<CategoryModel> categoryList = new ArrayList<>();
	private int selectedWalletIndex;
	private int selectedCategoryIndex;

	/**
	 * Create the panel.
	 */
	public PanelTransaction() {
		createUi();
	}

	@Override
	public void disposeUi() {
		transactionModel = null;
		setVisible(false);
	}

	@Override
	public void createUi() {
		System.out.println("PanelTransaction : createUi()");
		setVisible(true);
		setBounds(6, 0, 862, 564);
		setLayout(null);
		setComboBoxData();

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

	}

	private void setComboBoxData() {

	}

}
