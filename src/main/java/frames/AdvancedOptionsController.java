package frames;

import interfaces.Controller;
import interfaces.OptionsConsumer;

import java.awt.*;

public class AdvancedOptionsController extends Controller {
	public AdvancedOptionsController(String title) {
		super(title, new GridLayout(5, 5));
	}

	public void construct(OptionsConsumer consumer) {
		consumer.createInterface(this);
	}
}
