package pl.mais.mapping;

public class Course {
	private String shortId;//primary key
	private String fullName;
	private String mode;
	private boolean opened;
	private int maxCapacity;
	private int currSize;
	private int teacherId;
	private int nEcts;
	private String faculty;
	
	
	public String getShortId() {
		return shortId;
	}
	public void setShortId(String shortId) {
		this.shortId = shortId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public boolean isOpened() {
		return opened;
	}
	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public int getCurrSize() {
		return currSize;
	}
	public void setCurrSize(int currSize) {
		this.currSize = currSize;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public int getNEcts() {
		return nEcts;
	}
	public void setNEcts(int nEcts) {
		this.nEcts = nEcts;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public void assignNewStudent() {
		this.currSize = this.currSize + 1;
	}
	public void removeStudent() {
		this.currSize = this.currSize - 1;
	}
	
}
