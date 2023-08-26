package utils;

import java.awt.Image;
import javax.swing.ImageIcon;
import yenom.Home;

public class MyIcons {
	public static final Image logo_accounting = new ImageIcon(Home.class.getResource("/res/accounting.png")).getImage()
			.getScaledInstance(90, 90, Image.SCALE_SMOOTH);

	public static final Image logo_dashboard = new ImageIcon(Home.class.getResource("/res/dashboard.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_transactions = new ImageIcon(Home.class.getResource("/res/transactions.png"))
			.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_categories = new ImageIcon(Home.class.getResource("/res/categories.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_wallets = new ImageIcon(Home.class.getResource("/res/wallets.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_trash = new ImageIcon(Home.class.getResource("/res/trash.png")).getImage()
			.getScaledInstance(24, 20, Image.SCALE_SMOOTH);

	public static final Image logo_settings = new ImageIcon(Home.class.getResource("/res/settings.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_add = new ImageIcon(Home.class.getResource("/res/add.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_update = new ImageIcon(Home.class.getResource("/res/update.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_delete = new ImageIcon(Home.class.getResource("/res/delete.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_add_48 = new ImageIcon(Home.class.getResource("/res/add2.png")).getImage()
			.getScaledInstance(48, 48, Image.SCALE_SMOOTH);

	public static final Image logo_update_48 = new ImageIcon(Home.class.getResource("/res/update2.png")).getImage()
			.getScaledInstance(48, 48, Image.SCALE_SMOOTH);

	public static final Image logo_delete_48 = new ImageIcon(Home.class.getResource("/res/delete2.png")).getImage()
			.getScaledInstance(48, 48, Image.SCALE_SMOOTH);

	public static final Image logo_color_48 = new ImageIcon(Home.class.getResource("/res/colors.png")).getImage()
			.getScaledInstance(48, 48, Image.SCALE_SMOOTH);

	public static final Image logo_wallets_24 = new ImageIcon(Home.class.getResource("/res/wallets.png")).getImage()
			.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

	public static final Image logo_categories_24 = new ImageIcon(Home.class.getResource("/res/categories.png"))
			.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);

}
