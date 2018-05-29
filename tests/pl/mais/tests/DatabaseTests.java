package pl.mais.tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import pl.mais.db.DBHelper;
import pl.mais.mapping.User;

public class DatabaseTests {
	DBHelper db = new DBHelper();
	
	@Test
	public void testAddStudent() {
		User u = new User();
		int id = 6718,
			ects = 100;
		String fname = "Test", 
			   lname = "User", 
			   bday = "27/12/1995", 
			   email = "testing@mais.pl.edu", 
			   studies = "Test engineering";
		
		u.setId(id);
		u.setFirstName(fname);
		u.setLastName(lname);
		u.setRole('s');
		u.setBirthday(bday);
		u.setCurrentStudies(studies);
		u.setEmail(email);
		u.setCurrentEcts(ects);
		db.open();
		boolean addResult = db.addStudent(u.getFirstName(), u.getLastName(), u.getBirthday(), u.getEmail(), u.getCurrentStudies(), u.getCurrentEcts());
		assertTrue(addResult);
		db.close();
	}

}
