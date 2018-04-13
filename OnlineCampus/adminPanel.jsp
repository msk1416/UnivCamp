<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="pl.mais.db.DBHelper"%>
<%@ page import="pl.mais.mapping.*" %>
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
	User current = db.getUserById(Integer.valueOf((String)session.getAttribute("userid")));
	session.removeAttribute("user");
	session.setAttribute("user", current);%>
	<h1>Logged as <%= current.getFirstName() %> <%= current.getLastName() %></h1>
	<%
	
	db.close();
	%>
	Insert new student:
	<form action="${ pageContext.request.contextPath }/NewUser" method="post"> 
		<input type="text" name="firstname" maxlength="50" placeholder="First name"> 
		<input type="text" name="lastname" maxlength="50" placeholder="Last name">
		<input type="text" name="birthday" placeholder="Birthday DD/MM/YYYY" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}">
		<input type="email" name="email" placeholder="Email address">
		<input type="text" name="currentstudies" maxlength="50" placeholder="Current studies">
		<input type="text" name="currentects" pattern="^[0-9]*$" placeholder="Current ECTS">
		<input type="hidden" name="role" value="s">
		<input type="submit" value="Insert">
	</form>
	Insert new teacher:
	<form action="${ pageContext.request.contextPath }/NewUser" method="post"> 
		<input type="text" name="firstname" maxlength="50" placeholder="First name"> 
		<input type="text" name="lastname" maxlength="50" placeholder="Last name">
		<input type="text" name="birthday" placeholder="Birthday DD/MM/YYYY" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}">
		<input type="email" name="email" placeholder="Email address">
		<input type="text" name="officenumber" placeholder="Office number" maxlength="6">
		<input type="hidden" name="role" value="t">
		<input type="submit" value="Insert">
	</form>
	Remove a teacher or student from the list:
	<form action="">
		<select>
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
	</form>
	
	

</body>
</html>