package entities.drawable;

import interfaces.Controller;
import interfaces.Drawable;

import java.awt.*;

public record AccentBox(int xOffset, int yOffset, int width, int height, Color color) implements Drawable {

	public AccentBox(int carHeight) {
		this(0, 200, Controller.SCREEN_WIDTH, carHeight + 50, Color.BLACK);
	}

	@Override
	public void draw(Graphics g) {
		Color initialColor = g.getColor();

		g.setColor(color);

		g.fillRect(xOffset, yOffset, width, height);

		g.setColor(initialColor);
	}
}
