import entities.Car;
import entities.graphics.ComboBox;
import entities.graphics.Field;
import entities.graphics.Title;
import frames.OptionsController;
import frames.PosterCanvas;
import frames.PosterController;
import interfaces.Angles;
import interfaces.Controller;
import interfaces.OptionsConsumer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
		Field make = new Field("Brand", "The company who sells this car. E.x. Porsche");
		Field modelFamily = new Field("Model", "The specific car model from the specified brand. E.x. 911");
		Field modelRange = new Field("Variant", "A specific variation of the car. E.x. GT3");
		Field modelVariant = new Field("Type", "The car type/size. E.x. Coupe");
		Field bodySize = new Field("Size", "The amount of seats in the car. E.x. 2");
		Field modelYear = new Field("Year", "The year the car is for. E.x. 2022");

		carSpecsPanel.add(carChooser);
		carSpecsPanel.add(make);
		carSpecsPanel.add(modelFamily);
		carSpecsPanel.add(modelRange);
		carSpecsPanel.add(modelVariant);
		carSpecsPanel.add(bodySize);
		carSpecsPanel.add(modelYear);

		JPanel posterOptionsPanel = new JPanel(new GridLayout(2, 1));
		posterOptionsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		Title posterOptions = new Title("Poster Configuration");
		ComboBox angle = new ComboBox("Angles", Angles.NAMES);

		posterOptionsPanel.add(posterOptions);
		posterOptionsPanel.add(angle);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));




		JButton submit = new JButton("Create Poster");
		submit.addActionListener(e -> {
			Car car = new Car(make.getText(), modelFamily.getText(), modelRange.getText(), modelVariant.getText(), Integer.parseInt(bodySize.getText()), Integer.parseInt(modelYear.getText()));
			main = new PosterController(String.format("Poster - %d %s %s %s (%d-seater %s), %s",
					car.modelYear(), car.make(), car.modelFamily(), car.modelRange(), car.bodySize(), car.modelVariant(), angle.getText()), new PosterCanvas(car, Angles.fromName(angle.getText())));
			main.display();

			submit.setText("Poster Created In New Window");
			submit.setEnabled(false);
		});

		JButton download = new JButton("Download");
		download.addActionListener(e -> {
			download.setText("Please Selected Download Location...");

			if (main == null) {
				Car car = new Car(make.getText(), modelFamily.getText(), modelRange.getText(), modelVariant.getText(), Integer.parseInt(bodySize.getText()), Integer.parseInt(modelYear.getText()));
				main = new PosterController(String.format("Poster - %d %s %s %s (%d-seater %s), %s",
						car.modelYear(), car.make(), car.modelFamily(), car.modelRange(), car.bodySize(), car.modelVariant(), angle.getText()), new PosterCanvas(car, Angles.fromName(angle.getText())));
			}

			BufferedImage image = new BufferedImage(Controller.SCREEN_WIDTH, Controller.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);

			Graphics2D g = image.createGraphics();
			main.getCanvas().paintComponent(g);

			JFileChooser chooser = new JFileChooser(lastFileDirectory == null ? System.getProperty("user.dir") : lastFileDirectory);
			chooser.setDialogTitle("Select File Location");
			chooser.setFileFilter(new FileNameExtensionFilter("IMAGE FILE", "png", "jpeg", "jpg"));

			if (chooser.showSaveDialog(download) == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();

				if (lastFileDirectory == null) lastFileDirectory = chooser.getCurrentDirectory().getAbsolutePath();

				try {
					if (!file.getName().endsWith(".png") && !file.getName().endsWith("jpg") && !file.getName().endsWith("jpeg")) {
						ImageIO.write(image, "png", new File(lastFileDirectory + "\\" + file.getName() + ".png"));
					} else
						ImageIO.write(image, "png", chooser.getSelectedFile());
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}

			download.setText("Download Again");
		});

		buttonPanel.add(submit);
		buttonPanel.add(download);

		co.add(carSpecsPanel);
		co.add(posterOptionsPanel);
		co.add(buttonPanel);

	}



}
