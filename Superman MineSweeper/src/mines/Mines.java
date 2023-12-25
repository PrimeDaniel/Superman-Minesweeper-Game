package mines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//MineSweeper
public class Mines {
	private int height, width, numMines;
	private Board[][] board;
	private List<Location> visitedlocations = new ArrayList<>();
	private boolean showAll;

	public Mines(int height, int width, int numMines) {
		this.height = height;
		this.width = width;
		this.numMines = numMines;
		board = new Board[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				board[i][j] = new Board();
		spreadMines();
	}

	// board method
	private class Board {
		private boolean isOpen, isFlag, isMine;
		private int numOfMines;

		public void setNumOfMines(int numOfMines) {
			this.numOfMines = numOfMines;
		}
	}

	// Inserts a land mine at the given location. It can be assumed that this method
	// is only called immediately after the constructor, and before all other
	// methods
	public boolean addMine(int i, int j) {
		if (board[i][j].isMine)
			throw new IllegalArgumentException();
		if (i >= height || j >= width)
			throw new IndexOutOfBoundsException();
		board[i][j].isMine = true;
		numMines++;
		Neighbours(i, j);
		return true;
	}

	// Indicates that the user is opening this location. Returns true if this is not
	// a mine. In addition, if there is no mine near this location, then all the
	// neighboring locations are opened - and this continues recursively.
	public boolean open(int i, int j) {
		if (i >= height || j >= width)
			throw new IndexOutOfBoundsException();
		if (board[i][j].isOpen)
			return true;
		if (board[i][j].isMine) {
			board[i][j].isOpen = true;
			return false;
		}
		if (!board[i][j].isMine && board[i][j].numOfMines != 0) {
			board[i][j].isOpen = true;
			return true;
		}
		if (board[i][j].numOfMines == 0)
			board[i][j].isOpen = true;

		visitedlocations.add(new Location(i, j));
		if (i - 1 >= 0 && j - 1 >= 0 && !board[i - 1][j - 1].isMine)
			if (!visitedlocations.contains(new Location(i - 1, j - 1)))
				open(i - 1, j - 1);
		if (i - 1 >= 0 && !board[i - 1][j].isMine)
			if (!visitedlocations.contains(new Location(i - 1, j)))
				open(i - 1, j);
		if (i - 1 >= 0 && j + 1 < width && !board[i - 1][j + 1].isMine)
			if (!visitedlocations.contains(new Location(i - 1, j + 1)))
				open(i - 1, j + 1);

		if (j - 1 >= 0 && !board[i][j - 1].isMine)
			if (!visitedlocations.contains(new Location(i, j - 1)))
				open(i, j - 1);
		if (j + 1 < width && !board[i][j + 1].isMine)
			if (!visitedlocations.contains(new Location(i, j + 1)))
				open(i, j + 1);

		if (i + 1 < height && j - 1 >= 0 && !board[i + 1][j - 1].isMine)
			if (!visitedlocations.contains(new Location(i + 1, j - 1)))
				open(i + 1, j - 1);
		if (i + 1 < height && !board[i + 1][j].isMine)
			if (!visitedlocations.contains(new Location(i + 1, j)))
				open(i + 1, j);
		if (i + 1 < height && j + 1 < width && !board[i + 1][j + 1].isMine)
			if (!visitedlocations.contains(new Location(i + 1, j + 1)))
				open(i + 1, j + 1);
		return true;
	}

	// Names a flag at the given location, or removes it if there is already a flag
	// there.
	public void toggleFlag(int x, int y) {
		if (x >= height || y >= width)
			throw new IndexOutOfBoundsException();

		if (!board[x][y].isOpen) {
			board[x][y].isFlag = !board[x][y].isFlag;
		}
	}

	// Returns true if all non-mined locations are open.
	public boolean isDone() {
		int mines = 0, safe = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (!board[i][j].isOpen && board[i][j].isMine) {
					mines++;
				}
				if (board[i][j].isOpen)
					safe++;
			}
		}
		int totalFields = height * width;
		return mines == numMines && safe == totalFields - numMines;
	}

	// Returns a string representation of the location:
	// If the position is closed it will return "F" if it has a flag, otherwise "."
	// If the position is open, will return "X" if it is a mine, otherwise the
	// number of mines next to it and if this number is 0 then " ".
	public String get(int i, int j) {
		if (!board[i][j].isOpen && !showAll) {
			return board[i][j].isFlag ? "F" : ".";
		}
		if (board[i][j].isMine) {
			return showAll ? "X" : ".";
		}
		if (board[i][j].numOfMines != 0) {
			return String.valueOf(board[i][j].numOfMines);
		}
		return " ";
	}

	// will set the value of the showAll field. This field is initialized to false,
	// but when it is true, then in get and in toString, the return value is as if
	// all the cells are open (but it doesn't really open them)
	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}
    //Returns a description of the board as a string by get for each location.
	public String toString() {
		StringBuilder minesBuilder = new StringBuilder();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				minesBuilder.append(get(i, j));
			minesBuilder.append("\n");
		}
		return minesBuilder.toString();
	}
    //method goes on Neighbors of the field and updates them accordingly
	private void Neighbours(int i, int j) {
		for (int row = i - 1; row <= i + 1; row++) {
			for (int col = j - 1; col <= j + 1; col++) {
				if (row >= 0 && row < height && col >= 0 && col < width && !board[row][col].isMine) {
					board[row][col].setNumOfMines(board[row][col].numOfMines + 1);
				}
			}
		}
	}
    //method to get random spread of mines on the field
	private void spreadMines() {
		Random randomNum = new Random();
		int i, j;
		for (int numOfMines = 0; numOfMines < numMines; numOfMines++) {
			i = randomNum.nextInt(height);
			j = randomNum.nextInt(width);
			while (board[i][j].isMine) {
				i = randomNum.nextInt(height);
				j = randomNum.nextInt(width);
			}
			board[i][j].isMine = true;
			Neighbours(i, j);
		}
	}
    //for the tester to use element type
	@SuppressWarnings("unused")
	private class Location {
		private int i, j;

		public Location(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}
}
