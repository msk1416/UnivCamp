package pl.mais.mapping;

public class Registration {
	private String regId;//primary key
	private int studentId;
	private String courseId;
	private float grade;
	private String status;
	
	
	
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public float getGrade() {
		return grade;
	}
	public void setGrade(float grade) {
		this.grade = grade;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean hasGrade() {
		return status.equals("pass") || status.equals("failed");
	}
	public String getStatusForPrint() {
		if (this.hasGrade()) {
			return status.substring(0, 1).toUpperCase() + status.substring(1);
		} else if (status.equals("np")) {
			return "Not attended";
		} else if (status.equals("cur")) {
			return "Current";
		}
		return "Invalid status";
	}
}
