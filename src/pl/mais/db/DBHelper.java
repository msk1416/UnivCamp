package pl.mais.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    
    private boolean needUpdateUsers 			= true;
    private boolean needUpdateFaculties 		= true;
    private boolean needUpdateCourses 			= true;
    private boolean needUpdateRegistrations 	= true;
    
    public DBHelper() {
    	users 			= new HashMap<>();
    	courses 		= new HashMap<>();
    	faculties 		= new HashMap<>();
    	registrations 	= new HashMap<>();
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
    
    public void populateUsersCache () {
    	users.clear();
    	String query = "select * from users;";
    	try {
    		stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(query);
    		while (rs.next()) {
    			User u = new User();
    			u.setId(rs.getInt("id"));
    			u.setFirstName(rs.getString("firstname"));
    			u.setLastName(rs.getString("lastname"));
    			u.setBirthday(rs.getDate("birthday").toString());
    			u.setEmail(rs.getString("email"));
    			u.setRole(rs.getString("role").charAt(0));
    			u.setCurrentStudies(rs.getString("curr_studies"));
    			u.setCurrentEcts(rs.getInt("curr_ects"));
    			u.setOffice(rs.getString("office_number"));
    			u.setWorkingLicense(rs.getString("working_license"));
    			users.put(u.getId(), u);
    		}
    		needUpdateUsers = false;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public void populateFacultiesCache() {
    	faculties.clear();
    	String query = "select * from faculties;";
    	try {
    		stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(query);
    		while (rs.next()) {
    			Faculty f = new Faculty();
    			f.setName(rs.getString("name"));
    			f.setAddress(rs.getString("address"));
    			faculties.put(f.getName(), f);
    		}
    		needUpdateFaculties = false;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public User getUserById (int id) {
    	if (needUpdateUsers) 
    		populateUsersCache();
    	return users.get(id);
    }
    
    public boolean addStudent(String firstName, String lastName, String birthday, String email, String currentStudies, int currentEcts) {
    	String query = "insert into users (firstname, lastname, birthday, email, role, curr_studies, curr_ects) values("
    			+ "'" + firstName + "', "
    			+ "'" + lastName + "', "
    			+ "'" + birthday + "', "
    			+ "'" + email + "', "
    			+ "'s', " 
    			+ "'" + currentStudies + "', "
    			+ currentEcts + ");";
    	try {
    		stmt = conn.createStatement();
    		boolean ret = stmt.executeUpdate(query) > 0;
    		if (ret) needUpdateUsers = true;
    		return ret;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	} 
    }
    
    public boolean addTeacher(String firstName, String lastName, String birthday, String email, String officeNumber) {
    	String query = "insert into users (firstname, lastname, birthday, email, role, office_number) values("
    			+ "'" + firstName + "', "
    			+ "'" + lastName + "', "
    			+ "'" + birthday + "', "
    			+ "'" + email + "', "
    			+ "'t', " 
    			+ "'" + officeNumber + "');";
    	try {
    		stmt = conn.createStatement();
    		boolean ret = stmt.executeUpdate(query) > 0;
    		if (ret) needUpdateUsers = true;
    		return ret;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public boolean removeUser(int id) {
    	String query = "delete from users where id = " + id;
    	try {
    		stmt = conn.createStatement();
    		boolean ret = stmt.executeUpdate(query) > 0;
    		if (ret) needUpdateUsers = true;
    		return ret;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public ArrayList<User> getUsersByRole(char role) {
    	if (needUpdateUsers) 
    		populateUsersCache();
    	Iterator it = users.entrySet().iterator();
    	Map.Entry entry;
    	ArrayList<User> retlist = new ArrayList<User>();
        while (it.hasNext()) {
            entry = (Map.Entry)it.next();
            User u = (User)entry.getValue();
            if (u.getRole() == role) {
            	retlist.add(u);
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }
    	return retlist;
    }
    public ArrayList<Faculty> getFaculties() {
    	if (needUpdateFaculties) 
    		populateFacultiesCache();
    	Iterator it = faculties.entrySet().iterator();
    	Map.Entry entry;
    	ArrayList<Faculty> retlist = new ArrayList<>();
        while (it.hasNext()) {
            entry = (Map.Entry)it.next();
            retlist.add((Faculty)entry.getValue());
        }
    	return retlist;
    }
}
