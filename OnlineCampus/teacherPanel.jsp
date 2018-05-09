<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="pl.mais.db.DBHelper"%>
<%@ page import="pl.mais.mapping.*" %>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>University campus main page</title>
</head>
<style type="text/css">
	td, th {
	    border-top: 2px solid gray;
	    border-collapse: collapse;
	    border-bottom: 2px solid gray;
	    padding-right: 7px;
	    padding-left: 7px;
	}
	table {
		border: 2px solid gray;
		border-collapse: collapse;
	}
</style>

<body>

<%
	DBHelper db = (DBHelper)application.getAttribute("dbhelper");
	db.open();
	db.populateUsersCache();
	User current = db.getUserById(Integer.valueOf((String)session.getAttribute("userid")));
	session.removeAttribute("user");
	session.setAttribute("user", current);
	if (current != null && current.getRole() == 't') {
	%>
	<h1>Logged as <%= current.getFirstName() %> <%= current.getLastName() %></h1>
	<%
	
	
	%>

	<form action="${ pageContext.request.contextPath }/TakeCourse" method="post">
		<h3>Take a course:</h3>
		<select name="course" required>
			<option disabled selected style="display: none;" value="">Select a course to take</option>
			<% 
			ArrayList<Course> courses = db.getNonAssignedCourses();
				for (Course c : courses) {
					%>
						<option value="<%= c.getShortId() %>"><%= c.getShortId() %></option>
					<%
				}
			%>
		</select>
		<input type="password" name="password" placeholder="Enter your password" required>
		<input type="submit" value="Take">
	</form>
	<h3>Your courses</h3>
	<table>
		<tr>
			<th>Short name</th>
			<th>Full name</th>
			<th>Mode</th>
			<th>Opened</th>
			<th>Current size</th>
			<th>ECTS</th>
			<th>Faculty</th>
			<th>Students</th>
		</tr>
		<%
			courses = db.getCoursesFromTeacher(current.getId());
			for (int i = 0; i < courses.size(); i++) {
				%>
		<tr data-href="#">
			<td><%= courses.get(i).getShortId() %> </td>
			<td><%= courses.get(i).getFullName() %> </td>
			<td><%= courses.get(i).getMode() %> </td>
			<td><%= courses.get(i).isOpened() ? "Yes" : "No" %> </td>
			<td><%= courses.get(i).getCurrSize() %>/<%= courses.get(i).getMaxCapacity() %> </td>
			<td><%= courses.get(i).getNEcts() %> </td>
			<td><%= courses.get(i).getFaculty() %> </td>
			<td><a href="#">Student list</a></td>
		</tr>
				<%
			}
		%>
	</table>
	<table id="studentList" style="display: none;">
		
	</table>
	<%
	} else {
		%> 
			<h2 style="color:red">Don't try to get teacher privileges if you do not have.</h2>
		<%
	}
	db.close();
	%>
</body>
</html>