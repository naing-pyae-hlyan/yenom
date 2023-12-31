package yenom;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import adapter.DrawerButtonMouseAdapter;
import database.DbHelper;
import renderer.*;
import utils.*;

public class Home extends JFrame {
	private static final long serialVersionUID = 1L;

	private int drawerWidth = 212;

	private String selectedLeftPanelName;

//	/*
//	 * Menu Panel
//	 */
	private JPanel leftDashboard;
	private JPanel leftTransactions;
	private JPanel leftCategories;
	private JPanel leftWallets;

	/*
	 * Viewer Panel
	 */
	private PanelDashboard panelDashboard = new PanelDashboard();
	private PanelTransaction panelTransaction = new PanelTransaction();
	private PanelCategory panelCategory = new PanelCategory();
	private PanelWallet panelWallet = new PanelWallet();

	private static int appWidth = 0;
	private static int appHeight = 0;
	private int rightPanelWidth = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setTitle("Yenom-Project");
					// Get the screen size of computer

					frame.setSize(new Dimension(appWidth, appHeight));
					frame.setResizable(false);
					frame.setVisible(true);

					DbHelper.connect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Home() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		appWidth = (int) (screenSize.getWidth() * 0.8);
		appHeight = (int) (screenSize.getHeight() * 0.8);
		drawerWidth = (int) (appWidth * 0.15);
		rightPanelWidth = (int) (appWidth * 0.85);

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelLeft = new JPanel();
		panelLeft.setBackground(MyColors.primaryColor());
		panelLeft.setLayout(null);

