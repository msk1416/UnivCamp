package pl.mais.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author sergi
 *
 */
public class DBHelper {
	// JDBC driver name and database URL
    private final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  
    private final String DB_URL = "jdbc:sqlserver://localhost;databaseName=campus_db;";
    
    //  Database credentials
    private static final String DB_USER = "sergi";
    private static final String DB_PASS = "admin";
    
    private Connection conn = null;
    private Statement stmt = null;
    
    public DBHelper() {
    	try {
    		Class.forName(JDBC_DRIVER);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void open() {
        try {
        	String connectionUrl = DB_URL + "user=" + DB_USER + ";password=" + DB_PASS;
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(connectionUrl);
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void testSelectFaculties() {
    	try {  
            // Create and execute an SQL statement that returns some data.  
            String SQL = "SELECT * FROM faculties";  
            stmt = conn.createStatement();  
            ResultSet rs = stmt.executeQuery(SQL);  

            // Iterate through the data in the result set and display it.  
            while (rs.next()) {  
               System.out.println(rs.getString(1) + " - " + rs.getString(2));  
            }  
         }  

         // Handle any errors that may have occurred.  
         catch (Exception e) {  
            e.printStackTrace();  
         }  
    }
}
