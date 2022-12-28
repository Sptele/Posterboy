package fonts;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontController {
	private static Font futura;
	private static Font garamond;
	public static final int SUPER_BOLD = 59;

	public static void setup() {
		try {
			futura = Font.createFont(Font.TRUETYPE_FONT, new File("assets\\fonts\\Futura Heavy font.ttf"));

			garamond = new Font("garamond", -1, -1);
		} catch (FontFormatException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Font futura(int style, int size) {
		if (futura == null) {
			throw new IllegalArgumentException("Font FUTURA is null, setup() might not have been called...");
		}

		return futura.deriveFont(style, size);
	}

	public static Font garamond(int style, int size) {
		if (garamond == null) {
			throw new IllegalArgumentException("Font GARAMOND is null, setup() might not have been called...");
		}

		return garamond.deriveFont(style, size);
	}
}
