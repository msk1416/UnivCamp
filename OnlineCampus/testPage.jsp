<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/teststyles.css"/>
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/testswrapper.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Unit testing page</title>
</head>
<body>
<h2>Use tests below to check that functionalities work</h2>
<h3>Add and remove a user</h3>
<form action="#" onsubmit="testAddRemoveUser(); return false;" method="post" id="formTestAddRemove" style="display: inline;">
	<input type="hidden" name="firstname" value="Test"> 
	<input type="hidden" name="lastname" value="User">
	<input type="hidden" name="birthday" value="01/01/1900">
	<input type="hidden" name="email" value="testuser@mais.pl.edu">
	<input type="hidden" name="currentstudies" value="Test engineering">
	<input type="hidden" name="currentects" value="50">
	<input type="hidden" name="role" value="s">
	<input type="submit" value="Click to run test">
</form>
<p style="color: blue; display: inline; visibility: hidden;" id="statusMsgTest">Running...</p>
<div id="statusMsgDiv"></div>


<h3>Add and remove a course</h3>
<form action="#" onsubmit="testAddRemoveCourse(); return false;" method="post" id="formCourseAddRemove" style="display: inline;">
	<input type="hidden" name="courseid" value="OSCT"> 
	<input type="hidden" name="coursename" value="One-Student Course Test">
	<input type="hidden" name="mode" value="elective">
	<input type="hidden" name="opened" value="on">
	<input type="hidden" name="maxcapacity" value="1">
	<input type="hidden" name="teacherid" value="-1">
	<input type="hidden" name="nects" value="10">
	<input type="hidden" name="faculty" value="IFE">
	<input type="submit" value="Click to run test">
</form>
<p style="color: blue; display: inline; visibility: hidden;" id="statusMsgTest">Running...</p>
<div id="statusMsgDiv"></div>
<br/>
<br/>
<a href="adminPanel.jsp" style="color: blue;">Go back to panel</a>
</body>
</html>