package pl.mais.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.mais.mapping.*;

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
    
    //Data caches
    private HashMap<Integer, 	User> 			users;
    private HashMap<String,		Course> 		courses;
    private HashMap<String, 	Faculty> 		faculties;
    private HashMap<Integer, 	Registration> 	registrations;
    
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
    
    public boolean tryLogin(int userid, String encrPass) {
    	String query = "select * from logins where user_id = " + userid + " and pass = '" + encrPass + "';";
    	try {
    		stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(query);
    		boolean loginSucceed = rs.next();
    		System.out.println("Login " + ((loginSucceed) ? "succeeded." : "failed."));
			return loginSucceed;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Login failed.");
			return false;
		}
    }
    
    public String[] testSelectFaculties() {
    	try {  
            // Create and execute an SQL statement that returns some data.  
            String SQL = "SELECT * FROM faculties";  
            stmt = conn.createStatement();  
            ResultSet rs = stmt.executeQuery(SQL);  
            ArrayList<String> results = new ArrayList<String>();
            // Iterate through the data in the result set and display it.  
            while (rs.next()) {  
               results.add(rs.getString(1) + " - " + rs.getString(2)); 
               System.out.println(rs.getString(1) + " - " + rs.getString(2));
            }  
            String[] ret = new String[results.size()];
            for (int i = 0; i < results.size(); i++) {
            	ret[i] = results.get(i);
            }
            return ret;
         }  
 
         catch (Exception e) {  
            e.printStackTrace();  
            
         }  
    	return null;
    }
}
