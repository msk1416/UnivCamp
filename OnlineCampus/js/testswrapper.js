/**
 * @author sergi
 */

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
	    	if(!responseText.includes("true")) {
	    		alert("The user id couldn't be found because it was not a unique first name - last name pair. Try again, please.");
	    		msg1Failed(1);
	    	} else {
	    		var newId = responseText.substring(responseText.indexOf('=')+1);
	    		msg1Success(1, "Student added succesfully with ID = " + newId);
	    		console.log("Student added succesfully with ID = " + newId + ", full name: " + firstname + " " + lastname);
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
	    					msg2Success(1, "Student removed succesfully.");
	    				} else {
	    					console.log("Student could not be removed.");
	    					msg2Failure(1, "Student could not be removed.");
	    				}
	    				finishedTest(1)
	    			},
	    			error: function(errorMsg) {
	    				console.log(errorMsg);
	    				msg2Failure(1, "Student could not be removed.")
	    				finishedTest(1)
	    			}
	    		});
	    	}
		}, 
		error: function(errorMsg) {
			console.log(errorMsg);
			msg1Failure(1, "Student could not be added.");
		}
	});
}

function secondTest() {
	runningTest(2);
	finishedTest(2);
}

function runningTest(n) {
	$('*[id=statusMsgTest]')[n-1].style.visibility = "visible";
	$('*[id=statusMsgTest]')[n-1].innerHTML = "Running...";
}
function finishedTest(n) {
	$('*[id=statusMsgTest]')[n-1].style.visibility = "visible";
	$('*[id=statusMsgTest]')[n-1].innerHTML = "Finished test";
}
function msg1Success(n, msg = "Action succeeded") {
	$('*[id=msg1Test]')[n-1].style.visibility = "visible";
	$('*[id=msg1Test]')[n-1].style.color = "green";
	$('*[id=msg1Test]')[n-1].style.fontWeight = "bold";
	$('*[id=msg1Test]')[n-1].innerHTML = msg;
}
function msg1Failure(n, msg = "Action failed") {
	$('*[id=msg1Test]')[n-1].style.visibility = "visible";
	$('*[id=msg1Test]')[n-1].style.color = "red";
	$('*[id=msg1Test]')[n-1].style.fontWeight = "bold";
	$('*[id=msg1Test]')[n-1].innerHTML = msg;
}
function msg2Success(n, msg = "Action succeeded") {
	$('*[id=msg2Test]')[n-1].style.visibility = "visible";
	$('*[id=msg2Test]')[n-1].style.color = "green";
	$('*[id=msg2Test]')[n-1].style.fontWeight = "bold";
	$('*[id=msg2Test]')[n-1].innerHTML = msg;
}
function msg2Failure(n, msg = "Action failed") {
	$('*[id=msg2Test]')[n-1].style.visibility = "visible";
	$('*[id=msg2Test]')[n-1].style.color = "red";
	$('*[id=msg2Test]')[n-1].style.fontWeight = "bold";
	$('*[id=msg2Test]')[n-1].innerHTML = msg;
}