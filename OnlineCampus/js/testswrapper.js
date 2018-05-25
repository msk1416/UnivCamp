/**
 * @author sergi
 */

function testAddRemoveUser() {
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
//	var res = $.post("/UnivCamp/NewUser", { 
//			test: "true",
//			firstname: firstname,
//			lastname: lastname,
//			birthday: birthday,
//			email: email,
//			currentstudies: currentstudies,
//			currentects: currentects,
//			role: role
//		
//		}, function(responseText) {
//			console.log("callback function is executed")
//	    	if(!responseText.includes("true")) {
//	    		alert("Some error happened while validating the xml file.");
//	    	} else {
//	    		var newId = responseText.substring(responseText.indexOf('=')+1);
//	    		
//	    	}
//		});
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
	    	} else {
	    		var newId = responseText.substring(responseText.indexOf('=')+1);
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
	    				} else {
	    					console.log("Student could not be removed.");
	    				}
	    			},
	    			error: function(errorMsg) {
	    				console.log(errorMsg);
	    			}
	    		});
	    	}
		}, 
		error: function(errorMsg) {
			console.log(errorMsg);
		}
	});
}