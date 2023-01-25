package autoseller;
import java.sql.*;
import javax.swing.*;

/**
 * Auto Seller - SqlliteConnection Class
 * This class is used to connect to the Sqlite database.
 * The database is stored in the sub folder "Resources".
 * This application will not work once exported as a jar file
 * as the getConnection() URI will change to a value relative to the JVM of the 
 * machine running the application - Development is in progress to fix this issue!
 */
public class SqlliteConnection {
	
	public static Connection conn = null;
	
	/**
	 * Connection Method
	 * Method to try and connect to Database.
	 * @return	public Connection object "conn" used in each class
	 */
	public static Connection dbConnector()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			
			// Connects connection object "conn" to MySqlite Database
			Connection conn = DriverManager.getConnection("jdbc:sqlite:Resources/AutoSeller.sqlite");
			

			// Tests Connection: Displays if connection is successful in dialogue box
			// JOptionPane.showMessageDialog(null, "Connection Successful");

			
			return conn;
					
		} catch (Exception e) {
			// Displays if connection is unsuccessful in dialogue box
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
