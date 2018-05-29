package pl.mais.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pl.mais.utils.MD5Utils;
import pl.mais.db.DBHelper;
import pl.mais.mapping.User;


public class LoginTest {

	@Test
	public void testPassword() {
		String test1 = "testString";
		String test2 = "testString";
		String hashedTest1 = MD5Utils.getMD5HashAsString(test1);
		String hashedTest2 = MD5Utils.getMD5HashAsString(test2);
		assertEquals(hashedTest1, hashedTest2);
	}
	
	@Test
	public void testLogin() {
		DBHelper db = new DBHelper();
		db.open();
		ArrayList<User> admins = db.getUsersByRole('a');
		User a = null;
		if (admins.size() > 0) {
			a = admins.get(0);
			boolean correctPasswordResult = db.tryLogin(a.getId(), MD5Utils.getMD5HashAsString("admin"));
			assertTrue(correctPasswordResult);
			boolean wrongPasswordResult = db.tryLogin(a.getId(), MD5Utils.getMD5HashAsString("wrong password"));
			assertFalse(wrongPasswordResult);
		}
	}

}
