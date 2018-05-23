<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="pl.mais.db.DBHelper"%>
<%@ page import="pl.mais.mapping.*" %>
<%@ page import="pl.mais.mapping.Registration" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>University campus main page</title>
</head>

<body>

<%
	DBHelper db = (DBHelper)application.getAttribute("dbhelper");
	db.open();
	db.populateUsersCache();
	if (session.getAttribute("userid") != null) {
		User current = db.getUserById(Integer.valueOf((String)session.getAttribute("userid")));
		session.removeAttribute("user");
		session.setAttribute("user", current);
		if (current != null && current.getRole() == 'a') {
	%>
	<h1>Logged as <%= current.getFirstName() %> <%= current.getLastName() %></h1>
	<h3>Users administration</h3>
	Insert new student:
	<form action="${ pageContext.request.contextPath }/NewUser" method="post"> 
		<input type="text" name="firstname" maxlength="50" placeholder="First name" autocomplete='given-name' required> 
		<input type="text" name="lastname" maxlength="50" placeholder="Last name" autocomplete='family-name' required>
		<input type="text" name="birthday" placeholder="Birthday DD/MM/YYYY" required pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}">
		<input type="email" name="email" placeholder="Email address" required autocomplete='email'>
		<input type="text" name="currentstudies" maxlength="50" required placeholder="Current studies">
		<input type="text" name="currentects" pattern="^[0-9]*$" required placeholder="Current ECTS">
		<input type="hidden" name="role" value="s">
		<input type="submit" value="Insert">
	</form>
	Insert new teacher:
	<form action="${ pageContext.request.contextPath }/NewUser" method="post"> 
		<input type="text" name="firstname" maxlength="50" placeholder="First name" autocomplete='given-name' required> 
		<input type="text" name="lastname" maxlength="50" placeholder="Last name" autocomplete='family-name' required>
		<input type="text" name="birthday" placeholder="Birthday DD/MM/YYYY" required pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}">
		<input type="email" name="email" placeholder="Email address" autocomplete='email' required>
		<input type="text" name="officenumber" placeholder="Office number" maxlength="6" required>
		<input type="hidden" name="role" value="t">
		<input type="submit" value="Insert">
	</form>
	Remove a teacher or student from the list:
	<form action="${ pageContext.request.contextPath }/RemoveUser" method="post">
		<select name="userToDelete" required>
			<option disabled selected style="display: none;" value="">---</option>
			<optgroup label="Students">
			<% 
				ArrayList<User> userlist = db.getUsersByRole('s');
				for (User u : userlist) {
					%>
						<option value="<%= u.getId() %>"><%= u.getFirstName() %> <%= u.getLastName() %></option>
					<%
				}
			%>
			</optgroup>
			<optgroup label="Teachers">
			<% 
				
				userlist = db.getUsersByRole('t');
				for (User u : userlist) {
					%>
						<option value="<%= u.getId() %>"><%= u.getFirstName() %> <%= u.getLastName() %></option>
					<%
				}
			%>
			</optgroup>
		</select>
		<input type="password" name="password" placeholder="Admin password" required>
		<input type="submit" value="Remove">
	</form>
	<h3>Courses administration</h3>
	Add new course:
	<form action="${ pageContext.request.contextPath }/AddCourse" method="post">
		<input type="text" name="courseid" placeholder="Course ID" maxlength="6" required>
		<input type="text" name="coursename" placeholder="Course full name" maxlength="50" required>
		<select name="mode" required>
			<option value="compulsory">Compulsory</option>
			<option value="elective">Elective</option>
		</select>
		<label for="openedCB">Opened:</label>
		<input type="checkbox" name="opened" id="openedCB" style="vertical-align: -2px;">
		<input type="text" name="maxcapacity" placeholder="Maximum number of students" pattern="^[0-9]*$" required>
		<select name="teacherid">
			<option selected value="-1" style="font-style: italic;">Not assigned yet</option>
			<% 
				
			userlist = db.getUsersByRole('t');
				for (User u : userlist) {
					%>
						<option value="<%= u.getId() %>"><%= u.getFirstName() %> <%= u.getLastName() %></option>
					<%
				}
			%>
		</select>
		<input type="text" name="nects" placeholder="Number of ECTS" pattern="^[0-9]*$" required>
		<select name="faculty" required>
			<option disabled selected style="display: none;" value="">Select a faculty</option>
			<% 
			ArrayList<Faculty> faculties = db.getFaculties();
				for (Faculty f : faculties) {
					%>
						<option value="<%= f.getName() %>"><%= f.getName() %></option>
					<%
				}
			%>
		</select>
		<input type="submit" value="Add course">
	</form>
	Remove a course from the list:
	<form action="${ pageContext.request.contextPath }/RemoveCourse" method="post">
		<select name="courseToDelete" required>
			<option disabled selected style="display: none;" value="">---</option>
			<% 
				ArrayList<Course> courseslist = db.getCourses();
				for (Course c : courseslist) {
					%>
						<option value="<%= c.getShortId() %>"><%= c.getShortId() %> - <%= c.getFullName() %></option>
					<%
				}
			%>
		</select>
		<input type="password" name="password" placeholder="Admin password" required>
		<input type="submit" value="Remove">
	</form>
	<h3>Registries administration</h3>
	Register a student into a course:
	<form action="${ pageContext.request.contextPath }/RegisterStudent" method="post">
		<select name="userToRegister" required>
			<option disabled selected style="display: none;" value="">Select a student</option>

			<% 
				userlist = db.getUsersByRole('s');
				for (User u : userlist) {
					%>
						<option value="<%= u.getId() %>"><%= u.getFirstName() %> <%= u.getLastName() %></option>
					<%
				}
			%>

		</select>
		<select name="coursesToRegister" required>
			<option disabled selected style="display: none;" value="">Select a course</option>
			<% 
				courseslist = db.getCourses();
				for (Course c : courseslist) {
					%>
						<option value="<%= c.getShortId() %>"><%= c.getShortId() %> - <%= c.getFullName() %></option>
					<%
				}
			%>
		</select>
		<select name="courseStatus" required onchange="document.getElementById('grade').disabled = (this.value == 'np' || this.value == 'cur') ? true : false;">
			<option disabled selected style="display: none;" value="">Status</option>
			<% 
				String[] statusArr = db.getCourseStatus();
				for (int i = 0; i < statusArr.length; i += 2) {
					%>
						<option value="<%= statusArr[i] %>"><%= statusArr[i+1] %></option>
					<%
				}
			%>
		</select>
		<input type="text" disabled placeholder="Grade" name="grade" id="grade" pattern="[+-]?([0-5]*[.])?[0-9]+" title="Enter a valid number in range 0.0 - 5.0, out of range number will be treated as null" required>
		<input type="password" name="password" placeholder="Admin password" required>
		<input type="submit" value="Register">
	</form>
	Delete a student from a course in the list:
	<form action="${ pageContext.request.contextPath }/DropStudent" method="post">
		<select name="registersToDrop" required>
			<option disabled selected style="display: none;" value="">Select a registration</option>
			<% 
				ArrayList<Registration> reglist = db.getRegistrations();
				for (Registration r : reglist) {
					User u = db.getUserById(r.getStudentId());
					%>
						<option value="<%= r.getRegId() %>"><%= u.getFirstName() + " " + u.getLastName() %> - <%= r.getCourseId() %></option>
					<%
				}
			%>
		</select>
		<input type="password" name="password" placeholder="Admin password" required>
		<input type="submit" value="Delete">
	</form>
	<br/>
	<a href="login.jsp?f=logout" style="color: blue;">Log out</a>
	<%
		}
	} else {
		%> 
			<h2 style="color:red">Don't try to get administrator privileges if you do not have.</h2>
		<%
	}
	db.close();
	%>

</body>
</html>