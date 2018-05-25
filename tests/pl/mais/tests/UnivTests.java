package pl.mais.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pl.mais.db.DBHelper;
import pl.mais.mapping.User;
import sun.net.www.http.HttpClient;

@RunWith(Suite.class)
@SuiteClasses({})
public class UnivTests {
	DBHelper db = new DBHelper();
	@Test
	void testAddRemoveStudent() {
		db.open();
		User admin = db.getUserById(41);//main administrator has id = 41 in database
		char role = 's';
		String fname = "Test", 
			   lname = "User", 
			   bday = "01/01/1900", 
			   email = "testuser@univ.pl.com", 
			   cstud = "Test engineering";
		int ects = 50;
		
		
	}
}
