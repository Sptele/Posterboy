package entities.drawable;

import interfaces.Controller;
import interfaces.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;

public record Background() implements Drawable {
	@Override
	public void draw(Graphics g) {
		Color og = g.getColor();
		g.setColor(Color.decode("#ccccda"));
		g.fillRect(0, 0, Controller.SCREEN_WIDTH, Controller.SCREEN_HEIGHT);
		g.setColor(og);
	}
}
