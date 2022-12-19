package entities.fields;

import interfaces.InputField;

import javax.swing.*;

public class TextField extends InputField<JTextField, String> {
	public TextField(String label, String tip) {
		this(label, tip, false);
	}

	public TextField(String label, String tip, boolean required) {
		super(label, tip, required, new JTextField("", 20));
	}

	@Override
	public String getInputFieldValue() throws IllegalArgumentException {
		if(required && inputField.getText().equals("")) throw new IllegalArgumentException("Required Field must have completed field!");
		if (inputField.getText().equals("")) return Integer.MIN_VALUE+"";

		return inputField.getText();
	}
}
