// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
//import server.ServerUI;
import client.*;
import common.ChatIF;
import logic.Question;

import java.io.*;
import java.util.List;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  public static boolean awaitResponse = false;

  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
	 
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    //openConnection();
  }

  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
//	  Platform.runLater(new Runnable() {
//	    @Override
//	    public void run() {
//	    	System.out.println("Before updateIp");
//	    	
//	    	
//	    	try {
//	    	    System.out.println("Before updateIp");
//	    	    ServerUI.aFrame.updateIp(client.getInetAddress().toString());
//	    	    System.out.println("After updateIp");
//	    	} catch (Exception e) {
//	    	    System.out.println("Exception while updating IP: " + e.getMessage());
//	    	    e.printStackTrace();
//	    	}
//
//	        System.out.println("After updateIp");
//	        ServerUI.aFrame.updateHost("HELLO");
//	        ServerUI.aFrame.updateStatus("connected");
//	    }
//	});
      System.out.println("Received a message from server: " + msg);
      
      if (msg instanceof List<?>) {
          List<?> list = (List<?>) msg;
          if (!list.isEmpty() && list.get(0) instanceof Question) {
              System.out.println("The message is a list of Question objects. Passing to clientUI...");
              clientUI.display(list);
          } else {
              System.out.println("The message is a list, but it does not contain Question objects.");
          }
      } else {
          System.out.println("The message is not a list.");
      }
  }


  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  
  public void handleMessageFromClientUI(String message)  
  {
    try
    {
    	openConnection();//in order to send more than one message
       	awaitResponse = true;
    	sendToServer(message);
		// wait for response

    }
    catch(IOException e)
    {
    	e.printStackTrace();
      clientUI.display("Could not send message to server: Terminating client."+ e);
      quit();
    }
  }

  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
	  //ServerUI.aFrame.updateIp("");
	  //ServerUI.aFrame.updateHost("");
	  //ServerUI.aFrame.updateStatus("disconnected");
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
