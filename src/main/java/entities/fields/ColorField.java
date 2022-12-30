package entities.fields;

import entities.PosterButton;
import interfaces.InputField;

import javax.swing.*;
import java.awt.*;

public class ColorField extends InputField<PosterButton, Color> {
	private Color colorValue;
	public ColorField(String text, String tipText, boolean required, Color initial) {
		super(text, tipText, required, new PosterButton("Choose Color", 15));

		colorValue = initial;

		inputField.addActionListener(e -> {
			colorValue = JColorChooser.showDialog(this, "Choose color", colorValue);
		});
	}

	@Override
	public Color getInputFieldValue() {
		return colorValue;
	}
}
