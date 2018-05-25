/**
 * 
 */

function setEditMode(td) {
	
	var form = document.createElement("form");
	form.method = "POST";
	form.action = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2)) + "/UpdateGrade";
	form.onsubmit = function() {
		var g = parseFloat(this.children["grade"].value);
		if (g == null || g < 0 || g > 5) {
			this.children["grade"].style.color = "red";
			return false;
		}
	}
	var regId = td.parentElement.childNodes[1].innerText;
	var hidden = document.createElement("input");
	hidden.type = "hidden";
	hidden.value = regId;
	hidden.name = "regId";
	
	
	var inputGrade = document.createElement("input");
	inputGrade.type = "text";
	inputGrade.maxLength = 3;
	inputGrade.style = "width: 30px;";
	inputGrade.name = "grade";
	inputGrade.setAttribute("required", true);
	inputGrade.pattern = "[+-]?([0-5]*[.])?[0-9]+";
	inputGrade.title = "Enter a valid number in range 0.0 - 5.0, out of range number will be treated as null";
	td.innerText = "";
	

	var btnOk = document.createElement("input");
	btnOk.type = "image";
	btnOk.alt = "Confirm changes";
	btnOk.title = "Confirm changes";
	btnOk.src = "./imgs/ok.png";
	btnOk.style = "vertical-align: middle;"
	btnOk.name = "submit";

	var btnCancel = document.createElement("input");
	btnCancel.type = "image";
	btnCancel.alt = "Discard changes";
	btnCancel.title = "Discard changes";
	btnCancel.src = "./imgs/cancel.png";
	btnCancel.style = "vertical-align: middle; width: 20px; height: 15px;";
	
	form.append(inputGrade);
	form.append(hidden);
	form.append(btnOk);
	form.append(btnCancel);
	td.append(form);
}

