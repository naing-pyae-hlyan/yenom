package yenom;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

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
//	private PanelTrash trash;
//	private PanelSetting setting;

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
//					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//					frame.setSize(screenSize);
//					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setVisible(true);
					DbHelper.connect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Home() {
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1075, 600);

		JPanel panelLeft = new JPanel();
		panelLeft.setPreferredSize(new Dimension(drawerWidth, 0));
		panelLeft.setBackground(MyColors.primaryColor());
		panelLeft.setLayout(null);

		JPanel panelRight = new JPanel();
		panelRight.setLayout(null);
		panelRight.add(panelDashboard);
		panelRight.add(panelTransaction);
		panelRight.add(panelCategory);
		panelRight.add(panelWallet);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.2);
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

		leftDashboard.addMouseListener(new DrawerButtonMouseAdapter(leftDashboard) {
			@Override
			public void mouseClicked(MouseEvent e) {
				whenMenuItemisClicked(leftDashboard, panelDashboard);
//				whenClickedMenuShowThePanel(dashboard, drawerDashboard);
			}
		});

		leftTransactions.addMouseListener(new DrawerButtonMouseAdapter(leftTransactions) {
			@Override
			public void mouseClicked(MouseEvent e) {
				whenMenuItemisClicked(leftTransactions, panelTransaction);
//				whenClickedMenuShowThePanel(transaction, drawerTransactions);
			}
		});

		leftCategories.addMouseListener(new DrawerButtonMouseAdapter(leftCategories) {
			@Override
			public void mouseClicked(MouseEvent e) {
				whenMenuItemisClicked(leftCategories, panelCategory);
//				whenClickedMenuShowThePanel(category, drawerCategories);
			}
		});

		leftWallets.addMouseListener(new DrawerButtonMouseAdapter(leftWallets) {
			@Override
			public void mouseClicked(MouseEvent e) {
				whenMenuItemisClicked(leftWallets, panelWallet);
//				whenClickedMenuShowThePanel(wallet, drawerWallets);
			}
		});

	}

	private void whenMenuItemisClicked(JPanel leftPanelItem, BaseJPanel rightPanel) {
		if (selectedLeftPanelName == leftPanelItem.getName())
			return;
		selectedLeftPanelName = leftPanelItem.getName();

		panelDashboard.disposeUi();
		panelTransaction.disposeUi();
		panelCategory.disposeUi();
		panelWallet.disposeUi();

		rightPanel.createUi();

		leftDashboard.setBackground(MyColors.primaryColor());
		leftTransactions.setBackground(MyColors.primaryColor());
		leftCategories.setBackground(MyColors.primaryColor());
		leftWallets.setBackground(MyColors.primaryColor());

		leftPanelItem.setBackground(MyColors.secondaryColor());
	}
}

