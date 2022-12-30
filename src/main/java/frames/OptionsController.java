package frames;

import interfaces.Controller;
import interfaces.OptionsConsumer;

import javax.swing.*;
import java.awt.*;

public class OptionsController extends Controller {

	public OptionsController(String title) {
		super(title, new FlowLayout());

		setPreferredSize(null);
	}

	public OptionsController(String title, LayoutManager layout) {
		super(title, layout);
	}

	public void construct(OptionsConsumer consumer) {
		consumer.createInterface(this);
	}
}
