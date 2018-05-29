package pl.mais.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.mais.mapping.User;

public class UserTests {

	@Test
	public void testUserSetters() {
		int id = 30;
		String fname = "FirstName";
		String lname = "LastName";
		User u = new User();
		u.setId(id);
		u.setFirstName(fname);
		u.setLastName(lname);
		assertEquals(id, u.getId());
		assertEquals(fname, u.getFirstName());
		assertEquals(lname, u.getLastName());
	}

}
