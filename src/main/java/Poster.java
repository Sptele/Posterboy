import io.github.cdimascio.dotenv.Dotenv;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Poster {
	private String make;
	private String modelFamily;
	private String modelRange;
	private String modelVariant;
	private int bodySize;
	private int modelYear;

	private BufferedImage image;

	public Poster(String make, String modelFamily, String modelRange, String modelVariant, int bodySize, int modelYear) {
		this.make = make.toLowerCase();
		this.modelFamily = modelFamily.toLowerCase();
		this.modelRange = modelRange.toLowerCase();
		this.modelVariant = modelVariant.toLowerCase();
		this.bodySize = bodySize;
		this.modelYear = modelYear;
	}

	public BufferedImage getImage(int angle) throws IOException {
		Dotenv dotenv = Dotenv.configure().load();

		try {
			URL url = new URL(String.format("https://cdn.imagin.studio/getImage?customer=%s" +
							"&make=%s" +
							"&modelFamily=%s" +
							"&modelVariant=%s" +
							"&modelRange=%s" +
							"&bodySize=%d" +
							"&modelYear=%d" +
							"&angle=%s" +
							"&fileType=png",
					dotenv.get("API_KEY"), make, modelFamily, modelVariant, modelRange, bodySize, modelYear, angle));

			image = ImageIO.read(url);

			// Test if it works
			File f = new File("car.png");
			ImageIO.write(image, "PNG", f);
		} catch (MalformedURLException e) {
			throw new MalformedURLException("Bad Arguments Entered!");
		} catch (IOException e) {
			throw new IOException("Invalid Arguments Entered! Make sure the car entered exists to the required specs.");
		}
		return image;
	}

	public BufferedImage getImage() throws IOException {
		return getImage(Angles.DEFAULT);
	}
}
