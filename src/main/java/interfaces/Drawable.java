package interfaces;

import java.awt.*;

public interface Drawable {
	void draw(Graphics g);

	static Drawable empty() {
		return g -> System.out.println("[NOTICE] EMPTY DRAWABLE");
	}
}
