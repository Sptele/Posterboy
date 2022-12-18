package frames;

import interfaces.Controller;
import interfaces.OptionsConsumer;

import javax.swing.*;
import java.awt.*;

public class OptionsController extends Controller {

	public OptionsController(String title) {
		super(title, new FlowLayout());
	}

	public OptionsController(String title, LayoutManager layout) {
		super(title, layout);
	}

	public OptionsController(String title, LayoutManager layout, OptionsConsumer consumer) {
		super(title, layout);

		consumer.createInterface(this);
	}

	public void construct(OptionsConsumer consumer) {
		consumer.createInterface(this);
	}
}
