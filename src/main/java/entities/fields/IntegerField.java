package entities.fields;

import interfaces.InputField;

import javax.swing.*;
import java.text.ParseException;

public class IntegerField extends InputField<JSpinner, Integer> {
	public IntegerField(String text, String tipText, boolean required,
	                    int defaultValue, int min, int max, int step) {
		super(text, tipText, required, new JSpinner(new SpinnerNumberModel(defaultValue, min, max, step)));

	}

	@Override
	public Integer getInputFieldValue() throws NumberFormatException {
		try {
			inputField.commitEdit();
		} catch (ParseException ex) {
			throw new NumberFormatException(ex.getMessage());
		}

		return (Integer) inputField.getValue();
	}
}
