package pl.mais.general;

import pl.mais.db.DBHelper;

public class App {
	public static void main (String[] args) {
		DBHelper db = new DBHelper();
		db.open();
		db.testSelectFaculties();
		db.close();
	}
}
