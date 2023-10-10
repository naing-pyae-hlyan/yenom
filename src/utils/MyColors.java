package utils;

import java.awt.Color;

public class MyColors {
	public static Color primaryColor() {
		return new Color(38, 138, 138);
	}

	public static Color hoverColor() {
		return new Color(0, 100, 100);
	}

	public static Color secondaryColor() {
		return new Color(238, 238, 238);
	}

	public static String colorToRGBString(Color color) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		return "RGB(" + red + "," + green + "," + blue + ")";
	}
}
