package frames;

import entities.*;
import entities.drawable.*;
import fonts.FontController;
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
	private BufferedImage carImage;

	public PosterCanvas(Car car, PosterOptions options) throws IOException {
		this.car = car;
		this.options = options;
		this.angle = Angles.DEFAULT;
	}

	public PosterCanvas(Car car, int angle, PosterOptions options) {
		this.car = car;
		this.angle = angle;
		this.options = options;
	}

	public PosterCanvas(Car car, int angle, PosterOptions options, BufferedImage img) {
		this.car = car;
		this.angle = angle;
		this.options = options;
		this.carImage = img;
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

			if (desktopHints != null)
				g.setRenderingHints(desktopHints);

			CarImage image = new CarImage(
					0, 250, 100,
					carImage == null ? car.generateImage(angle) : carImage);

			AccentBox box = new AccentBox(options.accentBoxColor(), image.height());

			Text brand = new Text(20, 180, car.make().toUpperCase(), FontController.garamond(Font.ITALIC, 50), Color.BLACK);
			Text name = new Text(20, 325, car.modelFamily().toUpperCase(), FontController.futura(Font.BOLD, 125), Color.WHITE);

			Text[] variant = generateField("variant", car.modelRange(), true, true, box.height());
			Text[] year = generateField("year", car.modelYear()+"", true, variant[1].isEmpty(), box.height());
			Text[] range = generateField("type", car.modelVariant(), variant[1].isEmpty() && year[1].isEmpty(), true, box.height());
			Text[] bodySize = generateField("Capacity", numberToWords(car.bodySize()), variant[1].isEmpty() && year[1].isEmpty(), range[1].isEmpty(), box.height());

			// Other Texts
			DrawableController controller = new DrawableController(
					new Background(options.bkgrndColor()),
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

	private Text[] generateField(String title, String value, boolean firstOffset, boolean secondOffset, int boxHeight) {
		final int labelSize = 40;
		final int dataSize = 50;

		int xOffset = firstOffset ? Controller.SCREEN_WIDTH / 7 : Controller.SCREEN_WIDTH / 7 * 4;
		int yOffset = secondOffset ? 75 : 75*3;
		Text label = new Text(xOffset, 200 + boxHeight + yOffset, title.toUpperCase(), FontController.futura(Font.PLAIN, labelSize), Color.BLACK);
		Text data = new Text(xOffset, 200 + boxHeight + yOffset + labelSize +5, value.toUpperCase(), FontController.futura(Font.BOLD, dataSize), Color.GRAY);

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
		rt.exec("assets\\image-engine\\magick.exe " + inp.getAbsolutePath() + " -resize 75% -bordercolor white -border 1x1 -alpha set -channel RGBA " +
				"-fuzz 20% -fill none -floodfill +0+0 white -shave 1x1 " + out.getAbsolutePath());

		BufferedImage newImg = ImageIO.read(out);

		Logo logo = new Logo(newImg, Controller.SCREEN_WIDTH - newImg.getWidth(), 180 - newImg.getHeight() / 2);
		controller.append(logo, 2);

		inp.deleteOnExit();
		out.deleteOnExit();
	}
}
