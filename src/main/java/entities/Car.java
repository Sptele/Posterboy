package entities;

import interfaces.Angles;
import io.github.cdimascio.dotenv.Dotenv;
import org.xml.sax.InputSource;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

	public BufferedImage generateLogo() throws IOException {
		try {
			URL url = new URL(String.format("https://car-logos.net/wp-content/uploads/2018/09/%s-logo.jpg", make));

			return ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("[NOTE] Unable to find logo... omitting...");
			return null;
		}
	}

	@Override
	public String toString() {
		boolean needParans = !(bodySize == Integer.MIN_VALUE) || !modelVariant.equals(Integer.MIN_VALUE + "");
		return  ((modelYear == Integer.MIN_VALUE ? "" : modelYear+" ") +
				(make.equals(Integer.MIN_VALUE+"") ? "" : make+" ") +
				(modelFamily.equals(Integer.MIN_VALUE+"") ? "" : modelFamily+" ") +
				(modelRange.equals(Integer.MIN_VALUE+"") ? "" : modelRange+" ") +
				(needParans ? "(" : "") +
				(bodySize == Integer.MIN_VALUE ? "" : bodySize+"-Seater") +
				(bodySize == Integer.MIN_VALUE && modelVariant.equals(Integer.MIN_VALUE + "") ? " " : "") +
				(modelVariant.equals(Integer.MIN_VALUE+"") ? "" : modelVariant) +
				(needParans ? ")" : "")).trim();
	}
}
