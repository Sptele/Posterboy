package frames;

import entities.*;
import entities.drawable.*;
import interfaces.Angles;
import interfaces.Controller;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PosterCanvas extends JPanel {

	private Car car;
	private int angle;
	private PosterOptions options;

	public PosterCanvas(Car car, PosterOptions options) {
		this.car = car;
		this.options = options;
		this.angle = Angles.DEFAULT;
	}

	public PosterCanvas(Car car, int angle, PosterOptions options) {
		this.car = car;
		this.angle = angle;
		this.options = options;
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
			Text name = new Text(20, 325, car.modelFamily().toUpperCase(), new Font("futura", Font.BOLD, 125), Color.WHITE);

			Text[] variant = generateField("variant", car.modelRange(), true, 75, box.height());
			Text[] year = generateField("year", car.modelYear()+"", true, variant[1].isEmpty() ? 75 : (int) (75*2.5), box.height());
			Text[] range = generateField("type", car.modelVariant(), variant[1].isEmpty() && year[1].isEmpty(), 75, box.height());
			Text[] bodySize = generateField("Capacity", numberToWords(car.bodySize()), variant[1].isEmpty() && year[1].isEmpty(), range[1].isEmpty() ? 75 : (int) (75*2.5), box.height());

			// Other Texts
			DrawableController controller = new DrawableController(
					new Background(),
					box,
					brand,
					name,
					image,
					new TextOptional(variant),
					new TextOptional(range),
					new TextOptional(year),
					new TextOptional(bodySize)
			);


			if (options.showLogo())
				addLogo(controller, car.generateLogo());



			controller.draw(g);
		} catch (IIOException ignored) {
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Text[] generateField(String title, String value, boolean firstOffset, int yOffset, int boxHeight) {
		final int labelSize = 40;
		final int dataSize = 50;

		int xOffset = firstOffset ? Controller.SCREEN_WIDTH / 7 : Controller.SCREEN_WIDTH / 7 * 4;
		Text label = new Text(xOffset, 200 + boxHeight + yOffset, title.toUpperCase(), new Font("futura", Font.PLAIN, labelSize), Color.BLACK);
		Text data = new Text(xOffset, 200 + boxHeight + yOffset + labelSize +5, value.toUpperCase(), new Font("futura", Font.BOLD, dataSize), Color.GRAY);

		return new Text[]{label, data};
	}

	private String numberToWords(int num) {
		return switch (num) {
			case 0 -> "ZERO";
			case 1 -> "ONE";
			case 2 -> "TWO";
			case 3 -> "THREE";
			case 4 -> "FOUR";
			case 5 -> "FIVE";
			case 6 -> "SIX";
			case 7 -> "SEVEN";
			case 8 -> "EIGHT";
			case 9 -> "NINE";
			case 10 -> "TEN";
			default -> num+"";
		};
	}

	private void addLogo(DrawableController controller, BufferedImage img) throws IOException {
		if (img == null) return;

		File inp = new File(System.getProperty("user.dir") + "/temp/logo.png");
		File out = new File(System.getProperty("user.dir") + "/temp/fixed-logo.png");
		ImageIO.write(img, "png", inp);

		Runtime rt = Runtime.getRuntime();
		rt.exec("magick " + inp.getAbsolutePath() + " -resize 75% -bordercolor white -border 1x1 -alpha set -channel RGBA " +
				"-fuzz 20% -fill none -floodfill +0+0 white -shave 1x1 " + out.getAbsolutePath());

		BufferedImage newImg = ImageIO.read(out);

		Logo logo = new Logo(newImg, Controller.SCREEN_WIDTH - newImg.getWidth(), 180 - newImg.getHeight() / 2);
		controller.append(logo);

		inp.deleteOnExit();
		out.deleteOnExit();
	}
}
