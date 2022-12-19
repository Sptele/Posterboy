package entities.drawable;

import interfaces.Drawable;

import java.awt.*;

public record TextOptional(Text[] texts) implements Drawable {
	@Override
	public void draw(Graphics g) {
		if (texts[1].text().equals(Integer.MIN_VALUE+"")) {
			Drawable.empty().draw(g);
			return;
		}

		texts[0].draw(g);
		texts[1].draw(g);
	}
}
