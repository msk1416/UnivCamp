<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login page</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/LoginServlet" method="post">
		User ID: <input type="text" name="username" placeholder="User identification number"><br/>
		Password: <input type="password" name="password" placeholder="Password"><br/>
		<input type="submit" value="Sign in">
	</form>
	

</body>
</html>