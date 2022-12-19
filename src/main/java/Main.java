import entities.Car;
import entities.PosterOptions;
import entities.fields.ListField;
import entities.fields.TextField;
import entities.Title;
import entities.fields.ToggleField;
import frames.OptionsController;
import frames.PosterCanvas;
import frames.PosterController;
import interfaces.Angles;
import interfaces.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		OptionsController settings = new OptionsController("Car Settings");

		settings.construct(Main::constructOptionsFrame);

		settings.display();
	}

	static PosterController main;
	static String lastFileDirectory = null;

	public static void constructOptionsFrame(Controller co) {
		co.setLayout(new GridLayout(3, 1));

		JPanel carSpecsPanel = new JPanel(new GridLayout(7, 1));
		carSpecsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		Title carChooser = new Title("Car Specification");
		TextField make = new TextField("Brand", "The company who sells this car. E.x. Porsche", true);
		TextField modelFamily = new TextField("Model", "The specific car model from the specified brand. E.x. 911", true);
		TextField modelRange = new TextField("Variant", "A specific variation of the car. E.x. GT3");
		TextField modelVariant = new TextField("Type", "The car type/size. E.x. Coupe");
		TextField bodySize = new TextField("Size", "The amount of seats in the car. E.x. 2");
		TextField modelYear = new TextField("Year", "The year the car is for. E.x. 2022");

		carSpecsPanel.add(carChooser);
		carSpecsPanel.add(make);
		carSpecsPanel.add(modelFamily);
		carSpecsPanel.add(modelRange);
		carSpecsPanel.add(modelVariant);
		carSpecsPanel.add(bodySize);
		carSpecsPanel.add(modelYear);

		JPanel posterOptionsPanel = new JPanel(new GridLayout(3, 1));
		posterOptionsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		Title posterOptions = new Title("Poster Configuration");
		ListField angle = new ListField("Angles", "The angle the car is shot from, in relation to where the CAMERA would be at the angle specified", false, Arrays.stream(Angles.NAMES).map(s -> s.replaceAll("_", " ")).toArray(String[]::new));
		angle.getInputField().setSelectedItem("DEFAULT");

		ToggleField logo = new ToggleField("Logo", "Enable or disable showing the logo. This is primarily used if the final result of the logo is bad.",
				false, "Shown", "Hidden", true);

		posterOptionsPanel.add(posterOptions);
		posterOptionsPanel.add(angle);
		posterOptionsPanel.add(logo);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));

		JButton submit = new JButton("Create Poster");
		submit.addActionListener(e -> {
			main = displayPoster(submit, make, modelFamily, modelRange, modelVariant, bodySize, modelYear, angle, new PosterOptions(logo.getInputFieldValue()));
			if (main == null) return;

			main.display();
			submit.setText("Create Another Poster");
		});

		JButton download = new JButton("Download");
		download.addActionListener(e -> {
			download.setText("Please Selected Download Location...");

			main = displayPoster(download, make, modelFamily, modelRange, modelVariant, bodySize, modelYear, angle, new PosterOptions(logo.getInputFieldValue()));
			if (main == null) return;

			BufferedImage image = new BufferedImage(Controller.SCREEN_WIDTH, Controller.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);

			Graphics2D g = image.createGraphics();
			main.getCanvas().paintComponent(g);

			JFileChooser chooser;
			if (lastFileDirectory == null) chooser = new JFileChooser();
			else chooser = new JFileChooser(lastFileDirectory);

			chooser.setDialogTitle("Select File Location");
			chooser.setFileFilter(new FileNameExtensionFilter("IMAGE FILE", "png", "jpeg", "jpg"));

			if (chooser.showSaveDialog(download) == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();

				if (lastFileDirectory == null) lastFileDirectory = chooser.getCurrentDirectory().getAbsolutePath();

				chooser.setSelectedFile(new File(main.getCanvas().getCar().toString()));

				try {
					if (!file.getName().endsWith(".png") && !file.getName().endsWith("jpg") && !file.getName().endsWith("jpeg")) {
						ImageIO.write(image, "png", new File(lastFileDirectory + "\\" + file.getName() + ".png"));
					} else
						ImageIO.write(image, "png", chooser.getSelectedFile());
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}

			download.setText("Another Download");
		});

		buttonPanel.add(submit);
		buttonPanel.add(download);

		co.add(carSpecsPanel);
		co.add(posterOptionsPanel);
		co.add(buttonPanel);
	}

	private static PosterController displayPoster(JButton submit, TextField make, TextField modelFamily, TextField modelRange,
	                                              TextField modelVariant, TextField bodySize, TextField modelYear, ListField angle,
	                                              PosterOptions options) {
		try {
			Car car = new Car(make.getInputFieldValue(), modelFamily.getInputFieldValue(), modelRange.getInputFieldValue(), modelVariant.getInputFieldValue(),
					Integer.parseInt(bodySize.getInputFieldValue()), Integer.parseInt(modelYear.getInputFieldValue()));
			main = new PosterController(car + ", " + angle.getInputFieldValue(), new PosterCanvas(car, Angles.fromName(
					angle.getInputFieldValue().replaceAll(" ", "_")), options));
			return main;
		} catch (IllegalArgumentException ex) {
			submit.setText("You must enter the brand and model of the car! Try Again...");
		}

		return null;
	}



}
