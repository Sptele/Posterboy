import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		Poster poster = new Poster("Audi", "a3", "rs3", "hatchback", 5, 2022);
		poster.getImage(Angles.AERIAL_BACK_LEFT);
	}
}