		JPanel panelRight = new JPanel();
		panelRight.setLayout(null);
		panelRight.add(panelDashboard);
		panelRight.add(panelTransaction);
		panelRight.add(panelCategory);
		panelRight.add(panelWallet);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0);
		splitPane.setEnabled(false);
		splitPane.setLeftComponent(panelLeft);
		splitPane.setRightComponent(panelRight);
		splitPane.setOneTouchExpandable(false);
		splitPane.setContinuousLayout(true);
		splitPane.setDividerSize(0);
		splitPane.setDividerLocation(drawerWidth);
		setContentPane(splitPane);

		/*
		 * Menu
		 */

		JLabel lblDrawerIcon = new JLabel("");
		lblDrawerIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrawerIcon.setIcon(new ImageIcon(MyIcons.logo_accounting));
		lblDrawerIcon.setBounds(0, 8, drawerWidth, 90);
		panelLeft.add(lblDrawerIcon);

		/*
		 * Panel Dashboard
		 */

		leftDashboard = new JPanel();
		leftDashboard.setName("leftDashboard");
		leftDashboard.setBounds(0, 105, drawerWidth, 42);
		leftDashboard.setBackground(MyColors.secondaryColor());
		leftDashboard.setLayout(null);

		JLabel lblDashboard = new JLabel("Dashboard");
		lblDashboard.setBounds(52, 6, 142, 30);
		lblDashboard.setFont(new Font("Default", Font.PLAIN, 13));
		leftDashboard.add(lblDashboard);

		JLabel lblDashboardLogo = new JLabel("");
		lblDashboardLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashboardLogo.setIcon(new ImageIcon(MyIcons.logo_dashboard));
		lblDashboardLogo.setBounds(16, 6, 30, 30);
		leftDashboard.add(lblDashboardLogo);
		panelLeft.add(leftDashboard);

		/*
		 * Panel Transaction
		 */

		leftTransactions = new JPanel();
		leftTransactions.setName("leftTransactions");
		leftTransactions.setBounds(0, 147, drawerWidth, 42);
		leftTransactions.setBackground(MyColors.primaryColor());
		leftTransactions.setLayout(null);
		panelLeft.add(leftTransactions);

		JLabel lblTransactions = new JLabel("Transactions");
		lblTransactions.setFont(new Font("Default", Font.PLAIN, 13));
		lblTransactions.setBounds(52, 6, 142, 30);
		leftTransactions.add(lblTransactions);

		JLabel lblTransactionsLogo = new JLabel("");
		lblTransactionsLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransactionsLogo.setIcon(new ImageIcon(MyIcons.logo_transactions));
		lblTransactionsLogo.setBounds(16, 6, 30, 30);
		leftTransactions.add(lblTransactionsLogo);

		/*
		 * Panel Category
		 */

		leftCategories = new JPanel();
		leftCategories.setName("leftCategories");
		leftCategories.setBounds(0, 189, drawerWidth, 42);
		leftCategories.setBackground(MyColors.primaryColor());
		leftCategories.setLayout(null);
		panelLeft.add(leftCategories);

		JLabel lblCategories = new JLabel("Categories");
		lblCategories.setFont(new Font("Default", Font.PLAIN, 13));
		lblCategories.setBounds(52, 6, 142, 30);
		leftCategories.add(lblCategories);

		JLabel lblCategoriesLogo = new JLabel("");
		lblCategoriesLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoriesLogo.setIcon(new ImageIcon(MyIcons.logo_categories));
		lblCategoriesLogo.setBounds(16, 6, 30, 30);
		leftCategories.add(lblCategoriesLogo);

		/*
		 * Panel Wallet
		 */

		leftWallets = new JPanel();
		leftWallets.setName("leftWallets");
		leftWallets.setBounds(0, 231, drawerWidth, 42);
		leftWallets.setBackground(MyColors.primaryColor());
		leftWallets.setLayout(null);
		panelLeft.add(leftWallets);

		JLabel lblWallets = new JLabel("Wallets");
		lblWallets.setFont(new Font("Default", Font.PLAIN, 13));
		lblWallets.setBounds(52, 6, 142, 30);
		leftWallets.add(lblWallets);

		JLabel lblWalletsLogo = new JLabel("");
		lblWalletsLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblWalletsLogo.setIcon(new ImageIcon(MyIcons.logo_wallets));
		lblWalletsLogo.setBounds(16, 6, 30, 30);
		leftWallets.add(lblWalletsLogo);

		selectedLeftPanelName = leftDashboard.getName();

		// Start create dashboard UI
		panelDashboard.createUi(new Dimension(rightPanelWidth, appHeight - 42));

		leftDashboard.addMouseListener(new DrawerButtonMouseAdapter(leftDashboard) {
			@Override
			public void mouseClicked(MouseEvent e) {
				whenMenuItemisClicked(leftDashboard, panelDashboard, "Dashboard");
			}
		});

		leftTransactions.addMouseListener(new DrawerButtonMouseAdapter(leftTransactions) {
			@Override
			public void mouseClicked(MouseEvent e) {
				whenMenuItemisClicked(leftTransactions, panelTransaction, "Transaction");
			}
		});

		leftCategories.addMouseListener(new DrawerButtonMouseAdapter(leftCategories) {
			@Override
			public void mouseClicked(MouseEvent e) {
				whenMenuItemisClicked(leftCategories, panelCategory, "Category");
			}
		});

		leftWallets.addMouseListener(new DrawerButtonMouseAdapter(leftWallets) {
			@Override
			public void mouseClicked(MouseEvent e) {
				whenMenuItemisClicked(leftWallets, panelWallet, "Wallet");
			}
		});

	}

	private void whenMenuItemisClicked(JPanel leftPanelItem, BaseJPanel rightPanel, String arg) {
		if (selectedLeftPanelName == leftPanelItem.getName())
			return;
		selectedLeftPanelName = leftPanelItem.getName();

		panelDashboard.disposeUi();
		panelTransaction.disposeUi();
		panelCategory.disposeUi();
		panelWallet.disposeUi();

		rightPanel.createUi(new Dimension(rightPanelWidth, appHeight - 42));

		leftDashboard.setBackground(MyColors.primaryColor());
		leftTransactions.setBackground(MyColors.primaryColor());
		leftCategories.setBackground(MyColors.primaryColor());
		leftWallets.setBackground(MyColors.primaryColor());

		leftPanelItem.setBackground(MyColors.secondaryColor());
	}
}
