package mines;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MinesFX extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		HBox hbox;
		MinesController controller;
		stage.setTitle("The SuperMan Minesweeper");
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("minesweeperbuild.fxml"));
			hbox = loader.load();
			controller = loader.getController();
			GridPane grindPane = new GridPane();
			controller.setHbox(hbox);
			
			TextField textFieldWidth = controller.getTextFieldWidth();
			TextField textFieldHeight = controller.getTextFieldHeight();
			TextField textFieldMines = controller.getTextFieldMines();
			textFieldWidth.setText("10");
			textFieldHeight.setText("10");
			textFieldMines.setText("10");
			controller.setStage(stage);

			// Setting the background for superman image
			BackgroundSize backgroundSize = new BackgroundSize(500, 10000, false, false, true, false);
			BackgroundImage image = new BackgroundImage(new Image("mines/superman.png"), null, null, null,
					backgroundSize);
			hbox.setBackground(new Background(image));
			hbox.getChildren().add(grindPane);
			controller.startGame();



		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene scene = new Scene(hbox);
		stage.setScene(scene);
		stage.show();
	}

}
