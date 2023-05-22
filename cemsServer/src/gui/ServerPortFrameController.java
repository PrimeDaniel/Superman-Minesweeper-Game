package gui;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

//import client.ClientUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import server.EchoServer;
import server.ServerUI;

public class ServerPortFrameController  {
	
	public static String ip="";
	public static String host="";
	public static String  status="";
	
	@FXML
	private Button btnExit = null;
	@FXML
	private Button btnDone = null;
	@FXML
	private Label lbllist;
	@FXML
	private Label lbllis1t;
	@FXML
	private Label lbllist2;
	@FXML
	private Label lbllist3;
	@FXML
	private Label lbllist4;
	
	@FXML
	private TextField ip1;
	@FXML
	private TextField host1;
	@FXML
	private TextField status1;
	
	@FXML
	private TextField portxt;
	ObservableList<String> list;
	
	private String getport() {
		return portxt.getText();			
	}
	private void getip() throws UnknownHostException {
		InetAddress ip=InetAddress.getLocalHost();
		
		 ip1.setText(ip.getHostAddress());	
	}
	private void gethost() throws UnknownHostException {
		InetAddress ip=InetAddress.getLocalHost();
		
		 host1.setText(ip.getHostName());	
	}
	
	
    public void initialize(URL url, ResourceBundle rb) {
        ServerUI.aFrame = this;
      
    }
	public void Done(ActionEvent event) throws Exception {
		String p;
		
		p=getport();
		if(p.trim().isEmpty()) {
			System.out.println("You must enter a port number");
					
		}
		else
		{
			//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
//			Stage primaryStage = new Stage();
//			FXMLLoader loader = new FXMLLoader();
			ServerUI.runServer(p);
			getip();
			gethost();
			status1.setText("Connected");
			
			
		}
	}
	
	public void updateIp(String newip) {
	     Platform.runLater(() -> {
	            ip1.setText(newip);
	        });
	}
	public void updateHost(String host) {
	     Platform.runLater(() -> {
	    	 this.host1.setText(host);
	        });
		
	}
	public void updateStatus(String status) {
	     Platform.runLater(() -> {
	    	 this.status1.setText(status);
	        });
		
		
	}

	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerPort.fxml"));
				
		Scene scene = new Scene(root);
		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
		System.exit(0);			
	}

}