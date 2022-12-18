package entities;

import interfaces.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;

public record Background(BufferedImage image) implements Drawable {
	@Override
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
}
