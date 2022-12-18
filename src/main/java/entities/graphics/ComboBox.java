package entities.graphics;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class ComboBox extends JComponent {
	private CompoundBorder border;
	private JLabel label;
	private JComboBox<String> field;

	public ComboBox(String label, String... values) {
		setLayout(new FlowLayout(FlowLayout.LEFT));

		border = BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(Color.GRAY, Color.GRAY),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
		);

		this.label = new JLabel(label + ": ");
		this.label.setFont(new Font("Futura", Font.PLAIN, 30));
		this.label.setBorder(new EmptyBorder(5, 5, 5, 5));

		field = new JComboBox<>(values);

		add(this.label);
		add(field);
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

	public JComboBox<String> getField() {
		return field;
	}

	public void setField(JComboBox<String> field) {
		this.field = field;
	}


	public String getText() {
		return field.getSelectedItem().toString();
	}
}
