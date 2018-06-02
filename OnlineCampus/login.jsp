<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login page</title>
</head>
<body>
	<%
		//if we come from a log out we remove current user from session
		String param = request.getParameter("f");
		if (param != null && param.equals("logout")) {
			request.getSession().removeAttribute("userid");
		}
	%>
	<form action="${pageContext.request.contextPath}/LoginServlet" method="post">
		User ID: <input type="text" name="username" placeholder="User identification number" required><br/>
		Password: <input type="password" name="password" placeholder="Password" required><br/>
		<input type="submit" value="Sign in">
		<%
			if (param != null && param.equals("creds")) {
		%>
		<p style="color: red; display:inline;">Invalid user or password</p>
		<%
			}
		%>
	</form>
	

</body>
</html>