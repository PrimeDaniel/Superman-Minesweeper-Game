package client;
import javafx.application.Application;

import javafx.stage.Stage;


import java.util.Vector;

import gui.CemsDemoController;

import client.ClientController;

public class ClientUI extends Application {
	public static ClientController chat; //only one instance
	public static CemsDemoController cemsdem;
	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		 chat = new ClientController("192.168.56.1", 5555);
		// TODO Auto-generated method stub
						  		
		 cemsdem = new CemsDemoController(); 
		 
		 cemsdem.start(primaryStage);
	}
	
	
}
