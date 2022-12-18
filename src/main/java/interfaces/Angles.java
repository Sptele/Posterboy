package interfaces;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * The values in this class describe values accepted by the API for the possible angles. They are named by a description
 * of the angle, from the perspective of INSIDE the car.
 */
public interface Angles {
	int FRONT_RIGHT = 1;
	int RIGHT = 5;
	int BACK_RIGHT = 9;
	int BACK = 13;
	int AERIAL_BACK_LEFT = 17;
	int AERIAL_LEFT = 21;
	int LEFT = 22;
	int FRONT_LEFT = 23;
	int LEFT_ANGLED_FRONT_LEFT = 25;
	int AERIAL_FRONT_LEFT = 27;
	int SLIGHT_FRONT_LEFT = 28;
	int FRONT = 29;
	int RIGHT_ANGLED_FRONT_RIGHT = 51;
	int DEFAULT = FRONT_LEFT;

	int[] ALL = {FRONT_RIGHT, RIGHT, BACK_RIGHT, BACK, AERIAL_BACK_LEFT, AERIAL_LEFT, LEFT, FRONT_LEFT, LEFT_ANGLED_FRONT_LEFT, AERIAL_FRONT_LEFT, SLIGHT_FRONT_LEFT, FRONT,
	RIGHT_ANGLED_FRONT_RIGHT, DEFAULT};

	String[] NAMES = {"FRONT_RIGHT", "RIGHT", "BACK_RIGHT", "BACK", "AERIAL_BACK_LEFT", "AERIAL_LEFT", "LEFT", "FRONT_LEFT", "LEFT_ANGLED_FRONT_LEFT",
			"AERIAL_FRONT_LEFT", "SLIGHT_FRONT_LEFT", "FRONT", "RIGHT_ANGLED_FRONT_RIGHT", "DEFAULT"};

	static HashMap<String, Integer> NAMES_WITH_VALUES() {
		HashMap<String, Integer> hm = new HashMap<>();

		for (int i = 0; i < ALL.length; i++) {
			hm.put(NAMES[i], ALL[i]);
		}

		return hm;
	}

	static int fromName(String name) {
		return ALL[Arrays.asList(NAMES).indexOf(name)];
	}

}
