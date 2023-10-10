package utils;

import java.awt.Image;
import javax.swing.ImageIcon;
import yenom.Home;

public class MyIcons {
	public static final Image logo_accounting = new ImageIcon(Home.class.getResource("/res/accounting.png")).getImage()
			.getScaledInstance(90, 90, Image.SCALE_SMOOTH);

	public static final Image logo_categories = new ImageIcon(Home.class.getResource("/res/categories.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_color_48 = new ImageIcon(Home.class.getResource("/res/colors.png")).getImage()
			.getScaledInstance(48, 48, Image.SCALE_SMOOTH);

	public static final Image logo_dashboard = new ImageIcon(Home.class.getResource("/res/dashboard.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_transactions = new ImageIcon(Home.class.getResource("/res/transactions.png"))
			.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_wallets = new ImageIcon(Home.class.getResource("/res/wallets.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_wallets_24 = new ImageIcon(Home.class.getResource("/res/wallets.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_categories_24 = new ImageIcon(Home.class.getResource("/res/categories.png"))
			.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);

}
