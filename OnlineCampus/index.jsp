<%@page import="pl.mais.db.DBHelper"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
<%
	DBHelper db = new DBHelper();
	db.open();
	String[] faculties = db.testSelectFaculties();
	db.close();
	for (int i = 0; i < faculties.length; i++) {
		%>
		<h2>
		<%=
			faculties[i]
		%>
		</h2>
		<%
	}
%>

</body>
</html>