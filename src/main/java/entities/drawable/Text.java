package entities.drawable;

import interfaces.Drawable;

import java.awt.*;

public record Text(int xOffset, int yOffset, String text, Font font, Color color) implements Drawable {
	public void draw(Graphics g) {
		Color initialColor = g.getColor();
		Font initialFont = g.getFont();

		g.setColor(color);
		g.setFont(font);

		g.drawString(text, xOffset, yOffset);

		g.setColor(initialColor);
		g.setFont(initialFont);
	}

	public boolean isEmpty() {
		return (text.equals(Integer.MIN_VALUE+""));
	}
}
