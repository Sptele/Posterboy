package interfaces;

import fonts.FontController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public abstract class InputField<C extends JComponent, R> extends JComponent {
	protected C inputField;
	protected CompoundBorder border;
	protected JLabel label;
	protected JLabel tip;
	protected boolean required;

	public InputField(String text, String tipText, boolean required, C inputField) {
		this.required = required;
		this.inputField = inputField;
		this.inputField.setFont(FontController.futura(Font.PLAIN, this.inputField.getFont().getSize()));

		this.border = BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
		);

		this.label = new JLabel(text + (required ? "*" : "") + ": ");
		this.label.setFont(FontController.futura(Font.PLAIN, 30));
		this.label.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.tip = new JLabel((required ? "(REQUIRED) " : "") + tipText);
		this.tip.setForeground(Color.GRAY);
		this.tip.setFont(FontController.futura(Font.ITALIC, 10));

		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(this.label);
		add(this.inputField);
		add(this.tip);

		setBorder(this.border);
		setToolTipText(tipText);
	}

	public C getInputField() {
		return inputField;
	}

	public void setInputField(C inputField) {
		this.inputField = inputField;
	}

	@Override
	public CompoundBorder getBorder() {
		return border;
	}

	public void setBorder(CompoundBorder border) {
		this.border = border;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JLabel getTip() {
		return tip;
	}

	public void setTip(JLabel tip) {
		this.tip = tip;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public abstract R getInputFieldValue();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		InputField<?, ?> that = (InputField<?, ?>) o;

		if (required != that.required) return false;
		if (!Objects.equals(inputField, that.inputField)) return false;
		if (!Objects.equals(border, that.border)) return false;
		if (!Objects.equals(label, that.label)) return false;
		return Objects.equals(tip, that.tip);
	}

	@Override
	public int hashCode() {
		int result = inputField != null ? inputField.hashCode() : 0;
		result = 31 * result + (border != null ? border.hashCode() : 0);
		result = 31 * result + (label != null ? label.hashCode() : 0);
		result = 31 * result + (tip != null ? tip.hashCode() : 0);
		result = 31 * result + (required ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "InputField{" +
				"inputField=" + inputField +
				", border=" + border +
				", label=" + label +
				", tip=" + tip +
				", required=" + required +
				'}';
	}
}
