/**
 * @author sergi
 */

class PackedInfo {
	constructor(cid, cname, mode, opened, maxcap, tid, ects, fac, formNumber){
		this.courseid = cid;
		this.coursename = cname;
		this.mode = mode;
		this.opened = opened;
		this.maxcapacity = maxcap;
		this.teacherid = tid;
		this.nects = ects;
		this.faculty = fac;
		this.i = formNumber;
	}
	addRandomStudentId(sid) {
		this.studentId = sid;
	}
	addSecondStudentId(ssid) {
		this.secondStudentId = ssid;
	}
	addCourseInfo(grade, status, password) {
		this.grade = grade;
		this.coursestatus = status;
		this.password = password;
	}
}
var info;
function testAddRemoveUser() {
	
	runningTest(1);
	var firstname = document.forms["formTestAddRemove"].elements["firstname"].value;
	var lastname = document.forms["formTestAddRemove"].elements["lastname"].value;
	var birthday = document.forms["formTestAddRemove"].elements["birthday"].value;
	var email = document.forms["formTestAddRemove"].elements["email"].value;
	var currentstudies = document.forms["formTestAddRemove"].elements["currentstudies"].value;
	var currentects = document.forms["formTestAddRemove"].elements["currentects"].value;
	var role = document.forms["formTestAddRemove"].elements["role"].value;
	var uniqueNumber = Math.floor(Math.random() * (500000000)) + 1;
	firstname += "__" + uniqueNumber;
	lastname += "__" + uniqueNumber;
	$.ajax({
		type: "POST",
		url: "/UnivCamp/NewUser", 
		data: { 
			test: "true",
			firstname: firstname,
			lastname: lastname,
			birthday: birthday,
			email: email,
			currentstudies: currentstudies,
			currentects: currentects,
			role: role
		
		}, 
		success: function(responseText) {
	    	removeStudentAjax(responseText, firstname + " " + lastname);
		}, 
		error: function(errorMsg) {
			console.log(errorMsg);
			msgFailure(1, "Student could not be added.");
		}
	});
}

function removeStudentAjax(res, fullname) {
	if(!res.includes("true")) {
		alert("The user id couldn't be found because it was not a unique first name - last name pair. Try again, please.");
		msgFailure(1);
	} else {
		var newId = res.substring(res.indexOf('=')+1);
		msgSuccess(1, "Student added succesfully with ID = " + newId);
		console.log("Student added succesfully with ID = " + newId + ", full name: " + fullname);
		var adminPassword = "admin";
		$.ajax({
			type: "POST",
			url: "/UnivCamp/RemoveUser",
			data: {
				test: "true",
				userToDelete: newId,
				password: adminPassword
			},
			success: function(responseText) {
				if (responseText.includes("true")) {
					console.log("Student removed succesfully.");
					msgSuccess(1, "Student removed succesfully.");
				} else {
					console.log("Student could not be removed.");
					msgFailure(1, "Student could not be removed.");
				}
				finishedTest(1)
			},
			error: function(errorMsg) {
				console.log(errorMsg);
				msgFailure(1, "Student could not be removed.")
				finishedTest(1)
			}
		});
	}
}


function testAddRemoveCourse() {
	var i = 2;
	runningTest(i);
	info = new PackedInfo(
			document.forms["formCourseAddRemove"].elements["courseid"].value,
			document.forms["formCourseAddRemove"].elements["coursename"].value,
			document.forms["formCourseAddRemove"].elements["mode"].value,
			document.forms["formCourseAddRemove"].elements["opened"].value,
			document.forms["formCourseAddRemove"].elements["maxcapacity"].value,
			document.forms["formCourseAddRemove"].elements["teacherid"].value,
			document.forms["formCourseAddRemove"].elements["nects"].value,
			document.forms["formCourseAddRemove"].elements["faculty"].value,
			i);
	$.ajax({
		type: "GET",
		url: "/UnivCamp/RandomStudent",
		data: {test: "true"},
		success: function(responseText) {
			var id = responseText.substring(responseText.indexOf(';')+1);
			if (id <= 0){
				msgFailure(i, "No random student could be found.");
			} else {
				info.addRandomStudentId(id);
				addNewCourseAjax();
			}
		},
		error: function (errorMsg) {
			console.log(errorMsg);
		}
	});
}

function addNewCourseAjax() {
	$.ajax({
		type: "POST",
		url: "/UnivCamp/AddCourse",
		data: {
			courseid: info.courseid,
			coursename: info.coursename,
			mode: info.mode,
			opened: info.opened,
			maxcapacity: info.maxcapacity,
			teacherid: info.teacherid,
			nects: info.nects,
			faculty: info.faculty,
			test: "true"
		},
		success: registerStudentToCourseAjax,
		error: function(errorMsg) {
			console.error(errorMsg);
			msgFailed(info.i, "Course could not be added.");
		}
	});
}

