package entities.fields;

import interfaces.InputField;

import javax.swing.*;
import java.awt.*;

public class ColorField extends InputField<JButton, Color> {
	private Color colorValue;
	public ColorField(String text, String tipText, boolean required, Color initial) {
		super(text, tipText, required, new JButton("Choose Color"));

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
