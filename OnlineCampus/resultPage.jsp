<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Result page</title>
</head>
<body>
	<%	
		if ((session == null) || 
		(session.getAttribute("success") == null) || 
		(session.getAttribute("object") == null) || 
		(session.getAttribute("redirect") == null)) {
			%>
				<h2 style='color: blue'>No results to show. Go back.</h2>
			<%
		} else {
				
				if ((Boolean)session.getAttribute("success")) {
					%>
						<h2 style='color: green'><%= (String)session.getAttribute("object") %> has been added successfully.</h2>
					<%
					
				} else {
					%>
						<h2 style='color: red'><%= (String)session.getAttribute("object") %> could not be added.</h2>
					<%
				}
			%>
			<a href="${ sessionScope.redirect }" style="color:blue;">Go back to your panel</a>
		</body>
		<%
			session.removeAttribute("success");
			session.removeAttribute("object");
			session.removeAttribute("redirect");
		}
%>
</html>