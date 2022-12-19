package entities.drawable;

import interfaces.Drawable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public record DrawableController(ArrayList<Drawable> drawables) {
	public DrawableController(Drawable... drawables) {
		this(new ArrayList<>(Arrays.asList(drawables)));
	}

	public void append(Drawable drawable) {
		drawables.add(drawable);
	}

	public void insertAtPosition(int index, Drawable drawable) {
		drawables.add(index, drawable);
	}

	public void draw(Graphics g) {
		for (Drawable drawable : drawables) {
			drawable.draw(g);
		}
	}
}
