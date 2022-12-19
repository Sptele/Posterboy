package entities.fields;

import interfaces.InputField;

import javax.swing.*;

public class ListField extends InputField<JComboBox<String>, String> {
	public ListField(String text, String tipText, boolean required, String... values) {
		super(text, tipText, required, new JComboBox<>(values));
	}

	@Override
	public String getInputFieldValue() {
		return inputField.getSelectedItem().toString();
	}
}
