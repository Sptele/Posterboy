import entities.Car;
import entities.PosterOptions;
import entities.Title;
import entities.fields.*;
import entities.fields.TextField;
import fonts.FontController;
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
import java.util.Calendar;

public class Main {
	public static void main(String[] args) {
		FontController.setup();

		OptionsController settings = new OptionsController("Car Settings");
		settings.construct(Main::constructOptionsFrame);
		settings.display();
	}

	static PosterController main;
	static String lastFileDirectory = null;

	/**
	 * This is where all the magic happens. The option frame is constructed here.
	 * @param co The main frame to build on
	 */
	public static void constructOptionsFrame(Controller co) {
		co.setLayout(new GridBagLayout()); // Grid Manager for simplicity, 3 rows (1 column)

		/* 1st Panel, car information for the poster */
		JPanel carSpecsPanel = new JPanel(new GridLayout(7, 1)); // Same as the main frame, uses Grid manager. 7 rows

		// Title + Fields
		Title carChooser = new Title("Car Specification");
		TextField make = new TextField("Brand", "The company who sells this car. E.x. Porsche", true);
		TextField modelFamily = new TextField("Model", "The specific car model from the specified brand. E.x. 911", true);
		TextField modelRange = new TextField("Variant", "A specific variation of the car. E.x. GT3");
		TextField modelVariant = new TextField("Type", "The car type/size. E.x. Coupe");
		IntegerField bodySize = new IntegerField("Size", "The amount of seats in the car. E.x. 2", false,
				5, 0, 100, 1);
		IntegerField modelYear = new IntegerField("Year", "The year the car is for. E.x. 2022", false,
				Calendar.getInstance().get(Calendar.YEAR), 1500, Calendar.getInstance().get(Calendar.YEAR)+1, 1);
		modelYear.getInputField().setEditor(new JSpinner.NumberEditor(modelYear.getInputField(), "####"));

		// Add them (order does matter!)
		carSpecsPanel.add(carChooser);
		carSpecsPanel.add(make);
		carSpecsPanel.add(modelFamily);
		carSpecsPanel.add(modelRange);
		carSpecsPanel.add(modelVariant);
		carSpecsPanel.add(bodySize);
		carSpecsPanel.add(modelYear);

		/* 2nd Panel, options/configuration for the poster */
		JPanel posterOptionsPanel = new JPanel(new GridLayout(7, 1)); // 3 elements

		// Title + Fields
		Title posterOptions = new Title("Poster Configuration");
		ListField angle = new ListField("Angles", "The angle the car is shot from, from the Camera's POV", false, Arrays.stream(Angles.NAMES).map(s -> s.replaceAll("_", " ")).toArray(String[]::new));
		FileField logoFile = new FileField("Custom Logo", "If a custom logo image is desired. To use the default logo, don't fill this out", false);
		angle.getInputField().setSelectedItem("DEFAULT"); // Set the default on the List to the default option
		ToggleField logo = new ToggleField("Logo", "Enable or disable showing the logo. This is primarily used if the final result of the logo is bad.",
				false, "Shown", "Hidden", true);
		ColorField bkgrndColor = new ColorField("Background Color", "The color for the background", false, Color.decode("#ccccda"));
		ColorField accentBoxColor = new ColorField("Box Color", "The color for the box that goes behind the car", false, Color.BLACK);

		// Add them
		posterOptionsPanel.add(posterOptions);
		posterOptionsPanel.add(angle);
		posterOptionsPanel.add(logo);
		posterOptionsPanel.add(logoFile);
		posterOptionsPanel.add(bkgrndColor);
		posterOptionsPanel.add(accentBoxColor);

		/* 3rd Panel, buttons */
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1)); // 2 buttons

		// Display button, creates a window to show the poster
		JButton submit = new JButton("Display Poster");
		submit.addActionListener(e -> {
			/* Generate the poster and simply show it */
			main = generatePoster(co, make, modelFamily, modelRange, modelVariant, bodySize, modelYear, angle,
					new PosterOptions(logo.getInputFieldValue(), bkgrndColor.getInputFieldValue(), accentBoxColor.getInputFieldValue()));
			if (main == null) return;

			main.display();
		});

		// Download button, pops up a dialog to download
		JButton download = new JButton("Download");
		download.addActionListener(e -> {
			/* Generate the poster, prompt the user to download it to a location, and download */
			main = generatePoster(co, make, modelFamily, modelRange, modelVariant, bodySize, modelYear, angle,
					new PosterOptions(logo.getInputFieldValue(), bkgrndColor.getInputFieldValue(), accentBoxColor.getInputFieldValue()));
			if (main == null) return;

			// This is the image that will be downloaded
			BufferedImage image = new BufferedImage(Controller.SCREEN_WIDTH, Controller.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);

			Graphics2D g = image.createGraphics();
			main.getCanvas().paintComponent(g); // Write the poster onto the image

			/* The image is now created, now it needs to be downloaded */

			// Create a file dialog
			JFileChooser chooser;
			if (lastFileDirectory == null) chooser = new JFileChooser();
			else chooser = new JFileChooser(lastFileDirectory);
			chooser.setDialogTitle("Select File Location"); // Set title
			chooser.setFileFilter(new FileNameExtensionFilter("IMAGE FILE", "png", "jpeg", "jpg")); // Ensure it is an image file

			// Show dialog, and wait until user approves download
			if (chooser.showSaveDialog(download) == JFileChooser.APPROVE_OPTION) {
				// It is necessary to modify the file that is downloaded to make sure it is downloaded properly
				File file = chooser.getSelectedFile();

				// For caching, store the most recent directory
				lastFileDirectory = chooser.getCurrentDirectory().getAbsolutePath();

				try {
					// If the user doesn't add the file format, the program must
					if (!file.getName().endsWith(".png") && !file.getName().endsWith("jpg") && !file.getName().endsWith("jpeg")) {
						// If they don't add file format, add .png (best format)
						ImageIO.write(image, "png", new File(lastFileDirectory + "\\" + file.getName() + ".png"));
					} else {
						// Otherwise just add it
						ImageIO.write(image, "png", chooser.getSelectedFile());
					}
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		});

		// Buttons
		buttonPanel.add(submit);
		buttonPanel.add(download);

		// Add Panels to the main frame
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 10, 10, 10);

		c.gridx = 0;
		c.gridy = 0;

		co.add(carSpecsPanel, c);

		c.gridx = 1;
		c.gridy = 0;

		co.add(posterOptionsPanel, c);

		c.gridx = 0;
		c.gridy = 1;

		co.add(submit, c);

		c.gridx = 1;
		c.gridy = 1;

		co.add(download, c);
	}

	/**
	 * Generates a new Poster by generating a {@link PosterController} to represent it.
	 * @param co The Controller to generate this from. Required for the warning dialog if the brand or model are not specified.
	 * @param make The {@link TextField} for the brand
	 * @param modelFamily The {@link TextField} for the model
	 * @param modelRange The {@link TextField} for the variant
	 * @param modelVariant The {@link TextField} for the type
	 * @param bodySize The {@link IntegerField} for the body size
	 * @param modelYear The {@link IntegerField} for the model year
	 * @param angle The {@link Angles} for the camera to be positioned from
	 * @param options Additional {@link PosterOptions} for the poster
	 * @return a {@link PosterController} representing the poster with these options
	 */
	private static PosterController generatePoster(Controller co, TextField make, TextField modelFamily, TextField modelRange,
	                                               TextField modelVariant, IntegerField bodySize, IntegerField modelYear, ListField angle,
	                                               PosterOptions options) {
		try {
			// Create car object
			Car car = new Car(make.getInputFieldValue(), modelFamily.getInputFieldValue(), modelRange.getInputFieldValue(), modelVariant.getInputFieldValue(),
					bodySize.getInputFieldValue(), modelYear.getInputFieldValue());

			// Create the Poster Controller
			main = new PosterController(car + ", " + angle.getInputFieldValue(), new PosterCanvas(car, Angles.fromName(
					angle.getInputFieldValue().replaceAll(" ", "_")), options));

			return main;
		} catch (NumberFormatException ex) {
			// If the body size or model year is invalid, alert the user with a dialog
			JOptionPane.showMessageDialog(co, "Body Size and Model Year must be valid whole numbers!", "Invalid Input!", JOptionPane.ERROR_MESSAGE);
		} catch (IllegalArgumentException ex) {
			// IllegalArgumentException is thrown if the data given to Car is incomplete
			// So we alert the user
			JOptionPane.showMessageDialog(co, "Brand and Model fields must be filled out in order to create poster!",
					"Required Fields!", JOptionPane.WARNING_MESSAGE);
		}

		return null;
	}



}
