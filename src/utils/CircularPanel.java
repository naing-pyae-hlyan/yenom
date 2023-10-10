package utils;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class CircularPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Color backgroundColor = Color.white; // Default background color

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = getWidth();
		int height = getHeight();

		// Calculate the radius (use the smaller dimension for a circle)
		int radius = Math.min(width, height) / 2;

		// Calculate the center coordinates
		int centerX = width / 2;
		int centerY = height / 2;

		// Set the color for the circular panel
		g.setColor(backgroundColor);

		// Draw a filled oval to create a circle
		g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		repaint(); // Repaint the panel to reflect the new background color
	}
}