package interfaces;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public abstract class Controller extends JFrame {
	public static final int SCREEN_WIDTH = 854;
	public static final int SCREEN_HEIGHT = 1152;

	protected Controller(String title, LayoutManager layout) {
		super(title);
		setLayout(layout);
		try {
			setIconImage(ImageIO.read(new File(System.getProperty("user.dir") + "\\icon.png")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

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
