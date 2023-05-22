package server;

import javafx.application.Application;
import javafx.stage.Stage;


import java.util.Vector;

import gui.ServerPortFrameController;
import server.EchoServer;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
	public static ServerPortFrameController aFrame= new ServerPortFrameController();;
	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub			
		//implement GUI
		 // create StudentFrame
		 
		aFrame.start(primaryStage);
	}
	
	public static void runServer(String p)
	{
		 int port = 0; //Port to listen on

	        try
	        {
	        	port = Integer.parseInt(p); //Set port to 5555
	          
	        }
	        catch(Throwable t)
	        {
	        	System.out.println("ERROR - Could not connect!");
	        }
	    	
	        // run server with port
	        EchoServer sv = new EchoServer(port);
	        
	        try 
	        {
	          sv.listen(); //Start listening for connections -abstractserver method
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	        }
	}
	

}
