package entities.drawable;

import interfaces.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;

public record Logo(BufferedImage image, int xOffset, int yOffset) implements Drawable {
	@Override
	public void draw(Graphics g) {
		if (image == null) {
			System.out.println("UNABLE TO PRINT");
			return;
		};

		g.drawImage(image, xOffset, yOffset, null);
	}
}
