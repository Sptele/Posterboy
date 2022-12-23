package entities.drawable;

import interfaces.Controller;
import interfaces.Drawable;

import java.awt.*;

public record Background(Color c) implements Drawable {
	@Override
	public void draw(Graphics g) {
		Color og = g.getColor();
		g.setColor(c);
		g.fillRect(0, 0, Controller.SCREEN_WIDTH, Controller.SCREEN_HEIGHT);
		g.setColor(og);
	}
}
