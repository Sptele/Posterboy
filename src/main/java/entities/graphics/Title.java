package entities.graphics;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class Title extends JLabel {
	public Title(String text) {
		super(text);
		setFont(new Font("Garamond", Font.BOLD, 40));
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
		));
	}
}
