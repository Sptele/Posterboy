package entities;

import interfaces.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;

public record CarImage(int xOffset, int yOffset, int scale, BufferedImage image) implements Drawable {

	public CarImage(int xOffset, int yOffset) {
		this(xOffset, yOffset, 100, null);
	}

	public int width() {
		return image.getWidth();
	}

	public int height() {
		return image.getHeight();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(newScaledImage().image(), xOffset, yOffset, null);
	}

	public CarImage newScaledImage() {
		return new CarImage(xOffset, yOffset, scale, newResizedImage(image.getWidth() * (scale / 100), image.getHeight() * (scale / 100)));
	}

	/**
	 * Resizes the current image. <a href="https://stackoverflow.com/questions/12552144/resize-image-in-java-without-losing-transparency">Not mine, found here</a>
	 * @param width Desired width
	 * @param height Desired height
	 * @return A {@link BufferedImage} since this is only a convenience function to resize this. To properly transform the image, use {@link CarImage#newScaledImage()}
	 */
	public BufferedImage newResizedImage(int width, int height) {
		Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return dimg;
	}
}
