package pl.mais.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import pl.mais.db.DBHelper;
import pl.mais.mapping.Course;
import pl.mais.mapping.Registration;
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
	
	@Test
	public void newInvalidRegistration() {
		int sid = 600;
		String courseId = "NEC", status = "cur"; //NEC -> NonExistingCourse
		double grade = 4.0;
		db.open();
		boolean addResult = db.addRegistration(sid, courseId, grade, status);
		db.close();
		assertFalse(addResult);
	}
	
	@Test
	public void newValidRegistration() {
		int sid;
		String courseId = null, status = "cur";
		double grade = 4.5;
		ArrayList<Registration> regs;
		ArrayList<Course> courses;
		db.open();
		do {
			courses = db.getCourses();
			sid = db.getRandomStudentId();
			regs = db.getRegsFromStudent(sid);
			for (int i = 0; i < regs.size(); i++) {
				courses.remove(db.getCourseByShortId(regs.get(i).getCourseId()));
			}
			if (courses.size() > 0)
				courseId = courses.get(0).getShortId();
		} while (courseId == null);
		
		boolean addResult = db.addRegistration(sid, courseId, grade, status);
		assertTrue(addResult);
	}

}