function registerStudentToCourseAjax(responseText) {
	responseText.includes("true") ? 
			msgSuccess(info.i, "Course has beed added successfully.") : msgFailure(info.i, "Course could not be added.");
	info.addCourseInfo(5.0, "pass", "admin");

	$.ajax({
		type: "POST",
		url: "/UnivCamp/RegisterStudent",
		data: {
			coursesToRegister: info.courseid,
			grade: info.grade,
			courseStatus: info.coursestatus,
			userToRegister: info.studentId,
			password: info.password,
			test: "true"
		},
		success: findNextStudentAjax,
		error: function(errorMsg) {
			console.error(errorMsg);
			msgFailure(info.i, "Student could not be registered into the course.");
		}
	});
}

function findNextStudentAjax(responseText) {
	responseText.includes("true") ? 
			msgSuccess(info.i, "User has beed registered to the course successfully.") : msgFailure(info.i, "Student could not be registered.");
	$.ajax({
		type: "GET",
		url: "/UnivCamp/RandomStudent",
		data: {test: "true"},
		success: addSecondStudentToNewCourseAjax,
		error: function (errorMsg) {
			console.log(errorMsg);
		}
	});			
}

function addSecondStudentToNewCourseAjax(responseText) {
	var id = responseText.substring(responseText.indexOf(';')+1);
	if (id <= 0){
		msgFailure(i, "No random student could be found.");
	} else {
		info.addSecondStudentId(id);
		$.ajax({
			type: "POST",
			url: "/UnivCamp/RegisterStudent",
			data: {
				coursesToRegister: info.courseid,
				grade: info.grade,
				courseStatus: info.coursestatus,
				userToRegister: info.secondStudentId,
				password: info.password,
				test: "true"
			},
			success: dropFirstStudentAjax,
			error: function(errorMsg) {
				console.error(errorMsg);
				msgFailure(info.i, "Student could not be registered into the course.");
			}
		})
	}
}

function dropFirstStudentAjax(responseText) {
	if (responseText.includes("false")) {
		msgSuccess(info.i, "Second student could not be added.");
		$.ajax({
			type: "POST",
			url: "/UnivCamp/DropStudent",
			data: {
				registersToDrop: info.studentId + "_" + info.courseid,
				password: info.password,
				test: "true"
			},
			success: removeCourseAjax,
			error: function (errorMsg) {
				console.error(errorMsg);
				msgFailure(info.i, "Student could not be dropped from the test course.");
			}
		});
	} else {
		msgFailure(info.i, "Second student could be added, it should fail.");
	}
}

function removeCourseAjax(responseText) {
	if (responseText.includes("true")) {
		msgSuccess(info.i, "Student has been dropped from the test course successfully.");
		$.ajax({
			type: "POST",
			url: "/UnivCamp/RemoveCourse",
			data: {
				test: "true",
				courseToDelete: info.courseid,
				password: info.password
			},
			success: finalizeTest,
			error: function(errorMsg) {
				console.error(errorMsg);
				msgFailure(info.i, "Test course could not be removed.");
			}
		})
	} else {
		msgFailure(info.i, "Student could not be dropped from the test course.");
	}
}
function finalizeTest(responseText) {
	if (responseText.includes("true")) {
		msgSuccess(info.i, "Test course has been removed succesfully.");
		msgSuccess(info.i, "Test has been passed.");
	} else {
		msgFailure(info.i, "Test course could not be removed.");
		msgFailure(info.i, "Test failed.");
	}
	finishedTest(info.i);
}
function runningTest(n) {
	$('*[id=statusMsgTest]')[n-1].style.visibility = "visible";
	$('*[id=statusMsgTest]')[n-1].innerHTML = "Running...";
	initMsgBox(n);
}
function finishedTest(n) {
	$('*[id=statusMsgTest]')[n-1].style.visibility = "visible";
	$('*[id=statusMsgTest]')[n-1].innerHTML = "Finished test";
}
function initMsgBox(n) {
	$('*[id=statusMsgDiv]')[n-1].innerHTML = '';
}
function msgSuccess(n, msg = "Action succeeded") {
	var div = $('*[id=statusMsgDiv]')[n-1];
	var p = document.createElement('p');
	$(p).attr("class", "succeeded statusMsg");
	p.innerHTML = msg;
	div.appendChild(p);
}
function msgFailure(n, msg = "Action failed") {
	var div = $('*[id=statusMsgDiv]')[n-1];
	var p = document.createElement('p');
	$(p).attr("class", "failed statusMsg");
	p.innerHTML = msg;
	div.appendChild(p);
}