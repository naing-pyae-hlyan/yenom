package yenom;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;

import yenom.adapter.DrawerButtonMouseAdapter;

public class Home extends JFrame {

	private Image logo_accounting = new ImageIcon(Home.class.getResource("/res/accounting.png")).getImage()
			.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
	private Image logo_dashboard = new ImageIcon(Home.class.getResource("/res/dashboard.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	private Image logo_transactions = new ImageIcon(Home.class.getResource("/res/transactions.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	private Image logo_categories = new ImageIcon(Home.class.getResource("/res/categories.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	private Image logo_wallets = new ImageIcon(Home.class.getResource("/res/wallets.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	private Image logo_trash = new ImageIcon(Home.class.getResource("/res/trash.png")).getImage().getScaledInstance(24,
			20, Image.SCALE_SMOOTH);
	private Image logo_settings = new ImageIcon(Home.class.getResource("/res/settings.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	private int drawerWidth = 212;

	private static final long serialVersionUID = 1L;

	private JPanel selectedPanelViewer;

	private PanelDashboard dashboard;
	private PanelTransaction transaction;
	private PanelCategory category;
	private PanelWallet wallet;
	private PanelTrash trash;
	private PanelSetting setting;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
//		setUndecorated(true); //  for close, hide, minimize button 
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1075, 600);

		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(new Color(255, 255, 255));

		setContentPane(contentPanel);
		contentPanel.setLayout(null);

		dashboard = new PanelDashboard();
		transaction = new PanelTransaction();
		category = new PanelCategory();
		wallet = new PanelWallet();
		trash = new PanelTrash();
		setting = new PanelSetting();

		/*
		 * Menu
		 */
		JPanel panelDrawer = new JPanel();
		panelDrawer.setBounds(0, 0, 212, 572);
		contentPanel.add(panelDrawer);
		panelDrawer.setLayout(null);

		JLabel lblDrawerIcon = new JLabel("");
		lblDrawerIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblDrawerIcon.setIcon(new ImageIcon(logo_accounting));
		lblDrawerIcon.setBounds(0, 0, drawerWidth, 105);
		panelDrawer.add(lblDrawerIcon);

		/*
		 * Panel Dashboard
		 */

		JPanel drawerDashboard = new JPanel();
		drawerDashboard.addMouseListener(new DrawerButtonMouseAdapter(drawerDashboard) {
			@Override
			public void mouseClicked(MouseEvent e) {
				drawerClickedShowPanel(dashboard);
			}
		});
		drawerDashboard.setBounds(0, 105, drawerWidth, 42);
		drawerDashboard.setLayout(null);
		panelDrawer.add(drawerDashboard);

		JLabel lblDashboard = new JLabel("Dashboard");
		lblDashboard.setBounds(52, 6, 142, 30);
		lblDashboard.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
		drawerDashboard.add(lblDashboard);

		JLabel lblDashboardLogo = new JLabel("");
		lblDashboardLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblDashboardLogo.setIcon(new ImageIcon(logo_dashboard));
		lblDashboardLogo.setBounds(16, 6, 30, 30);
		drawerDashboard.add(lblDashboardLogo);

		/*
		 * Panel Transaction
		 */

		JPanel drawerTransactions = new JPanel();
		drawerTransactions.addMouseListener(new DrawerButtonMouseAdapter(drawerTransactions) {
			@Override
			public void mouseClicked(MouseEvent e) {
				drawerClickedShowPanel(transaction);
			}
		});
		drawerTransactions.setBounds(0, 147, drawerWidth, 42);
		drawerTransactions.setLayout(null);
		panelDrawer.add(drawerTransactions);

		JLabel lblTransactions = new JLabel("Transactions");
		lblTransactions.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
		lblTransactions.setBounds(52, 6, 142, 30);
		drawerTransactions.add(lblTransactions);

		JLabel lblTransactionsLogo = new JLabel("");
		lblTransactionsLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransactionsLogo.setIcon(new ImageIcon(logo_transactions));
		lblTransactionsLogo.setBounds(16, 6, 30, 30);
		drawerTransactions.add(lblTransactionsLogo);

		/*
		 * Panel Category
		 */

		JPanel drawerCategories = new JPanel();
		drawerCategories.addMouseListener(new DrawerButtonMouseAdapter(drawerCategories) {
			@Override
			public void mouseClicked(MouseEvent e) {
				drawerClickedShowPanel(category);
			}
		});
		drawerCategories.setBounds(0, 189, drawerWidth, 42);
		drawerCategories.setLayout(null);
		panelDrawer.add(drawerCategories);

		JLabel lblCategories = new JLabel("Categories");
		lblCategories.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
		lblCategories.setBounds(52, 6, 142, 30);
		drawerCategories.add(lblCategories);

		JLabel lblCategoriesLogo = new JLabel("");
		lblCategoriesLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoriesLogo.setIcon(new ImageIcon(logo_categories));
		lblCategoriesLogo.setBounds(16, 6, 30, 30);
		drawerCategories.add(lblCategoriesLogo);

		/*
		 * Panel Wallet
		 */

		JPanel drawerWallets = new JPanel();
		drawerWallets.addMouseListener(new DrawerButtonMouseAdapter(drawerWallets) {
			@Override
			public void mouseClicked(MouseEvent e) {
				drawerClickedShowPanel(wallet);
			}
		});
		drawerWallets.setBounds(0, 231, drawerWidth, 42);
		drawerWallets.setLayout(null);
		panelDrawer.add(drawerWallets);

		JLabel lblWallets = new JLabel("Wallets");
		lblWallets.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
		lblWallets.setBounds(52, 6, 142, 30);
		drawerWallets.add(lblWallets);

		JLabel lblWalletsLogo = new JLabel("");
		lblWalletsLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblWalletsLogo.setIcon(new ImageIcon(logo_wallets));
		lblWalletsLogo.setBounds(16, 6, 30, 30);
		drawerWallets.add(lblWalletsLogo);

		/*
		 * Panel Trash
		 */

		JPanel drawerTrash = new JPanel();
		drawerTrash.addMouseListener(new DrawerButtonMouseAdapter(drawerTrash) {
			@Override
			public void mouseClicked(MouseEvent e) {
				drawerClickedShowPanel(trash);
			}
		});
		drawerTrash.setBounds(0, 273, drawerWidth, 42);
		drawerTrash.setLayout(null);
		panelDrawer.add(drawerTrash);

		JLabel lblTrash = new JLabel("Trash");
		lblTrash.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
		lblTrash.setBounds(52, 6, 142, 30);
		drawerTrash.add(lblTrash);

		JLabel lblTrashLogo = new JLabel("");
		lblTrashLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrashLogo.setIcon(new ImageIcon(logo_trash));
		lblTrashLogo.setBounds(16, 6, 30, 30);
		drawerTrash.add(lblTrashLogo);

		/*
		 * Panel Setting
		 */

		JPanel drawerSettings = new JPanel();
		drawerSettings.addMouseListener(new DrawerButtonMouseAdapter(drawerSettings) {
			@Override
			public void mouseClicked(MouseEvent e) {
				drawerClickedShowPanel(setting);
			}
		});
		drawerSettings.setBounds(0, 315, drawerWidth, 42);
		drawerSettings.setLayout(null);
		panelDrawer.add(drawerSettings);

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("JetBrains Mono", Font.PLAIN, 13));
		lblSettings.setBounds(52, 6, 142, 30);
		drawerSettings.add(lblSettings);

		JLabel lblSettingsLogo = new JLabel("");
		lblSettingsLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSettingsLogo.setIcon(new ImageIcon(logo_settings));
		lblSettingsLogo.setBounds(16, 6, 30, 30);
		drawerSettings.add(lblSettingsLogo);

		/*
		 * Panel Viewer
		 */

		JPanel panelViewer = new JPanel();
		panelViewer.setBounds(212, 0, 862, 572);
		contentPanel.add(panelViewer);
		panelViewer.setLayout(null);

		panelViewer.add(dashboard);
		panelViewer.add(transaction);
		panelViewer.add(category);
		panelViewer.add(wallet);
		panelViewer.add(trash);
		panelViewer.add(setting);

		drawerClickedShowPanel(dashboard);

		selectedPanelViewer = dashboard;
	}

	public void drawerClickedShowPanel(JPanel panel) {
		if (selectedPanelViewer == panel) {
//			return;
		}
		selectedPanelViewer = panel;
		System.out.println("Do");
		
		dashboard.setVisible(false);
		transaction.setVisible(false);
		category.setVisible(false);
		wallet.setVisible(false);
		trash.setVisible(false);
		setting.setVisible(false);

		panel.setVisible(true);
	}
}
