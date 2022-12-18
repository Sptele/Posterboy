package frames;

import interfaces.Controller;

import java.awt.*;

public class PosterController extends Controller {
	private PosterCanvas canvas;
	public PosterController(String title, PosterCanvas canvas) {
		super(title, new FlowLayout());

		setContentPane(canvas);
		this.canvas = canvas;
	}

	public PosterCanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(PosterCanvas canvas) {
		this.canvas = canvas;
	}
}
