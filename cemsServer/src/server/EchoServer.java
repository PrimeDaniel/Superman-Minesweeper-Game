package server;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import logic.Question;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient//
    (Object msg, ConnectionToClient client)
    
  {


	  System.out.println("Received a message from client: " + msg);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("fail here?");
			System.out.println("Driver definition failed");
		}
		//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/lab3db?serverTimezone=IST","root","Mat*1997");
        try {
        	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cems?serverTimezone=IST","root","1234");
			System.out.println("SQL connection succeed");
			Statement stmt = conn.createStatement();
			ArrayList<String> arr= new ArrayList<String>();	
			ArrayList<Question> questionArray= new ArrayList<Question>();	
			
			if(!((String)msg).contains("UPDATE")){
				ResultSet rs = stmt.executeQuery((String) msg);
				while (rs.next()) {
					
					arr.add(rs.getString(1));
					arr.add(rs.getString(2));
					arr.add(rs.getString(3));
					arr.add(rs.getString(4));
					arr.add(rs.getString(5));
					arr.add(rs.getString(6));
				}	
				int i=0;
				while (i<arr.size()) {
					if (i%6==0) {
						Question q = new Question(arr.get(i),arr.get(i+1),arr.get(i+2),arr.get(i+3),arr.get(i+4),arr.get(i+5));
						questionArray.add(q);
					}
					i++;
						
				}
					
				this.sendToAllClients(questionArray);
			}
			else {
				stmt.executeUpdate((String) msg);
			}

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
   /* int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    } 
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }*/
  }
}
//End of EchoServer class
