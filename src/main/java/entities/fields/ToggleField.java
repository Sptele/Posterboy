package entities.fields;

import interfaces.InputField;

import javax.swing.*;

public class ToggleField extends InputField<JToggleButton, Boolean> {
	public ToggleField(String text, String tipText, boolean required, String enabledText, String disabledText, boolean selectedByDefault) {
		super(text, tipText, required, new JToggleButton(selectedByDefault ? enabledText : disabledText, selectedByDefault));

		getInputField().addActionListener(l -> {
			if (getInputFieldValue()) getInputField().setText(enabledText);
			else getInputField().setText(disabledText);
		});
	}

	@Override
	public Boolean getInputFieldValue() {
		return inputField.isSelected();
	}
}
