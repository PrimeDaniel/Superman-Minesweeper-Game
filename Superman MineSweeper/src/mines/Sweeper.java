package mines;

import javafx.scene.control.Button;

//method for the sweeper buttons
public class Sweeper extends Button {
	private int x, y;

	public Sweeper(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
