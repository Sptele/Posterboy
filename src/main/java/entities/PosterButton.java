package entities;

import fonts.FontController;

import javax.swing.*;
import java.awt.*;

public class PosterButton extends JButton {
	public PosterButton(String title) {
		this(title, 40);
	}

	public PosterButton(String title, int size) {
		super(title);

		setFont(FontController.futura(Font.PLAIN, size));
	}

}
