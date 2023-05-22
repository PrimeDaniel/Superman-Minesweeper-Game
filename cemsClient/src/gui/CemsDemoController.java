package gui;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import logic.Question;


public class CemsDemoController implements Initializable  {
	@FXML
	private Button sendbtn ;
	@FXML
	private Button updateBtn ;
	@FXML
	private TableColumn<Question, String> id;
	@FXML
	private TableColumn<Question, String> subject_name;
	@FXML
	private TableColumn<Question, String> coursename;
	@FXML
	private TableColumn<Question, String> question_text;
	@FXML
	private TableColumn<Question, String> question_number;
	@FXML
	private TableColumn<Question, String> lecturer;
    @FXML
    private TableView<Question> table;
    @FXML
    private TextField idToChange;
    @FXML
    private TextField newQnum;
    @FXML
    private TextArea newQtext;
    
	
	private String GetqNum() {
		return id.getText();
	}
	
	public void Send(ActionEvent event)  throws Exception {
		String qNum;
		FXMLLoader loader = new FXMLLoader();
		
		qNum=GetqNum();
		String queryexist="SELECT * FROM cems.questions";
		ClientUI.chat.accept(queryexist);
			

	}
	public void update(ActionEvent event)  throws Exception {
		
		String queryexist=String.format("UPDATE questions SET question_number='%s' ,question_text='%s' WHERE id='%s'", newQnum.getText(),newQtext.getText(),idToChange.getText());
		ClientUI.chat.accept(queryexist);
			

	}

	public void start(Stage primaryStage) throws Exception {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CemsDemo.fxml"));
	    Parent root = loader.load();
	    Scene scene = new Scene(root);
	    primaryStage.setTitle("check");
	    primaryStage.setScene(scene);
	    primaryStage.show();

	    // Get the controller instance from the loader
	    CemsDemoController controller = loader.getController();
	    // Set the static reference in ClientUI to this controller
	    ClientUI.cemsdem = controller;
	}

	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");		
	}
    public void initialize(URL url, ResourceBundle rb) {
        ClientUI.cemsdem = this;
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        subject_name.setCellValueFactory(new PropertyValueFactory<>("subject_name"));
        coursename.setCellValueFactory(new PropertyValueFactory<>("coursename"));
        question_text.setCellValueFactory(new PropertyValueFactory<>("question_text"));
        question_number.setCellValueFactory(new PropertyValueFactory<>("question_number"));
        lecturer.setCellValueFactory(new PropertyValueFactory<>("lecturer"));
        
    }
    
    public void display(Object message) {
        ArrayList<Question> messageArr = (ArrayList<Question>)message;

        // Debug output
        System.out.println("Received " + messageArr.size() + " questions:");

        for (Question question : messageArr) {
            System.out.println("Question id: " + question.getId());
        }

        Platform.runLater(() -> {
            table.getItems().clear(); // Clear any existing items
            table.getItems().addAll(messageArr);
        });
    }

	
	
	

}
