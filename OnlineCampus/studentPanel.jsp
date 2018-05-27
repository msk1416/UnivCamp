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
<link rel="stylesheet" type="text/css" href="css/tablestyles.css"/>
</head>
<body>
	<%
	DBHelper db = (DBHelper)application.getAttribute("dbhelper");
	if (db != null) db.open();
	if (session.getAttribute("userid") != null) {
		User current = db.getUserById(Integer.valueOf((String)session.getAttribute("userid")));
		session.removeAttribute("user");
		session.setAttribute("user", current);
		if (current != null) {
	%>
	<h1>Logged as <%= current.getFirstName() %> <%= current.getLastName() %></h1>
	<h3>My courses</h3>
	<table>
		<tr>
			<th>Reg ID</th>
			<th>Course</th>
			<th>Status</th>
			<th>Grade (if applicable)</th>
			<th>Teacher</th>
			<th>ECTS</th>
			<th>Faculty</th>
			<th>Actions</th>
		</tr>
	<%
		ArrayList<Registration> regs = db.getRegsFromStudent(current.getId());
		for (int i = 0; i < regs.size(); i++) {
			Course c = db.getCourseByShortId(regs.get(i).getCourseId());
			%>
		<tr>
			<td><%= regs.get(i).getRegId() %></td>
			<td><%= regs.get(i).getCourseId() %></td>
			<td><%= regs.get(i).getStatusForPrint() %></td>
			<td><%= regs.get(i).hasGrade() ? regs.get(i).getGrade() : "" %></td>
			<td><%= c.getTeacherId() > 0 ? db.getUserById(c.getTeacherId()).getFullName() : "Not assigned" %></td>
			<td><%= c.getNEcts() %></td>
			<td><%= c.getFaculty() %></td>
			<td>
				<form action="${ pageContext.request.contextPath }/DropStudent" method="post">
					<input type="hidden" name="requestedBy" value="<%= current.getId() %>"/>
					<input type="hidden" name="registersToDrop" value="<%= regs.get(i).getRegId() %>"/>
					<input type="submit" value="Remove" />
				</form>
			</td>
		</tr>
			<%
		}
	%>
	</table>
	<h3>Apply for a course</h3>
	<table>
		<tr>
			<th>Short name</th>
			<th>Full name</th>
			<th>Mode</th>
			<th>Opened</th>
			<th>Current size</th>
			<th>ECTS</th>
			<th>Faculty</th>
			<th>Actions</th>
		</tr>
		<%
			ArrayList<Course> courses = db.getCourses();
			for (int i = 0; i < regs.size(); i++) {
				courses.remove(db.getCourseByShortId(regs.get(i).getCourseId()));
			}
			for (int i = 0; i < courses.size(); i++) {
				%>
		<tr>
			<td><%= courses.get(i).getShortId() %> </td>
			<td><%= courses.get(i).getFullName() %> </td>
			<td><%= courses.get(i).getMode() %> </td>
			<td><%= courses.get(i).isOpened() ? "Yes" : "No" %> </td>
			<td><%= courses.get(i).getCurrSize() %>/<%= courses.get(i).getMaxCapacity() %> </td>
			<td><%= courses.get(i).getNEcts() %> </td>
			<td><%= courses.get(i).getFaculty() %> </td>
			<td>
				<%
					if (courses.get(i).getCurrSize() < courses.get(i).getMaxCapacity()) {
				%>
				<form action="${ pageContext.request.contextPath }/RegisterStudent" method="post">
					<input type="hidden" name="requestedBy" value="<%= current.getId() %>"/>
					<input type="hidden" name="coursesToRegister" value="<%= courses.get(i).getShortId()%>"/>
					<input type="submit" value="Apply" />
				</form>
				<%
					} else {
						%>
						Full
						<%
					}
				%>
			</td>
		</tr>
				<%
				
			}
		%>
	</table>
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