package pl.mais.mapping;


public class User {
	private int id;//primary key
	private String firstName;
	private String lastName;
	private String birthday;
	private String email;
	private char role;
	//particular attributes for students
	private String currentStudies;
	private int currentEcts;
	//particular attribute for teachers
	private String office;
	//particular attribure for admins
	private String workingLicense;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public char getRole() {
		return role;
	}
	public void setRole(char role) {
		this.role = role;
	}
	public String getCurrentStudies() {
		return currentStudies;
	}
	public void setCurrentStudies(String currentStudies) {
		if (currentStudies == null) this.currentStudies = "";
		else this.currentStudies = currentStudies;
	}
	public int getCurrentEcts() {
		return currentEcts;
	}
	public void setCurrentEcts(int currentEcts) {
		this.currentEcts = currentEcts;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		if (office == null) this.office = "";
		else this.office = office;
	}
	public String getWorkingLicense() {
		return workingLicense;
	}
	public void setWorkingLicense(String workingLicense) {
		if (workingLicense == null) this.workingLicense = "";
		else this.workingLicense = workingLicense;
	}
	
	
}