//
//public class Home extends JFrame {
//
//	private static final long serialVersionUID = 1L;
//
//	private int drawerWidth = 212;
//
//	private JPanel selectedPanelViewer;
//
//	/*
//	 * Viewer Panel
//	 */
//	private PanelDashboard dashboard;
//	private PanelTransaction transaction;
//	private PanelCategory category;
//	private PanelWallet wallet;
//	private PanelTrash trash;
//	private PanelSetting setting;
//
//	/*
//	 * Menu Panel
//	 */
//	private JPanel drawerDashboard;
//	private JPanel drawerTransactions;
//	private JPanel drawerCategories;
//	private JPanel drawerWallets;
//	private JPanel drawerTrash;
//	private JPanel drawerSettings;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Home frame = new Home();
//					frame.setVisible(true);
//					DbHelper.connect();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
//	public Home() {
////		setUndecorated(true); //  for close, hide, minimize button 
//		setBackground(new Color(255, 255, 255));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 1075, 600);
//
//		JPanel contentPanel = new JPanel();
//		contentPanel.setBackground(new Color(255, 255, 255));
//
//		setContentPane(contentPanel);
//		contentPanel.setLayout(null);
//
//		dashboard = new PanelDashboard();
//		transaction = new PanelTransaction();
//		category = new PanelCategory();
//		wallet = new PanelWallet();
//		trash = new PanelTrash();
//		setting = new PanelSetting();;
//
//		/*
//		 * Menu
//		 */
//		JPanel panelDrawer = new JPanel();
//		panelDrawer.setBounds(0, 0, 212, 572);
//		panelDrawer.setBackground(MyColors.primaryColor());
//		contentPanel.add(panelDrawer);
//		panelDrawer.setLayout(null);
//
//		JLabel lblDrawerIcon = new JLabel("");
//		lblDrawerIcon.setHorizontalAlignment(SwingConstants.CENTER);
//		lblDrawerIcon.setIcon(new ImageIcon(MyIcons.logo_accounting));
//		lblDrawerIcon.setBounds(0, 0, drawerWidth, 105);
//		panelDrawer.add(lblDrawerIcon);
//
//		/*
//		 * Panel Dashboard
//		 */
//
//		drawerDashboard = new JPanel();
//		drawerDashboard.setBounds(0, 105, drawerWidth, 42);
//		drawerDashboard.setBackground(MyColors.primaryColor());
//		drawerDashboard.setLayout(null);
//		panelDrawer.add(drawerDashboard);
//
//		JLabel lblDashboard = new JLabel("Dashboard");
//		lblDashboard.setBounds(52, 6, 142, 30);
//		lblDashboard.setFont(new Font("Default", Font.PLAIN, 13));
//		drawerDashboard.add(lblDashboard);  
//
//		JLabel lblDashboardLogo = new JLabel("");
//		lblDashboardLogo.setHorizontalAlignment(SwingConstants.CENTER);
//		lblDashboardLogo.setIcon(new ImageIcon(MyIcons.logo_dashboard));
//		lblDashboardLogo.setBounds(16, 6, 30, 30);
//		drawerDashboard.add(lblDashboardLogo);
//
//		/*
//		 * Panel Transaction
//		 */
//
//		drawerTransactions = new JPanel();
//		drawerTransactions.setBounds(0, 147, drawerWidth, 42);
//		drawerTransactions.setBackground(MyColors.primaryColor());
//		drawerTransactions.setLayout(null);
//		panelDrawer.add(drawerTransactions);
//
//		JLabel lblTransactions = new JLabel("Transactions");
//		lblTransactions.setFont(new Font("Default", Font.PLAIN, 13));
//		lblTransactions.setBounds(52, 6, 142, 30);
//		drawerTransactions.add(lblTransactions);
//
//		JLabel lblTransactionsLogo = new JLabel("");
//		lblTransactionsLogo.setHorizontalAlignment(SwingConstants.CENTER);
//		lblTransactionsLogo.setIcon(new ImageIcon(MyIcons.logo_transactions));
//		lblTransactionsLogo.setBounds(16, 6, 30, 30);
//		drawerTransactions.add(lblTransactionsLogo);
//
//		/*
//		 * Panel Category
//		 */
//
//		drawerCategories = new JPanel();
//		drawerCategories.setBounds(0, 189, drawerWidth, 42);
//		drawerCategories.setBackground(MyColors.primaryColor());
//		drawerCategories.setLayout(null);
//		panelDrawer.add(drawerCategories);
//
//		JLabel lblCategories = new JLabel("Categories");
//		lblCategories.setFont(new Font("Default", Font.PLAIN, 13));
//		lblCategories.setBounds(52, 6, 142, 30);
//		drawerCategories.add(lblCategories);
//
//		JLabel lblCategoriesLogo = new JLabel("");
//		lblCategoriesLogo.setHorizontalAlignment(SwingConstants.CENTER);
//		lblCategoriesLogo.setIcon(new ImageIcon(MyIcons.logo_categories));
//		lblCategoriesLogo.setBounds(16, 6, 30, 30);
//		drawerCategories.add(lblCategoriesLogo);
//
//		/*
//		 * Panel Wallet
//		 */
//
//		drawerWallets = new JPanel();
//		drawerWallets.setBounds(0, 231, drawerWidth, 42);
//		drawerWallets.setBackground(MyColors.primaryColor());
//		drawerWallets.setLayout(null);
//		panelDrawer.add(drawerWallets);
//
//		JLabel lblWallets = new JLabel("Wallets");
//		lblWallets.setFont(new Font("Default", Font.PLAIN, 13));
//		lblWallets.setBounds(52, 6, 142, 30);
//		drawerWallets.add(lblWallets);
//
//		JLabel lblWalletsLogo = new JLabel("");
//		lblWalletsLogo.setHorizontalAlignment(SwingConstants.CENTER);
//		lblWalletsLogo.setIcon(new ImageIcon(MyIcons.logo_wallets));
//		lblWalletsLogo.setBounds(16, 6, 30, 30);
//		drawerWallets.add(lblWalletsLogo);
//
//		/*
//		 * Panel Trash
//		 */
//
//		drawerTrash = new JPanel();
//		drawerTrash.setBounds(0, 273, drawerWidth, 42);
//		drawerTrash.setBackground(MyColors.primaryColor());
//		drawerTrash.setLayout(null);
//		panelDrawer.add(drawerTrash);
//
//		JLabel lblTrash = new JLabel("Trash");
//		lblTrash.setFont(new Font("Default", Font.PLAIN, 13));
//		lblTrash.setBounds(52, 6, 142, 30);
//		drawerTrash.add(lblTrash);
//
//		JLabel lblTrashLogo = new JLabel("");
//		lblTrashLogo.setHorizontalAlignment(SwingConstants.CENTER);
//		lblTrashLogo.setIcon(new ImageIcon(MyIcons.logo_trash));
//		lblTrashLogo.setBounds(16, 6, 30, 30);
//		drawerTrash.add(lblTrashLogo);
//
//		/*
//		 * Panel Setting
//		 */
//
//		drawerSettings = new JPanel();
//		drawerSettings.setBounds(0, 315, drawerWidth, 42);
//		drawerSettings.setBackground(MyColors.primaryColor());
//		drawerSettings.setLayout(null);
//		panelDrawer.add(drawerSettings);
//
//		JLabel lblSettings = new JLabel("Settings");
//		lblSettings.setFont(new Font("Default", Font.PLAIN, 13));
//		lblSettings.setBounds(52, 6, 142, 30);
//		drawerSettings.add(lblSettings);
//
//		JLabel lblSettingsLogo = new JLabel("");
//		lblSettingsLogo.setHorizontalAlignment(SwingConstants.CENTER);
//		lblSettingsLogo.setIcon(new ImageIcon(MyIcons.logo_settings));
//		lblSettingsLogo.setBounds(16, 6, 30, 30);
//		drawerSettings.add(lblSettingsLogo);
//
//		/*
//		 * Panel Viewer
//		 */
//
//		JPanel panelViewer = new JPanel();
//		panelViewer.setBounds(212, 0, 862, 572);
//		contentPanel.add(panelViewer);
//		panelViewer.setLayout(null);
////
//		panelViewer.add(dashboard);
//		panelViewer.add(transaction);
//		panelViewer.add(category);
//		panelViewer.add(wallet);
//		panelViewer.add(trash);
//		panelViewer.add(setting);
//
//
//		selectedPanelViewer = dashboard;
//		whenClickedMenuShowThePanel(dashboard, drawerDashboard);
//
//		drawerDashboard.addMouseListener(new DrawerButtonMouseAdapter(drawerDashboard) {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				whenClickedMenuShowThePanel(dashboard, drawerDashboard);
//			}
//		});
//
//		drawerTransactions.addMouseListener(new DrawerButtonMouseAdapter(drawerTransactions) {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				whenClickedMenuShowThePanel(transaction, drawerTransactions);
//			}
//		});
//
//		drawerCategories.addMouseListener(new DrawerButtonMouseAdapter(drawerCategories) {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				whenClickedMenuShowThePanel(category, drawerCategories);
//			}
//		});
//
//		drawerWallets.addMouseListener(new DrawerButtonMouseAdapter(drawerWallets) {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				whenClickedMenuShowThePanel(wallet, drawerWallets);
//			}
//		});
//
//		drawerTrash.addMouseListener(new DrawerButtonMouseAdapter(drawerTrash) {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				whenClickedMenuShowThePanel(trash, drawerTrash);
//			}
//		});
//
//		drawerSettings.addMouseListener(new DrawerButtonMouseAdapter(drawerSettings) {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				whenClickedMenuShowThePanel(setting, drawerSettings);
//			}
//		});
//	}
//
//	private void whenClickedMenuShowThePanel(JPanel drawer, JPanel menu) {
//		if (selectedPanelViewer == drawer) {
//			return;
//		}
//		selectedPanelViewer = drawer;
//
//		dashboard.setVisible(false);
//		transaction.setVisible(false);
//		category.setVisible(false);
//		wallet.setVisible(false);
//		trash.setVisible(false);
//		setting.setVisible(false);
//
//		drawer.setVisible(true);
//
//
//		whenClickedMenuChangeMenuBackgroundColor(menu);
//	}
//
//	private void whenClickedMenuChangeMenuBackgroundColor(JPanel panel) {
//		drawerDashboard.setBackground(MyColors.primaryColor());
//		drawerTransactions.setBackground(MyColors.primaryColor());
//		drawerCategories.setBackground(MyColors.primaryColor());
//		drawerWallets.setBackground(MyColors.primaryColor());
//		drawerTrash.setBackground(MyColors.primaryColor());
//		drawerSettings.setBackground(MyColors.primaryColor());
//
//		panel.setBackground(MyColors.secondaryColor());
//	}
//}
