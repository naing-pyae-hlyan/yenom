package utils;

import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundBorder extends AbstractBorder {
	private static final long serialVersionUID = 1L;
	private int radius;

	public RoundBorder(int radius) {
		this.radius = radius;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		RoundRectangle2D roundRect = new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius);
		g2d.draw(roundRect);

		g2d.dispose();
	}

	@Override
	public Insets getBorderInsets(Component c) {
		int margin = 8;// Math.max(radius, 1);
		return new Insets(margin, margin, margin, margin);
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.left = insets.right = insets.top = insets.bottom = Math.max(radius, 1);
		return insets;
	}
}
