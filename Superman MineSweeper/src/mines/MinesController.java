package mines;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class MinesController {
	private Mines board;
	private int width, height, numOfMines;
	private Sweeper[][] fieldButtons;
	private HBox hbox;
	private Stage stage;
	private Label popUpLabel;
	private static final int BUTTON_SIZE = 35;

	@FXML
	private TextField textFieldHeight;

	@FXML
	private TextField textFieldWidth;

	@FXML
	private TextField textFieldMines;

	@FXML
	void Reset(ActionEvent event) {
		startGame();
	}

	public void startGame() {
		GridPane gridPane = new GridPane();
		List<ColumnConstraints> column = new ArrayList<>();
		List<RowConstraints> row = new ArrayList<>();

		try {
			// get values from user
			width = Integer.parseInt(textFieldWidth.getText());
			height = Integer.parseInt(textFieldHeight.getText());
			numOfMines = Integer.parseInt(textFieldMines.getText());
	
			if (width <= 0 || height <= 0 || numOfMines <= 0) {
				// Handle invalid input (show a message, log, etc.)
				System.out.println("Invalid input. Please enter positive integers.");
				return;
			}
		} catch (NumberFormatException e) {
			// Handle the error (show a message, log, etc.)
			System.out.println("Invalid input. Please enter valid integers.");
			return;
		}


		// initialize board
		board = new Mines(height, width, numOfMines);
		fieldButtons = new Sweeper[height][width];
		for (int i = 0; i < width; i++)
			column.add(new ColumnConstraints(BUTTON_SIZE));
		for (int i = 0; i < height; i++)
			row.add(new RowConstraints(BUTTON_SIZE));
		startGame(gridPane);

		gridPane.getColumnConstraints().addAll(column);
		gridPane.getRowConstraints().addAll(row);

		hbox.getChildren().remove(hbox.getChildren().size() - 1);
		hbox.getChildren().add(gridPane);
		hbox.autosize();
		stage.sizeToScene();
	}

	private void startGame(GridPane gridPane) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				fieldButtons[i][j] = new Sweeper(i, j);
				fieldButtons[i][j].setText(board.get(i, j));
				fieldButtons[i][j].setStyle("-fx-background-color: \r\n"
						+ "#000000,\r\n"
						+ "linear-gradient(red, #2f4b8f),\r\n"
						+ "linear-gradient(#426ab7, #263e75),\r\n"
						+ "linear-gradient(white 70%, black); -fx-text-fill: black");
				fieldButtons[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY) { // left click - will open the block that the
																		// user wants.
							boolean isReset = board.open(((Sweeper) event.getSource()).getX(),
									((Sweeper) event.getSource()).getY());
							updateFieldButtons();
							if (!isReset) {
								board.setShowAll(true);
								updateFieldButtons();
								popUpWindow(true, false);
							}
							if (board.isDone()) {
								popUpWindow(false, true);
							}
						}
						if (event.getButton() == MouseButton.SECONDARY) { // put flag when right mouse clicked
							int x = ((Sweeper) event.getSource()).getX();
							int y = ((Sweeper) event.getSource()).getY();
							board.toggleFlag(x, y);
							updateFieldButtons();
						}
					}
				});
				fieldButtons[i][j].setMaxWidth(Double.MAX_VALUE);
				fieldButtons[i][j].setMaxHeight(Double.MAX_VALUE);
				gridPane.add(fieldButtons[i][j], j, i);
			}
		}
	}

	private void updateFieldButtons() {
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				fieldButtons[i][j].setText(board.get(i, j));
				if (fieldButtons[i][j].getText().equals(" ")) {
					fieldButtons[i][j].setStyle(    "-fx-background-color: \n" +
					"    linear-gradient(from 0% 0% to 15% 50%, rgba(255, 200, 200, 0.9), rgba(255, 150, 150, 0.7)),\n" +
					"    #f5a4a4;\n" +
					"    -fx-background-insets: 0, 1, 2, 3, 0;\n" +
					"-fx-effect: dropshadow(gaussian, rgba(0, 0, 255, 0.5), 10, 0, 0, 0);"
				);

				}
				if (!fieldButtons[i][j].getText().equals(".") && !fieldButtons[i][j].getText().equals(" ")
						&& !fieldButtons[i][j].getText().equals("X")) {
					fieldButtons[i][j].setGraphic(null);
					fieldButtons[i][j].setStyle(    "-fx-background-color: \n" +
					"    linear-gradient(#5BC0F8 70%, #150E56),\n" +
					"    linear-gradient(from 0% 0% to 15% 50%, rgba(255, 255, 255, 0.9), rgba(255, 255, 255, 0)),\n" +
					"    linear-gradient(from 0% 100% to 15% 50%, rgba(255, 0, 0, 0.9), rgba(255, 0, 0, 0));\n" +
					"    -fx-background-insets: 0, 1, 2, 3, 0;\n" +
					"    -fx-font-weight: bold;\n"
				);
				}
				// mine
				if (fieldButtons[i][j].getText().equals("X")) {
					fieldButtons[i][j].setText("");
					fieldButtons[i][j].setStyle("-fx-background-color:#7DCE13");
					Image bombImg = new Image("mines/krypto.png");
					ImageView view = new ImageView(bombImg);
					view.setFitHeight(25);
					view.setFitWidth(50);
					view.setPreserveRatio(true);
					fieldButtons[i][j].setGraphic(view);
				}
				if (fieldButtons[i][j].getText().equals("F")) {
					fieldButtons[i][j].setText("");
					fieldButtons[i][j].setStyle("-fx-background-color: \r\n"
							+ "rgba(0,0,0,0.08),\r\n"
							+ "linear-gradient(#c1fcc4 90%, #f3f3f3 50%, #ececec 30%, #f2f2f2 20%);\r\n"
							+ "-fx-background-insets: 1 1 1 1,1,1;");
					Image flagImg = new Image("mines/flag.png");
					ImageView view = new ImageView(flagImg);
					view.setFitHeight(25);
					view.setFitWidth(25);
					view.setPreserveRatio(true);
					fieldButtons[i][j].setGraphic(view);
				}
			}
	}

	public void setHbox(HBox hbox) {
		this.hbox = hbox;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void popUpWindow(boolean isOver, boolean isDone) {
		Scene popUp = new Scene(initHBox(isOver, isDone), 500, 150);

		Stage popUpStage = new Stage();
		popUpStage.setScene(popUp);
		popUpStage.show();
	}

	private HBox initHBox(boolean isOver, boolean isDone) {
		HBox hbox = new HBox();

		if (isOver) {

			ImageView imageView = new ImageView("mines/dead.png");
			imageView.setFitHeight(100);
			imageView.setFitWidth(100);
			hbox.getChildren().add(imageView);
			hbox.setSpacing(10);

			popUpLabel = new Label("Encountered kryptonite!");
			popUpLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
			hbox.setPadding(new Insets(30, 20, 20, 80));
			hbox.getChildren().addAll(popUpLabel);
			return hbox;

		}

		else if (isDone)
			popUpLabel = new Label("Congratulation you won!");

		popUpLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		popUpLabel.setAlignment(Pos.BASELINE_CENTER);
		hbox.setPadding(new Insets(30, 20, 20, 80));
		hbox.getChildren().addAll(popUpLabel);
		ImageView imageView = new ImageView("mines/won.png");
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);
		hbox.getChildren().add(imageView);
		hbox.setSpacing(10);
		return hbox;
	}

	public TextField getTextFieldWidth() {
		return textFieldWidth;
	}

	public TextField getTextFieldHeight() {
		return textFieldHeight;
	}

	public TextField getTextFieldMines() {
		return textFieldMines;
	}

}