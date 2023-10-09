package yenom;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import database.DataController;
import database.WalletModel;
import renderer.*;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

import utils.*;
import java.awt.GridLayout;

public class PanelDashboard extends BaseJPanel {

	private static final long serialVersionUID = 1L;

	private static MyWalletCard[] arrayBtn;

	@Override
	public void disposeUi() {
		super.disposeUi();
	}

	/**
	 * Create the panel.
	 */
	public PanelDashboard() {
	}

	@Override
	public void createUi(Dimension size) {
		super.createUi(size);
		setLayout(new BorderLayout(0, 0));
		final Dimension dimension = new Dimension(size.width, size.height);

		setSize(dimension);
		setPreferredSize(dimension);
		JPanel gridPanel = new JPanel(new GridLayout(2, 2, 16, 16));

		JPanel nestedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// TODO calc sql
		final List<WalletModel> wallets = DataController.getWallets();

		for (int i = 0, l = wallets.size(); i < l; i++) {
			final WalletModel wm = wallets.get(i);
			nestedPanel.add(new MyWalletCard(wm, dimension));
		}

		gridPanel.add(nestedPanel);

		JScrollPane scrollPane = new JScrollPane(gridPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.setSize(dimension);
		scrollPane.setPreferredSize(dimension);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(scrollPane);

	}
}
