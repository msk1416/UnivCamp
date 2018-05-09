package pl.mais.db;

import java.rmi.StubNotFoundException;
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
import java.util.Random;
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
    private HashMap<String, 	Registration> 	registrations;
    
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
    
    public void populateCoursesCache () {
    	courses.clear();
    	String query = "select * from courses;";
    	try {
    		stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(query);
    		while (rs.next()) {
    			Course c = new Course();
    			c.setShortId(rs.getString("short_id"));
    			c.setFullName(rs.getString("full_name"));
    			c.setMode(rs.getString("mode"));
    			c.setOpened(rs.getBoolean("opened"));
    			c.setMaxCapacity(rs.getInt("max_capacity"));
    			c.setCurrSize(rs.getInt("current_size"));
    			c.setTeacherId(rs.getInt("teacher_id"));
    			c.setNEcts(rs.getInt("n_ects"));
    			c.setFaculty(rs.getString("faculty"));
    			courses.put(c.getShortId(), c);
    		}
    		needUpdateCourses = false;
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
    
    public void populateRegistrationsCache() {
    	registrations.clear();
    	String query = "select * from registrations;";
    	try {
    		stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(query);
    		while (rs.next()) {
    			Registration r = new Registration();
    			r.setRegId(rs.getString("reg_id"));
    			r.setCourseId(rs.getString("course_id"));
    			r.setStudentId(rs.getInt("student_id"));
    			r.setGrade(rs.getFloat("grade"));
    			r.setStatus(rs.getString("status"));
    			registrations.put(r.getRegId(), r);
    		}
    		needUpdateRegistrations = false;
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
    
    public boolean addCourse(String shortId, String fullName, String mode, boolean isOpened, int maxCapacity, 
    		int teacherId, int nEcts, String faculty) {
    	String query = "insert into courses"
    			+ (teacherId == -1 ? "(short_id, full_name, mode, opened, max_capacity, current_size, n_ects, faculty)" : "")
    			+ " values("
    			+ "'" + shortId + "', "
    			+ "'" + fullName + "', "
    			+ "'" + mode + "', "
    			+ (isOpened ? 1 : 0) + ", "
    			+ maxCapacity + ", "
    			+ "0, " 
    			+ (teacherId == -1 ? "" : String.valueOf(teacherId) + ", ")
    			+ nEcts + ", "
				+ "'" + faculty + "');";
    	try {
    		stmt = conn.createStatement();
    		boolean ret = stmt.executeUpdate(query) > 0;
    		if (ret) needUpdateCourses = true;
    		return ret;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public boolean addRegistration(int studentId, String courseId, Double grade, String status) {
    	String query = "insert into registrations values("
    			+ "'" + studentId + "_" + courseId + "', "
    			+ studentId + ", "
    			+ "'" + courseId + "', "
    			+ (grade != null ? String.valueOf(grade.doubleValue()) : null) + ", "
    			+ "'" + status + "');";
    	try {
    		stmt = conn.createStatement();
    		boolean ret = stmt.executeUpdate(query) > 0;
    		if (ret) {
    			needUpdateRegistrations = true;
    			if (!assignNewStudent(courseId)) return false;
    		}
    		return ret;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public boolean assignNewStudent(String courseId) {
    	String query = "update courses set current_size = current_size + 1 where short_id = '" + courseId + "';";
		
		try {
    		stmt = conn.createStatement();
    		boolean ret = stmt.executeUpdate(query) > 0;
    		if (ret) {
    			if (courses.get(courseId).getCurrSize() == courses.get(courseId).getMaxCapacity()) 
    				return false;
    			else {
    				courses.get(courseId).assignNewStudent();
    				needUpdateCourses = true;
    			}
    			return true;
    		} 
		}catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
		return false;
    }
    
    public void removeStudentFromCourse(String courseId) {
    	String query = "update courses set current_size = current_size - 1 where short_id = '" + courseId + "';";
		
		try {
    		stmt = conn.createStatement();
    		boolean ret = stmt.executeUpdate(query) > 0;
    		if (ret) {
    			courses.get(courseId).removeStudent();
    			needUpdateCourses = true;
    		} 
		}catch (SQLException e) {
    		e.printStackTrace();
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
    
    public boolean removeCourse(String shortId) {
    	String query = "delete from courses where short_id = '" + shortId + "';";
    	try {
    		stmt = conn.createStatement();
    		boolean ret = stmt.executeUpdate(query) > 0;
    		if (ret) needUpdateCourses = true;
    		return ret;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public boolean removeRegistration(String regId) {
    	String query = "delete from registrations where reg_id = '" + regId + "';";
    	try {
    		stmt = conn.createStatement();
    		boolean ret = stmt.executeUpdate(query) > 0;
    		if (ret) needUpdateRegistrations = true;
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
    
    public ArrayList<Course> getCourses() {
    	if (needUpdateCourses) 
    		populateCoursesCache();
    	Iterator it = courses.entrySet().iterator();
    	Map.Entry entry;
    	ArrayList<Course> retlist = new ArrayList<>();
        while (it.hasNext()) {
            entry = (Map.Entry)it.next();
            retlist.add((Course)entry.getValue());
        }
    	return retlist;
    }
    
    public Course getCourseByShortId(String shortid) {
    	if (needUpdateCourses)
    		populateCoursesCache();
    	return courses.get(shortid);
    }
    
    public ArrayList<Course> getNonAssignedCourses() {
    	if (needUpdateCourses) 
    		populateCoursesCache();
    	Iterator it = courses.entrySet().iterator();
    	Map.Entry entry;
    	ArrayList<Course> retlist = new ArrayList<>();
        while (it.hasNext()) {
            entry = (Map.Entry)it.next();
            if (((Course)entry.getValue()).getTeacherId() < 1) 
            	retlist.add((Course)entry.getValue());
        }
    	return retlist;
    }
    
    public ArrayList<Course> getCoursesFromTeacher(int teacherId) {
    	if (needUpdateCourses)
    		populateCoursesCache();
    	Iterator it = courses.entrySet().iterator();
    	Map.Entry entry;
    	ArrayList<Course> retlist = new ArrayList<>();
        while (it.hasNext()) {
            entry = (Map.Entry)it.next();
            if (((Course)entry.getValue()).getTeacherId() == teacherId)
            	retlist.add((Course)entry.getValue());
        }
    	return retlist;
    }
    
    public ArrayList<Registration> getRegistrationsFromCourse(String courseId) {
    	if (needUpdateRegistrations)
    		populateRegistrationsCache();;
    	Iterator it = registrations.entrySet().iterator();
    	Map.Entry entry;
    	ArrayList<Registration> retlist = new ArrayList<>();
        while (it.hasNext()) {
            entry = (Map.Entry)it.next();
            if (((Registration)entry.getValue()).getCourseId().equals(courseId))
            	retlist.add((Registration)entry.getValue());
        }
    	return retlist;
    }
    
    public String[] getCourseStatus() {
    	String query = "select * from coursestatus;";
    	ArrayList<String> ret = new ArrayList<>();
    	try {
    		stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(query);
    		while (rs.next()) {
    			ret.add(rs.getString("status"));
    			ret.add(rs.getString("description"));
    		}
    		String[] arr = new String[ret.size()];
    		int i = 0;
    		for (String s : ret) {
    			arr[i++] = s;
    		}
    		return arr;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return new String[0];
    	}
    }
    
    public ArrayList<Registration> getRegistrations() {
    	if (needUpdateRegistrations) 
    		populateRegistrationsCache();
    	Iterator it = registrations.entrySet().iterator();
    	Map.Entry entry;
    	ArrayList<Registration> retlist = new ArrayList<>();
        while (it.hasNext()) {
            entry = (Map.Entry)it.next();
            retlist.add((Registration)entry.getValue());
        }
    	return retlist;
    } 
    
    public boolean assignTeacherToCourse(String courseId, int teacherId) {
    	String query = "update courses set teacher_id = " + teacherId + " where short_id = '" + courseId + "';";
    	try {
    		stmt = conn.createStatement();
    		boolean ret = stmt.executeUpdate(query) > 0;
    		if (ret) needUpdateCourses = true;
    		return ret;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    
    }
}
