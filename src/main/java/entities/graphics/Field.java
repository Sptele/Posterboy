package entities.graphics;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class Field extends JComponent {
	private CompoundBorder border;
	private JLabel label;
	private JTextField field;
	private JLabel tip;

	public Field(String label, String tip) {
		setLayout(new FlowLayout(FlowLayout.LEFT));

		border = BorderFactory.createCompoundBorder(
				BorderFactory.createEtchedBorder(Color.GRAY, Color.GRAY),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
		);

		this.label = new JLabel(label + ": ");
		this.label.setFont(new Font("Futura", Font.PLAIN, 30));
		this.label.setBorder(new EmptyBorder(5, 5, 5, 5));

		field = new JTextField("", 20);

		this.tip = new JLabel(tip);
		this.tip.setForeground(Color.GRAY);
		this.tip.setFont(new Font("Futura", Font.ITALIC, 10));

		add(this.label);
		add(field);
		add(this.tip);
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

	public JTextComponent getField() {
		return field;
	}

	public void setField(JTextField field) {
		this.field = field;
	}

	public JLabel getTip() {
		return tip;
	}

	public void setTip(JLabel tip) {
		this.tip = tip;
	}

	public String getText() {
		return field.getText();
	}
}
