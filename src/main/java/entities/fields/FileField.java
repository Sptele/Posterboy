package entities.fields;

import entities.PosterButton;
import interfaces.InputField;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileField extends InputField<PosterButton, File> {
	private File file;
	private String lastFileDirectory;
	private JLabel fileConfirmation;
	public FileField(String text, String tipText, boolean required) {
		super(text, tipText, required, new PosterButton("Choose File", 15));

		fileConfirmation = new JLabel();
		fileConfirmation.setFont(new Font("Futura", Font.ITALIC, 15));
		fileConfirmation.setEnabled(false);
		add(fileConfirmation, 2);

		inputField.addActionListener(e -> {
			JFileChooser chooser;
			if (lastFileDirectory == null) chooser = new JFileChooser();
			else chooser = new JFileChooser(lastFileDirectory);
			chooser.setDialogTitle("Select File Location"); // Set title
			chooser.setFileFilter(new FileNameExtensionFilter("IMAGE FILE", "png", "jpeg", "jpg")); // Ensure it is an image file

			if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				lastFileDirectory = chooser.getCurrentDirectory().getAbsolutePath();

				fileConfirmation.setText("File: " + file.getName());
				fileConfirmation.setEnabled(true);
			}
		});
	}

	@Override
	public File getInputFieldValue() {
		return file;
	}
}
