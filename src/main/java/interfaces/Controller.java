package interfaces;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public abstract class Controller extends JFrame {
	public static final int SCREEN_WIDTH = 854;
	public static final int SCREEN_HEIGHT = 1152;

	protected Controller(String title, LayoutManager layout) {
		super(title);
		setLayout(layout);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
	}

	@Override
	public Component add(Component comp) {
		return getContentPane().add(comp);
	}

	public void display() {
		pack();
		setVisible(true);
	}

}
