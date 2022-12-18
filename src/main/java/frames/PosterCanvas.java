package frames;

import entities.*;
import interfaces.Angles;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PosterCanvas extends JPanel {

	private Car car;
	private int angle;

	public PosterCanvas(Car car) {
		this.car = car;
		this.angle = Angles.DEFAULT;
	}

	public PosterCanvas(Car car, int angle) {
		this.car = car;
		this.angle = angle;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	@Override
	public void paintComponent(Graphics graphics) {
		try {
			Graphics2D g = (Graphics2D) graphics;

			Map<?, ?> desktopHints =
					(Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");

			if (desktopHints != null) {
				g.setRenderingHints(desktopHints);
			}

			CarImage image = new CarImage(
					0, 250, 100,
					car.generateImage(angle));

			AccentBox box = new AccentBox(image.height());

			Text brand = new Text(20, 180, car.make().toUpperCase(), new Font("Garamond", Font.ITALIC, 50), Color.BLACK);
			Text name = new Text(20, 325, car.modelFamily().toUpperCase(), new Font("Futura", Font.BOLD, 125), Color.WHITE);

			DrawableController controller = new DrawableController(
					new Background(ImageIO.read(new File("CarBackground.png"))),
					box,
					brand,
					name,
					image
			);

			controller.draw(g);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
