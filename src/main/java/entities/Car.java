package entities;

import interfaces.Angles;
import io.github.cdimascio.dotenv.Dotenv;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public record Car(String make, String modelFamily, String modelRange, String modelVariant, int bodySize, int modelYear) {
	public BufferedImage generateImage(int angle) throws IOException {
		Dotenv dotenv = Dotenv.configure().load();

		try {
			URL url = new URL(String.format("https://cdn.imagin.studio/getImage?customer=%s" +
							"&make=%s" +
							"&modelFamily=%s" +
							"&modelVariant=%s" +
							"&modelRange=%s" +
							(bodySize > 0 ? "&bodySize=" + bodySize : "")  +
							(modelYear > 0 ? "&modelYear=" + modelYear : "") +
							"&angle=%s" +
							"&fileType=png&width=800",
					dotenv.get("API_KEY"), make, modelFamily, modelVariant, modelRange, angle));

			return ImageIO.read(url);
		} catch (MalformedURLException e) {
			throw new MalformedURLException("Bad Arguments Entered!");
		} catch (IOException e) {
			throw new IOException("Invalid Arguments Entered! Make sure the car entered exists to the required specs.");
		}
	}

	public BufferedImage generateImage() throws IOException {
		return generateImage(Angles.DEFAULT);
	}
}
